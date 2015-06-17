package cz.bbmri.trigeredEvents.impl;

import cz.bbmri.dao.*;
import cz.bbmri.entity.*;
import cz.bbmri.entity.constant.Constant;
import cz.bbmri.entity.enumeration.Status;
import cz.bbmri.io.*;
import cz.bbmri.trigeredEvents.PatientDataCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

// Must not be @Transactional !
@Service("patientDataCheck")
public class PatientDataCheckImpl extends Basic implements PatientDataCheck {

    @Autowired
    private BiobankDAO biobankDAO;

    @Autowired
    private PatientDAO patientDAO;

    @Autowired
    private SampleDAO sampleDAO;

    @Autowired
    private BiobankMaterialTypeDAO biobankMaterialTypeDAO;

    @Autowired
    private MaterialTypeDAO materialTypeDAO;

    @Autowired
    private ConservationMethodDAO conservationMethodDAO;

    @Scheduled(cron = "0 0 8 * * *") // each day 8:00
//    @Scheduled(cron = "0 */5 * * * *") // each 5 minutes
//    @Scheduled(cron = "* * * * * *") // each minute
    public void scheduledPatientDataCheck() {

        System.err.println("CRON event triggered at: " + new Date());

        // Check all biobank
        for (Biobank biobank : biobankDAO.all()) {

            // Scan all patient data files in biobank folder
            List<File> files = FileUtils.getFiles(storagePath + biobank.getBiobankPatientDataFolder());

            for (File file : files) {

                System.err.println("File: " + file.getAbsolutePath());

                FileImportResult fileImportResult = parsePatientImportFile(file, biobank);

//                log(fileImportResult.toString());

                if (fileImportResult.getStatus().equals(Status.ERROR)) {

                    System.err.println("Error ");

                    // nothing to be done here
                    continue;
                }

                if (FileUtils.copyFile(file, storagePath + biobank.getBiobankPatientArchiveDataFolder()) == Constant.SUCCESS) {
                    // delete file if copy succeeded
                    FileUtils.deleteFile(file);
                }
            }
        }
    }

    public FileImportResult parsePatientImportFile(File file, Biobank biobank) {

        // Trida ktera popisuje jak dopadl import jednoho souboru.
        // Tj. zalozen pacient, upraven pacient, nezmenen pacient, error

        FileImportResult fileImportResult = new FileImportResult();
        fileImportResult.setPath(file.getPath());

        //FileImportReport fileImportResult =

        //Inicializovat parser;
        // pokud ne tak error cteni souboru
        AbstractPatientParser patientDataParser;

        // MOU -- hack
        if (biobank.getId() == 1) {
            patientDataParser = initialize(file.getPath());
        } else {
            // 1. LF hack ..
            patientDataParser = initializeV2(file.getPath());
        }

        if (patientDataParser == null) {
            fileImportResult.setStatus(Status.ERROR);
            return fileImportResult;
        }

        String biobankInstitutionalId = patientDataParser.getBiobankInstitutionalId();

        // ID retrieved from .xml must match the abbreviation of given biobank
        if (!biobankInstitutionalId.equals(biobank.getInstitutionalId())) {
            logger.error("Biobank institutional id (identifier in export) doesn't match");

            fileImportResult.setStatus(Status.ERROR);
            return fileImportResult;
        }

        Patient patient = patientDataParser.getPatient();

        if (patient == null) {
            logger.error("Patient null");

            fileImportResult.setStatus(Status.ERROR);
            return fileImportResult;
        }

        patient.setBiobank(biobank);

        InstanceImportResult patientImportResult = patientDAO.importInstance(patient);

        if (patientImportResult == null) {
            logger.error("parsePatientImport failed");

            fileImportResult.setStatus(Status.ERROR);
            return fileImportResult;
        }

        if (patientImportResult.getStatus().equals(Status.ERROR)) {
            fileImportResult.setStatus(Status.ERROR);
            return fileImportResult;
        }

        fileImportResult.setStatus(patientImportResult.getStatus());

        fileImportResult.getInstanceImportResults().add(patientImportResult);

        if (patientImportResult.getStatus().equals(Status.REMOVED) ||
                patientImportResult.getStatus().equals(Status.NOT_ADDED)) {
            // no need to check samples
            // consent changed to false -> patient was deleted
            return fileImportResult;
        }

        List<InstanceImportResult> sampleImportResults = parsePatientSamplesImport(patient, patientDataParser);

        if (sampleImportResults == null) {
            logger.error("parsePatientSamplesImport failed");

            fileImportResult.setStatus(Status.ERROR);
            return fileImportResult;
        }

        fileImportResult.getInstanceImportResults().addAll(sampleImportResults);

        return fileImportResult;
    }


