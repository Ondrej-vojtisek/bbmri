package cz.bbmri.facade.impl;

import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.entities.infrastructure.*;
import cz.bbmri.entities.webEntities.PositionDTO;
import cz.bbmri.facade.AutoTriggeredOperations;
import cz.bbmri.io.MonitoringDataParser;
import cz.bbmri.io.PatientDataParser;
import cz.bbmri.service.*;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.controller.StripesFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 10.4.14
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */
@Controller("autoTriggeredOperations")
public class AutoTriggeredOperationsImpl extends BasicFacade implements AutoTriggeredOperations {

    @Value("${StoragePath}")
    private String storagePath;

    @Autowired
    private BiobankService biobankService;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private InfrastructureService infrastructureService;

    @Autowired
    private ContainerService containerService;

    @Autowired
    private RackService rackService;

    @Autowired
    private BoxService boxService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SampleQuestionService sampleQuestionService;

    @Autowired
    private RequestService requestService;

    private void logger(String msg) {
        logger.debug("CRON fired at: " + new Date() + " with: " + msg);
    }

    // TODO - update to lower frequency
    // triggers at 0:05 each day
    //@Scheduled(cron = "5 0 * * * *")
    //@Scheduled(cron = "1 * * * * *")
    public void checkBiobankMonitoringData() {
        logger("method checkBiobankMonitoringData");

        for (Biobank biobank : biobankService.all()) {

            logger.debug("Biobank: " + biobank.getName());

            // Scan all patient data files in biobank folder
            List<File> files = FacadeUtils.getFiles(storagePath + biobank.getBiobankMonitoringFolder());

            for (File file : files) {
                logger.debug("Biobank: " + biobank.getName() + " file: " + file);

                if (parseMonitoringImport(file.getPath(), biobank) != SUCCESS) {
                    logger.debug("Parse of file : " + file + " failed. ");

                    // Don't copy and remove file in case that something went wrong
                    continue;
                }

//                copy file if parsing was correct
                //        if (FacadeUtils.copyFile(file, storagePath + biobank.getBiobankMonitoringArchiveFolder()) == SUCCESS) {
//                delete file if copy succeeded
                //        FacadeUtils.deleteFile(file);
                //    }

            }
        }
    }

