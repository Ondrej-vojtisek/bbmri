package cz.bbmri.trigeredEvents.impl;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Module;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.constant.Constant;
import cz.bbmri.entities.exception.DifferentEntityException;
import cz.bbmri.entities.sample.Sample;
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
import java.util.List;

/**
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

// Can't be @Transactional !

@Service("patientDataCheck")
public class PatientDataCheckImpl extends Basic implements PatientDataCheck {

    @Autowired
    private BiobankService biobankService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private SampleService sampleService;

    /**
     * Method should be fired once per day - 1 minute after midnight.
     * Scheduled(cron = "1 0 * * * *")
     * For testing purpose is better to fire the method each minute.
     */

    //Testing purpose only. TODO remove
    @Scheduled(cron = "1 * * * * *")
    public void checkBiobankPatientData() {

        log("Cron fired method checkBiobankPatientData");

        // Check all biobank
        for (Biobank biobank : biobankService.all()) {

            log("Biobank: " + biobank.getName());

            // Scan all patient data files in biobank folder
            List<File> files = ServiceUtils.getFiles(storagePath + biobank.getBiobankPatientDataFolder());

            log("Checked path: " + storagePath + biobank.getBiobankPatientDataFolder());

            log("Biobank: List of files: " + files);


            // for each import do
            for (File file : files) {
                log("Biobank: " + biobank.getName() + " file: " + file);
                // parse
                if (parsePatientImport(file.getPath(), biobank) != Constant.SUCCESS) {

                    // TODO solve duplicate logging

                    logger.error("Parse of file : " + file + " failed. ");

                    archiveSystem("Parse of file : " + file + " failed. ");

                    // Don't copy and remove file in case that something went wrong
                    continue;
                }

                //TODO uncomment

                // if successfull copy it into archive folder
                //   if (ServiceUtils.copyFile(file, storagePath + biobank.getBiobankPatientArchiveDataFolder()) == Constant.SUCCESS) {
                // delete file if copy succeeded
                //        ServiceUtils.deleteFile(file);
                //    }

            }
        }
    }

    /**
     * Parse data from one .xml import file. Parse one patient and all his associated data
     *
     * @param path    - path to .xml file
     * @param biobank - - biobank which is currently managed
     * @return SUCCESS/NOT_SUCCESS
     */
    private int parsePatientImport(String path, Biobank biobank) {

        PatientDataParser parser;
        try {
            parser = new PatientDataParser(path);
        } catch (Exception ex) {
            logger.error("PatientDataParser failed");
            ex.printStackTrace();
            return Constant.NOT_SUCCESS;
        }

        // document must be valid against .xsd
        if (!parser.validate()) {
            logger.error("Document is NOT valid. Document path was: " + path);
            return Constant.NOT_SUCCESS;
        }

        String biobankAbbreviation = parser.getBiobankAbbreviation();

        // ID retrieved from .xml must match the abbreviation of given biobank
        if (!biobankAbbreviation.equals(biobank.getAbbreviation())) {
            logger.error("Biobank abbreviation (identifier in export) doesn't match");
            return Constant.NOT_SUCCESS;
        }

        // get instance of patient
        Patient patient = parser.getPatient();

        if (patient == null) {
            logger.error("Patient is null");
            return Constant.NOT_SUCCESS;
        }
        Patient patientDB = patientService.getByInstitutionalId(patient.getInstitutionId());
        if (patientDB == null) {
            // Patient is new
            patient = patientService.create(patient, biobank.getId());
            // get Patient with associated object - e.g. modules (moduleLTS)
            patient = patientService.get(patient.getId());

            // TODO: only one debug
            logger.debug("New patient created. Id: " + patient.getId() + ", institutionalID:" + patient.getInstitutionId());

            archiveSystem("New patient created. Id: " + patient.getId() + ", institutionalID:" + patient.getInstitutionId());


        } else {

            // Assign ID to object parsed from XML
            patient.setId(patientDB.getId());

            try {
                // patient differs against DB
                if (!patientDB.attributeEquality(patient)) {
                    patientService.update(patient);

                    // TODO: only one debug
                    logger.debug("Patient with ID:" + patient.getId() + " and institutionalID:" + patient.getInstitutionId() + " was updated in current import");

                    archiveSystem("Patient with ID:" + patient.getId() + " and institutionalID:" + patient.getInstitutionId() + " was updated in current import");
                }
            } catch (DifferentEntityException ex) {
                logger.error("This must not happened. New patient was not created but it doens't match any patient in DB." + ex.getMessage());
                return Constant.NOT_SUCCESS;
            }
            // Patient already exists
            patient = patientDB;
        }

        if (patient.getModuleLTS() == null) {
            logger.debug("Patient module null");
            return Constant.NOT_SUCCESS;
        }

        // Parse LTS module
        List<Sample> samples = parser.getPatientLtsSamples();
        if (samples == null) {
            logger.debug("Parse of LTS module from import failed");
            return Constant.NOT_SUCCESS;
        }
        if (!samples.isEmpty()) {
            if (manageImportedSamples(samples, patient.getModuleLTS()) != Constant.SUCCESS) {
                logger.debug("ManageImportedSamples for LTS module failed");
                return Constant.NOT_SUCCESS;
            }
        } else {
            logger.debug("Samples is empty - LTS module was not found or it was empty or there is other bug.");
        }

        // Parse STS module
        samples = parser.getPatientStsSamples();
        if (samples == null) {
            logger.debug("Parse of STS module from import failed");
            return Constant.NOT_SUCCESS;
        }
        if (!samples.isEmpty()) {
            if (manageImportedSamples(samples, patient.getModuleSTS()) != Constant.SUCCESS) {
                logger.debug("ManageImportedSamples for STS module failed");
                return Constant.NOT_SUCCESS;
            }
        } else {
            logger.debug("Samples is empty - STS module was not found or it was empty or there is other bug.");
        }


        return Constant.SUCCESS;
    }

    /**
     * Handle samples retrieved from xml
     *
     * @param samples - list of samples
     * @param module  - current module - LTS or STS of patient
     * @return SUCCESS/NOT_SUCCESS
     */
    private int manageImportedSamples(List<Sample> samples, Module module) {

        if (module == null) {
            logger.error("Module must not be null");
            return Constant.NOT_SUCCESS;
        }

        for (Sample sample : samples) {

            if (sample.getSampleIdentification() == null) {
                return Constant.NOT_SUCCESS;
            }
            if (sample.getSampleIdentification().getSampleId() == null) {
                return Constant.NOT_SUCCESS;
            }

            // Set module
            sample.setModule(module);

            Sample sampleDB = sampleService.getByInstitutionalId(sample.getSampleIdentification().getSampleId());

            if (sampleDB == null) {
                // sample is not in DB
                sampleService.create(sample, sample.getModule().getId());
            } else {
                // set primary identifier
                sample.setId(sampleDB.getId());
                try {
                    // not the same
                    if (!sample.attributeEquality(sampleDB)) {
                        // number of aliquotes might have changed - so update record
                        if (sampleService.update(sample) == null) {
                            return Constant.NOT_SUCCESS;
                        }
                        // TODO: only one debug
                        logger.debug("Sample: " + sample.getId() + " was updated in current import");

                        archiveSystem("Sample: " + sample.getId() + " was updated in current import");
                    }
                } catch (DifferentEntityException ex) {
                    logger.error("This must not happened. New sample was not created but it doens't match any sample in DB. " + ex.getMessage());
                    return Constant.NOT_SUCCESS;
                }

            }
        }

        return Constant.SUCCESS;
    }

}
