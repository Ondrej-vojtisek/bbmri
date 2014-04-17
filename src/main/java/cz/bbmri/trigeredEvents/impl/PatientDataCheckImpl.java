package cz.bbmri.trigeredEvents.impl;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Module;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.constant.Constant;
import cz.bbmri.io.PatientDataParser;
import cz.bbmri.service.BiobankService;
import cz.bbmri.service.PatientService;
import cz.bbmri.service.SampleService;
import cz.bbmri.service.impl.ServiceUtils;
import cz.bbmri.trigeredEvents.PatientDataCheck;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class PatientDataCheckImpl extends Basic implements PatientDataCheck {

    @Autowired
    private BiobankService biobankService;

    @Autowired
    private PatientService patientService;


    @Autowired
    private SampleService sampleService;


    // triggers at 0:01 each day
    //@Scheduled(cron = "1 0 * * * *")
    //@Scheduled(cron = "1 * * * * *")
    public void checkBiobankPatientData() {

        logger.debug("Cron fired method checkBiobankPatientData");

        for (Biobank biobank : biobankService.all()) {

            logger.debug("Biobank: " + biobank.getName());

            // Scan all patient data files in biobank folder
            List<File> files = ServiceUtils.getFiles(storagePath + biobank.getBiobankPatientDataFolder());

            for (File file : files) {
                logger.debug("Biobank: " + biobank.getName() + " file: " + file);

                if (parsePatientImport(file.getPath(), biobank) != Constant.SUCCESS) {
                    logger.debug("Parse of file : " + file + " failed. ");

                    // Don't copy and remove file in case that something went wrong
                    continue;
                }

//                copy file if parsing was correct
                //   if (FacadeUtils.copyFile(file, storagePath + biobank.getBiobankPatientArchiveDataFolder()) == SUCCESS) {
//                delete file if copy succeeded
                //        FacadeUtils.deleteFile(file);
                //    }

            }
        }
    }

    private int parsePatientImport(String path, Biobank biobank) {

        logger.debug("ParsePatientImport");

        PatientDataParser parser;
        try {
            parser = new PatientDataParser(path);
        } catch (Exception ex) {
            logger.debug("PatientDataParser failed");
            ex.printStackTrace();
            return Constant.NOT_SUCCESS;
        }

        if (!parser.validate()) {
            logger.debug("Document is NOT valid. Document path was: " + path);
            return Constant.NOT_SUCCESS;
        }

        String biobankAbbreviation = parser.getBiobankId();
        if (!biobankAbbreviation.equals(biobank.getAbbreviation())) {
            logger.debug("Biobank abbreviation (identifier in export) doesn't match");
            return Constant.NOT_SUCCESS;
        }

        Patient patient = parser.getPatient();

        if (patient == null) {
            logger.debug("Patient is null");
            return Constant.NOT_SUCCESS;
        }
        Patient patientDB = patientService.getByInstitutionalId(patient.getInstitutionId());
        if (patientDB == null) {
            // Patient is new
            patient = patientService.create(patient, biobank.getId());
            patient = patientService.get(patient.getId());
        } else {
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

        if (manageImportedSamples(samples, patient.getModuleLTS()) != Constant.SUCCESS) {
            logger.debug("ManageImportedSamples for LTS module failed");
            return Constant.NOT_SUCCESS;
        }

        // Parse STS module
        samples = parser.getPatientStsSamples();
        if (samples == null) {
            logger.debug("Parse of STS module from import failed");
            return Constant.NOT_SUCCESS;
        }
        if (manageImportedSamples(samples, patient.getModuleSTS()) != Constant.SUCCESS) {
            logger.debug("ManageImportedSamples for STS module failed");
            return Constant.NOT_SUCCESS;
        }

        return Constant.SUCCESS;
    }

    private int manageImportedSamples(List<Sample> samples, Module module) {

        if (module == null) {
            logger.debug("Module must not be null");
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
                sampleService.create(sample, sample.getModule().getId(), null);
            } else {
                // set primary identifier
                sample.setId(sampleDB.getId());
                // number of aliquotes might have changed - so update record
                if (sampleService.update(sample) == null) {
                    return Constant.NOT_SUCCESS;
                }
            }
        }

        return Constant.SUCCESS;
    }

}