    // triggers at 0:01 each day
    //@Scheduled(cron = "1 0 * * * *")
    //@Scheduled(cron = "1 * * * * *")
    public void checkBiobankPatientData() {

        logger.debug("Cron fired method checkBiobankPatientData");

        for (Biobank biobank : biobankService.all()) {

            logger.debug("Biobank: " + biobank.getName());

            // Scan all patient data files in biobank folder
            List<File> files = FacadeUtils.getFiles(storagePath + biobank.getBiobankPatientDataFolder());

            for (File file : files) {
                logger.debug("Biobank: " + biobank.getName() + " file: " + file);

                if (parsePatientImport(file.getPath(), biobank) != SUCCESS) {
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

    private int parseMonitoringImport(String path, Biobank biobank) {

        logger.debug("ParseMonitoringImport");

        MonitoringDataParser parser = null;
        try {
            parser = new MonitoringDataParser(path);
        } catch (Exception ex) {
            logger.debug("MonitoringDataParser failed");
            ex.printStackTrace();
            return NOT_SUCCESS;
        }

        if (!parser.validate()) {
            logger.debug("Document is NOT valid. Document path was: " + path);
            return NOT_SUCCESS;
        }

        String biobankName = parser.getBiobankId();

        logger.debug("BiobankName: " + biobankName);

        if (!biobankName.equals(biobank.getName())) {
            logger.debug("Biobank identifier doesn't match");
            return NOT_SUCCESS;
        }

        // Parse standalone boxes
        for (Box box : parser.getStandaloneBoxes()) {
            Box boxDB = boxService.getBoxByName(biobank, null, box.getName());

            if (boxDB == null) {
                StandaloneBox boxNew = new StandaloneBox();
                boxNew.setCapacity(box.getCapacity());
                boxNew.setName(box.getName());
                boxNew.setTempMax(box.getTempMax());
                boxNew.setTempMin(box.getTempMin());

                box = boxService.createStandaloneBox(biobank.getInfrastructure().getId(), boxNew);
            } else {
                box.setId(boxDB.getId());
                box = boxService.update(box);
            }

            // for standalonebox container and rack is null
            if (parseBoxPositions(parser, biobank, null, null, box) != SUCCESS) {
                logger.debug("Parse positions failed");
                return NOT_SUCCESS;
            }


        }

        // Parse containers
        for (Container container : parser.getContainers()) {

            // Is the container already present in DB?
            Container containerDB = containerService.getContainerByName(biobank, container.getName());

            // container is not in DB
            if (containerDB == null) {

                if (biobank.getInfrastructure() == null) {
                    logger.debug("Infrastructure of biobank must not be null");
                    return NOT_SUCCESS;
                }

                // create it
                container = containerService.create(biobank.getInfrastructure().getId(), container);
            } else {
                // container is present
                // set id for update method to recognize that container and containerDB are the during update
                container.setId(containerDB.getId());
                container = containerService.update(container);
            }

            // Parse racks
            for (Rack rack : parser.getRacks(container)) {

                // Is the rack already present in DB?
                Rack rackDB = rackService.getRackByName(container, rack.getName());

                // rack in not in DB
                if (rackDB == null) {
                    // create it
                    rack = rackService.create(container.getId(), rack);
                } else {
                    // set id to make rack and rackDB equal during update
                    rack.setId(rackDB.getId());
                    rack = rackService.update(rack);
                }

                // Parse rackBoxes
                for (Box box : parser.getRackBoxes(container, rack)) {

                    // Is the box already present in DB?
                    RackBox boxDB = (RackBox) boxService.getBoxByName(biobank, rack, box.getName());
                    if (boxDB == null) {
                        // create new rackBox
                        RackBox rackBox = new RackBox();
                        rackBox.setCapacity(box.getCapacity());
                        rackBox.setName(box.getName());
                        rackBox.setTempMin(box.getTempMin());
                        rackBox.setTempMax(box.getTempMax());

                        box = boxService.createRackBox(rack.getId(), rackBox);
                    } else {
                        // set id to make box and boxDB equal during update
                        box.setId(boxDB.getId());
                        box = boxService.update(box);
                    }

                    if (parseBoxPositions(parser, biobank, container, rack, box) != SUCCESS) {
                        logger.debug("Parse positions failed");
                        return NOT_SUCCESS;
                    }
                }
            }
        }


        return SUCCESS;
    }

    private int parseBoxPositions(MonitoringDataParser parser, Biobank biobank, Container container,
                                  Rack rack, Box box) {
        // Parse positions of box
        for (PositionDTO positionDTO : parser.getBoxPositions(biobank, container, rack, box)) {

            // Is this position in DB
            Position positionDB = positionService.getByCoordinates(box,
                    positionDTO.getSequentialPosition(), positionDTO.getColumn(), positionDTO.getRow());

            Sample sampleDB = sampleService.getByInstitutionalId(positionDTO.getSampleId());

            if (sampleDB == null) {
                logger.debug("Sample identifier doesn't match");
                return NOT_SUCCESS;
            }

            // Create new position from DTO
            Position positionNew = new Position();
            positionNew.setColumn(positionDTO.getColumn());
            positionNew.setRow(positionDTO.getRow());
            positionNew.setSequentialPosition(positionDTO.getSequentialPosition());

            if (positionDB == null) {
                positionNew = positionService.create(positionNew, box.getId(), sampleDB.getId());
            } else {
                positionNew.setId(positionDB.getId());
                positionNew = positionService.update(positionNew);
            }
        }
        return SUCCESS;
    }

    private int parsePatientImport(String path, Biobank biobank) {

        logger.debug("ParsePatientImport");

        PatientDataParser parser = null;
        try {
            parser = new PatientDataParser(path);
        } catch (Exception ex) {
            logger.debug("PatientDataParser failed");
            ex.printStackTrace();
            return NOT_SUCCESS;
        }

        if (!parser.validate()) {
            logger.debug("Document is NOT valid. Document path was: " + path);
            return NOT_SUCCESS;
        }

        String biobankAbbreviation = parser.getBiobankId();
        if (!biobankAbbreviation.equals(biobank.getAbbreviation())) {
            logger.debug("Biobank abbreviation (identifier in export) doesn't match");
            return NOT_SUCCESS;
        }

        Patient patient = parser.getPatient();

        if (patient == null) {
            logger.debug("Patient is null");
            return NOT_SUCCESS;
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
            return NOT_SUCCESS;
        }

        // Parse LTS module
        List<Sample> samples = parser.getPatientLtsSamples();
        if (samples == null) {
            logger.debug("Parse of LTS module from import failed");
            return NOT_SUCCESS;
        }

        if (manageImportedSamples(samples, patient.getModuleLTS()) != SUCCESS) {
            logger.debug("ManageImportedSamples for LTS module failed");
            return NOT_SUCCESS;
        }

        // Parse STS module
        samples = parser.getPatientStsSamples();
        if (samples == null) {
            logger.debug("Parse of STS module from import failed");
            return NOT_SUCCESS;
        }
        if (manageImportedSamples(samples, patient.getModuleSTS()) != SUCCESS) {
            logger.debug("ManageImportedSamples for STS module failed");
            return NOT_SUCCESS;
        }

        return SUCCESS;
    }

    private int manageImportedSamples(List<Sample> samples, Module module) {

        if (module == null) {
            logger.debug("Module must not be null");
            return NOT_SUCCESS;
        }

        for (Sample sample : samples) {

            if (sample.getSampleIdentification() == null) {
                return NOT_SUCCESS;
            }
            if (sample.getSampleIdentification().getSampleId() == null) {
                return NOT_SUCCESS;
            }

            // Set module
            sample.setModule(module);

            Sample sampleDB = sampleService.getByInstitutionalId(sample.getSampleIdentification().getSampleId());

            if (sampleDB == null) {
                sampleService.create(sample, sample.getModule().getId());
            } else {
                // set primary identifier
                sample.setId(sampleDB.getId());
                // number of aliquotes might have changed - so update record
                sampleService.update(sample);
            }
        }

        return SUCCESS;
    }

    private void setReservationAsExpired(SampleReservation sampleReservation) {
           sampleReservation.setRequestState(RequestState.EXPIRED);
           sampleQuestionService.update(sampleReservation);

           // delete all request - alocated samples are free
           for (Request request : sampleReservation.getRequests()) {
               requestService.remove(request.getId());
           }
       }

       // triggers at 0:10 each day
       @Scheduled(cron = "10 0 * * * *")
       public void checkReservationValidity() {
           logger.debug("CRON - checkReservationValidity auto triggered at: " + new Date());

           Date date = new Date();
           boolean firstValid = false;
           for (SampleReservation sampleReservation : sampleQuestionService.getSampleReservationsOrderedByDate()) {
               // if this date (today) is after sampleReservation.getValidity
               // then validity is over

               if (firstValid) break;

               if (date.after(sampleReservation.getValidity())) {
                   setReservationAsExpired(sampleReservation);

                   LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.AutoTriggeredOperationsImpl.reservationExpired");

                   notificationService.create(sampleReservation.getUser().getId(),
                           NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleReservation.getId());
               } else {
                   // reservations are sorted from oldest to newest
                   // if first one is valid, that all next will be also valid
                   firstValid = true;
               }

           }


       }


}
