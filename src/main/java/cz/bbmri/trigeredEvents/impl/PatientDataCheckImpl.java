package cz.bbmri.trigeredEvents.impl;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Module;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.constant.Constant;
import cz.bbmri.entities.enumeration.Status;
import cz.bbmri.entities.sample.Sample;
import cz.bbmri.io.FileImportResult;
import cz.bbmri.io.InstanceImportResult;
import cz.bbmri.io.PatientDataParser;
import cz.bbmri.service.BiobankService;
import cz.bbmri.service.PatientService;
import cz.bbmri.service.SampleService;
import cz.bbmri.service.impl.ServiceUtils;
import cz.bbmri.trigeredEvents.PatientDataCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

// Must not be @Transactional !

@Service("patientDataCheck")
public class PatientDataCheckImpl extends Basic implements PatientDataCheck {

    @Autowired
    private BiobankService biobankService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private SampleService sampleService;


    @Scheduled(cron = "1 * * * * *")
    public void scheduledPatientDataCheck() {

        // Check all biobank
        for (Biobank biobank : biobankService.all()) {

            // Scan all patient data files in biobank folder
            List<File> files = ServiceUtils.getFiles(storagePath + biobank.getBiobankPatientDataFolder());

            for (File file : files) {

                FileImportResult fileImportResult = parsePatientImportFile(file, biobank);

                System.err.println(fileImportResult);

                if (fileImportResult.getStatus().equals(Status.ERROR)) {
                    break;
                    //TODO: UNCOMMENT continue, delete break
                    // nothing to be done here
                   // continue;
                }

                if (ServiceUtils.copyFile(file, storagePath + biobank.getBiobankPatientArchiveDataFolder()) == Constant.SUCCESS) {
                    // delete file if copy succeeded
                    ServiceUtils.deleteFile(file);
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
        PatientDataParser patientDataParser = initialize(file.getPath());
        if (patientDataParser == null) {
            fileImportResult.setStatus(Status.ERROR);
            return fileImportResult;
        }

        String biobankAbbreviation = patientDataParser.getBiobankAbbreviation();

        // ID retrieved from .xml must match the abbreviation of given biobank
        if (!biobankAbbreviation.equals(biobank.getAbbreviation())) {
            logger.error("Biobank abbreviation (identifier in export) doesn't match");

            fileImportResult.setStatus(Status.ERROR);
            return fileImportResult;
        }

        Patient patient = patientDataParser.getPatient();

        if (patient == null) {
            logger.error("Patient null");

            fileImportResult.setStatus(Status.ERROR);
            return fileImportResult;
        }

        InstanceImportResult patientImportResult = parsePatientImport(patient, biobank.getId());

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
     * TODO popisek
     */
    private InstanceImportResult parsePatientImport(Patient patient, Long biobankId) {

        if (patient == null) {
            logger.error("Patient is null");
            return null;
        }

        InstanceImportResult result = patientService.importInstance(patient, biobankId);

        return result;
    }

    private List<InstanceImportResult> parsePatientSamplesImport(Patient patient, PatientDataParser parser) {

        notNull(patient);

        Patient patientDB = patientService.get(patient.getId());

        if (patientDB.getModuleLTS() == null) {
            logger.debug("Patient module null");
            return null;
        }

        // Parse LTS module

        List<Sample> samplesLTS = parser.getPatientLtsSamples();
        if (samplesLTS == null) {
            logger.debug("Parse of LTS module from import failed");
            return null;
        }

        List<InstanceImportResult> result = new ArrayList<InstanceImportResult>();

        if (!samplesLTS.isEmpty()) {

            List<InstanceImportResult> ltsImportResults = manageImportedSamples(samplesLTS, patientDB.getModuleLTS());
            if (ltsImportResults == null) {
                logger.debug("ManageImportedSamples for LTS module failed");
                return null;
            }

            result.addAll(ltsImportResults);

        }

        // Parse STS module

        List<Sample> samplesSTS = parser.getPatientStsSamples();
        if (samplesSTS == null) {
            logger.debug("Parse of STS module from import failed");
            return null;
        }

        if (!samplesSTS.isEmpty()) {

            List<InstanceImportResult> ltsImportResults = manageImportedSamples(samplesSTS, patientDB.getModuleLTS());
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
     * @param module  - current module - LTS or STS of patient
     * @return SUCCESS/NOT_SUCCESS
     */
    private List<InstanceImportResult> manageImportedSamples(List<Sample> samples, Module module) {

        if (module == null) {
            logger.error("Module must not be null");

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

            Sample sampleDB = sampleService.getByInstitutionalId(sample.getSampleIdentification().getSampleId());
            if (sampleDB != null) {
                // this sample is already in DB but associated with different user
                if (!sampleDB.getModule().getPatient().equals(module.getPatient())) {
                    System.err.println("Trying to add sample which is already associated to different user");
                    return null;
                }

            }

            InstanceImportResult result = sampleService.importInstance(sample, module.getId());

            if (result == null) {
                System.err.println("InstanceImportResult is null");
                return null;
            }

            instanceImportResults.add(result);

        }

        return instanceImportResults;
    }

}