    /**
     * Check presence and validity of import xml file.
     *
     * @param path
     * @return new instance of PatientDataParser or null if document is not valid or document doesn't exist
     */

    private PatientDataParser initialize(String path) {

        notNull(path);

        PatientDataParser parser;
        try {
            parser = new PatientDataParser(path);

        } catch (Exception ex) {
            logger.error("PatientDataParser failed");
            ex.printStackTrace();
            return null;
        }

        // document must be valid against .xsd
        if (!parser.validate()) {
            logger.error("Document is NOT valid. Document path was: " + path);
            return null;
        }

        return parser;
    }

    /**
     * Check presence and validity of import xml file.
     *
     * @param path
     * @return new instance of PatientDataParser or null if document is not valid or document doesn't exist
     */

    private PatientDataParserV2 initializeV2(String path) {

        notNull(path);

        PatientDataParserV2 parser;
        try {
            parser = new PatientDataParserV2(path);

        } catch (Exception ex) {
            logger.error("PatientDataParser failed");
            ex.printStackTrace();
            return null;
        }

        // document must be valid against .xsd
        if (!parser.validate()) {
            logger.error("Document is NOT valid. Document path was: " + path);
            return null;
        }

        return parser;
    }

    private List<InstanceImportResult> parsePatientSamplesImport(Patient patient, AbstractPatientParser parser) {

        notNull(patient);

        Patient patientDB = patientDAO.get(patient.getId());

        // Parse LTS module

        List<Sample> samplesLTS = parser.getPatientLtsSamples();
        if (samplesLTS == null) {
            logger.debug("Parse of LTS module from import failed");
            return null;
        }

        List<InstanceImportResult> result = new ArrayList<InstanceImportResult>();

        if (!samplesLTS.isEmpty()) {

            List<InstanceImportResult> ltsImportResults = manageImportedSamples(samplesLTS, patientDB);
            if (ltsImportResults == null) {
                logger.debug("ManageImportedSamples for LTS module failed");
                return null;
            }

            result.addAll(ltsImportResults);

        }

        // Parse STS module

        List<Sample> samplesSTS = parser.getPatientStsSamples();
        if (samplesSTS != null && !samplesSTS.isEmpty()) {

            List<InstanceImportResult> ltsImportResults = manageImportedSamples(samplesSTS, patientDB);
            if (ltsImportResults == null) {
                logger.debug("ManageImportedSamples for STS module failed");
                return null;
            }

            result.addAll(ltsImportResults);
        }

        return result;
    }

    /**
     * Handle samples retrieved from xml
     *
     * @param samples - list of samples
     * @param patient - patient
     * @return SUCCESS/NOT_SUCCESS
     */
    private List<InstanceImportResult> manageImportedSamples(List<Sample> samples, Patient patient) {

        if (patient == null) {
            logger.error("Patient must not be null");

            // General error, don't use report
            return null;
        }

        if (samples == null) {
            logger.error("Samples must not be null");

            // General error, don't use report
            return null;
        }

        List<InstanceImportResult> instanceImportResults = new ArrayList<InstanceImportResult>();

        for (Sample sample : samples) {

            Sample sampleDB = sampleDAO.getByInstitutionalId(sample.getInstitutionalId());
            if (sampleDB != null) {
                // this sample is already in DB but associated with different user
                if (!sampleDB.getPatient().equals(patient)) {
                    System.err.println("Trying to add sample which is already associated to different user");
                    return null;
                }

            }

            sample.setPatient(patient);

            InstanceImportResult result = importSampleInstance(sample);

            if (result == null) {
                System.err.println("InstanceImportResult is null");
                return null;
            }

            instanceImportResults.add(result);

        }

        return instanceImportResults;
    }

    public InstanceImportResult importSampleInstance(Sample sample) {
        notNull(sample);

        InstanceImportResult result = new InstanceImportResult(Sample.class.toString());

        if (sample.getInstitutionalId() == null) {
            result.setStatus(Status.ERROR);
            return result;
        }

        Sample sampleDB = sampleDAO.getByInstitutionalId(sample.getInstitutionalId());

        // is sample in DB ?
        if (sampleDB == null) {
            // No .. lets create it

            setSampleFields(sample);

            sample = sampleDAO.save(sample);

            // new sample was successfully added
            result.setIdentifier(sample.getId());
            result.setStatus(Status.ADDED_NEW);
            return result;

        } else {
            // Sample is already in DB

            sample.setId(sampleDB.getId());

            setSampleFields(sample);

            result = sampleDAO.updateWithResult(sample);

            // If something went wrong
            if (result == null) {
                result = new InstanceImportResult(Sample.class.toString());
                result.setIdentifier(sample.getId());
                result.setStatus(Status.ERROR);
            }

        }
        return result;
    }

    private void setSampleFields(Sample sample) {


        if (sample.getBiopticalReport() != null) {
            sample.getBiopticalReport().setSample(sample);
        }

        if (sample.getQuantity() != null) {
            sample.getQuantity().setSample(sample);
        }

        if (sample.getPtnm() != null) {
            sample.getPtnm().setSample(sample);
        }

        if (sample.getTnm() != null) {
            sample.getTnm().setSample(sample);
        }

        if (sample.getMorphology() != null) {
            sample.getMorphology().setSample(sample);
        }

        if (sample.getStorageMethodology() != null) {
            if(sample.getStorageMethodology().getConservationMethod() != null){

                // There are only key set in conservationMethod of parsedSet so it wont be attached to appropriate object
                // stored in database
                Set<ConservationMethod> parsedSet = sample.getStorageMethodology().getConservationMethod();

                sample.getStorageMethodology().setConservationMethod(new HashSet<ConservationMethod>());

                for(ConservationMethod conservationMethod : parsedSet){
                    ConservationMethod cmDB = conservationMethodDAO.getByKey(conservationMethod.getKey());
                    if(cmDB != null){
                        sample.getStorageMethodology().getConservationMethod().add(cmDB);
                    }
                }
            }

            sample.getStorageMethodology().setSample(sample);


        }

        if (sample.getDiagnosis() != null) {
            for (Diagnosis diagnosis : sample.getDiagnosis()) {
                diagnosis.setSample(sample);
            }
        }

        if (sample.getMaterialType() != null) {

            // hack - key is temporarily stored in MaterialType.name to not need DAO linked in parser
            String key = sample.getMaterialType().getName();
            if (key != null) {

                Biobank biobank = sample.getPatient().getBiobank();

                // find existing identifier of materialType for biobank
                MaterialType materialType = biobankMaterialTypeDAO.get(biobank, key);

                // exist?
                if (materialType != null) {
                    sample.setMaterialType(materialType);
                } else {
                    //create at least new fake type to enable further harmonization
                    materialType = sample.getMaterialType();

                    biobank = biobankDAO.get(biobank.getId());

                    // set combination biobank + key to this UNKNOWN type
                    BiobankMaterialType biobankMaterialType = new BiobankMaterialType();
                    biobankMaterialType.setBiobank(biobank);
                    biobankMaterialType.setKey(key);
                    biobankMaterialType.setMaterialType(materialType);

                    materialType.setName(biobank.getInstitutionalId() + PatientDataParser.BIOBANK_PREFIX_SEPARATOR + key);
                    materialType.setDescription(MaterialType.UNKNOWN_TYPE);
                    materialType.getBiobankMaterialType().add(biobankMaterialType);

                    materialType = materialTypeDAO.save(materialType);
                    sample.setMaterialType(materialType);
                }
            }
        }
    }
}
