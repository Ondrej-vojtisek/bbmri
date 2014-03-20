package cz.bbmri.facade.impl;

import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.entities.infrastructure.*;
import cz.bbmri.facade.BiobankFacade;
import cz.bbmri.service.*;
import cz.bbmri.service.exceptions.DuplicitBiobankException;
import cz.bbmri.service.exceptions.LastManagerException;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 15.10.13
 * Time: 10:16
 * To change this template use File | Settings | File Templates.
 */
@Controller("biobankFacade")
public class BiobankFacadeImpl extends BasicFacade implements BiobankFacade {


    @Autowired
    private BiobankService biobankService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @Autowired
    private BiobankAdministratorService biobankAdministratorService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private SampleQuestionService sampleQuestionService;

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


    public BiobankService getService() {
        return biobankService;
    }

    public boolean createBiobank(Biobank biobank, Long newAdministratorId, ValidationErrors errors, String bbmriPath) {

        notNull(biobank);
        notNull(newAdministratorId);
        notNull(errors);
        notNull(bbmriPath);

        logger.debug("CreateBiobank - controle");

        try {
            biobank = biobankService.create(biobank, newAdministratorId);

            logger.debug("CreateBiobank - created");

        } catch (DuplicitBiobankException ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.duplicitBiobankException"));
            return false;

        } catch (Exception ex) {
            // Return DBG info that something went wrong. In final version there should be logging instead.
            fatalError(errors);
            return false;
        }

        logger.debug("CreateBiobank - create folders");

        // Base folder
        if (!FacadeUtils.folderExists(bbmriPath)) {

            if (FacadeUtils.createFolder(bbmriPath, errors) != SUCCESS) {

                logger.debug("CreateBiobank - Create Base folder failed");

                biobankService.remove(biobank.getId());
                return false;
            }
        }

        // Biobanks folder
        if (!FacadeUtils.folderExists(bbmriPath + Biobank.BIOBANK_FOLDER)) {
            if (FacadeUtils.createFolder(bbmriPath + Biobank.BIOBANK_FOLDER, errors) != SUCCESS) {

                logger.debug("CreateBiobank - Create Biobank folder failed");

                biobankService.remove(biobank.getId());
                return false;
            }
        }

        // Folder for the biobank
        if (FacadeUtils.createFolder(bbmriPath + biobank.getBiobankFolderPath(), errors)
                != SUCCESS) {

            logger.debug("CreateBiobank - Create folder for specific biobank failed");

            biobankService.remove(biobank.getId());
            return false;
        }

        // Folder for the biobank/monitoring_data
        if (FacadeUtils.createFolder(bbmriPath + biobank.getBiobankMonitoringFolder(), errors)!= SUCCESS) {

            logger.debug("CreateBiobank - Create folder for monitoring data failed");

            biobankService.remove(biobank.getId());
            return false;
        }

        // Folder for the biobank/patient_data
        if (FacadeUtils.createFolder(bbmriPath + biobank.getBiobankPatientDataFolder(), errors)
                != SUCCESS) {

            logger.debug("CreateBiobank - Create folder for patient data failed");

            biobankService.remove(biobank.getId());
            return false;
        }

        // Folder for the biobank/monitoring_data_archive
        if (FacadeUtils.createFolder(bbmriPath + biobank.getBiobankMonitoringArchiveFolder(), errors)
                != SUCCESS) {

            logger.debug("CreateBiobank - Create folder for archive of patient data failed");

            biobankService.remove(biobank.getId());
            return false;
        }

        // Folder for the biobank/patient_data_archive
        if (FacadeUtils.createFolder(bbmriPath + biobank.getBiobankPatientArchiveDataFolder(), errors)
                != SUCCESS) {

            logger.debug("CreateBiobank - Create folder for archive of monitoring data failed");

            biobankService.remove(biobank.getId());
            return false;
        }

        return true;
    }


    public boolean updateBiobank(Biobank biobank, ValidationErrors errors, Long loggedUserId) {
        notNull(biobank);
        notNull(errors);
        notNull(loggedUserId);

        try {

            biobankService.update(biobank);

            String msg = "Biobank " + biobank.getName() + " was updated.";

            notificationService.create(getOtherBiobankAdministrators(biobank, loggedUserId),
                    NotificationType.BIOBANK_DETAIL, msg, biobank.getId());

            return true;

        } catch (Exception ex) {

            // Return DBG info that something went wrong. In final version there should be logging instead.

            fatalError(errors);

            return false;
        }
    }

    public boolean removeBiobank(Long biobankId, ValidationErrors errors, String bbmriPath, Long loggedUserId) {
        notNull(biobankId);
        notNull(errors);
        notNull(bbmriPath);

        boolean result = false;

        Biobank biobankDB = biobankService.get(biobankId);

        try {

            if (biobankService.remove(biobankId)) {
                result = FacadeUtils.recursiveDeleteFolder(bbmriPath + biobankDB.getBiobankFolderPath(), errors) == SUCCESS;
            }

        } catch (Exception ex) {

            // Return DBG info that something went wrong. In final version there should be logging instead.
            fatalError(errors);
            return false;

        }

        if (result) {
            String msg = "Biobank " + biobankDB.getName() + " was removed.";

            notificationService.create(getOtherBiobankAdministrators(biobankDB, loggedUserId),
                    NotificationType.BIOBANK_DELETE, msg, biobankDB.getId());
        }

        return result;


    }

    public boolean assignAdministrator(Long objectId,
                                       Long newAdministratorId,
                                       Permission permission,
                                       ValidationErrors errors, Long loggedUserId) {
        notNull(objectId);
        notNull(newAdministratorId);
        notNull(permission);
        notNull(errors);
        notNull(loggedUserId);

        Biobank biobankDB = biobankService.get(objectId);
        User newAdmin = userService.get(newAdministratorId);

        if (biobankDB == null || newAdmin == null) {
            fatalError(errors);
            return false;
        }

        if (biobankAdministratorService.get(objectId, newAdministratorId) != null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.adminAlreadyExists"));
            return false;
        }

        boolean result = false;

        try {
            result = biobankService.assignAdministrator(biobankDB, newAdministratorId, permission);
        } catch (Exception ex) {
            fatalError(errors);
            return false;
        }

        if (result) {

            String msg = "New administrator was assigned to biobank: " + biobankDB.getName() +
                    ". His name is: " + newAdmin.getWholeName() + " and his permission is: " + permission + ". ";

            notificationService.create(getOtherBiobankAdministrators(biobankDB, loggedUserId),
                    NotificationType.BIOBANK_ADMINISTRATOR, msg, biobankDB.getId());

        }

        return result;

    }

    public boolean changeAdministratorPermission(Long objectAdministratorId,
                                                 Permission permission,
                                                 ValidationErrors errors, Long loggedUserId) {
        notNull(objectAdministratorId);
        notNull(permission);
        notNull(errors);
        notNull(errors);

        BiobankAdministrator ba = biobankAdministratorService.get(objectAdministratorId);

        if (ba == null) {
            fatalError(errors);
            return false;
        }

        boolean result = false;

        try {
            result = biobankService.changeAdministratorPermission(ba, permission);
        } catch (LastManagerException ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.service.exceptions.LastBiobankManagerException"));
            return false;
        } catch (Exception ex) {
            fatalError(errors);
            return false;
        }

        if (result) {
            Biobank biobank = ba.getBiobank();

            User user = ba.getUser();

            String msg = "Permission of " + user.getWholeName() + " was changed to: " + permission;

            notificationService.create(getOtherBiobankAdministrators(biobank, loggedUserId),
                    NotificationType.BIOBANK_ADMINISTRATOR, msg, biobank.getId());
        }
        return result;
    }

    public boolean removeAdministrator(Long objectAdministratorId, ValidationErrors errors, Long loggedUserId) {
        notNull(objectAdministratorId);
        notNull(errors);

        BiobankAdministrator ba = biobankAdministratorService.get(objectAdministratorId);
        if (ba == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.dbg.null"));
            return false;
        }

        boolean result = false;

        try {
            result = biobankService.removeAdministrator(ba);
        } catch (LastManagerException ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.service.exceptions.LastBiobankManagerException"));
            return false;
        }

        if (result) {
            Biobank biobank = ba.getBiobank();

            User user = ba.getUser();

            String msg = "Permission of " + user.getWholeName() + " to access biobank " + biobank.getName() + " was removed.";

            notificationService.create(getOtherBiobankAdministrators(biobank, loggedUserId),
                    NotificationType.BIOBANK_ADMINISTRATOR, msg, biobank.getId());
        }

        return result;
    }

    public List<Biobank> all() {
        return biobankService.all();
    }

    public boolean hasPermission(Permission permission, Long objectId, Long userId) {
        notNull(permission);
        notNull(objectId);
        notNull(userId);

        BiobankAdministrator ba = biobankAdministratorService.get(objectId, userId);

        if (ba == null) {
            return false;
        }

        return ba.getPermission().include(permission);
    }

    public Biobank get(Long biobankId) {
        notNull(biobankId);

        return biobankService.get(biobankId);
    }

    public BiobankAdministrator getBiobankAdministrator(Long id) {
        notNull(id);

        return biobankAdministratorService.get(id);
    }


    public List<Biobank> getBiobanksByUser(Long userId) {
        notNull(userId);
        User userDB = userService.get(userId);
        //User userDB = userService.eagerGet(userId, false, false, true, false, false);
        if (userDB == null) {
            return null;
        }
        List<Biobank> biobanks = new ArrayList<Biobank>();
        for (BiobankAdministrator ba : userDB.getBiobankAdministrators()) {
            biobanks.add(ba.getBiobank());
        }
        return biobanks;
    }

//    public List<Patient> getAllPatients(Long biobankId) {
//        notNull(biobankId);
//        Biobank biobankDB = biobankService.eagerGet(biobankId, true, false);
//        return biobankDB.getPatients();
//    }

    public List<Sample> getAllSamples(Long biobankId) {
        notNull(biobankId);

        logger.debug("AllSamples:");

        List<Sample> samples = new ArrayList<Sample>();
//        for(Patient patient : getAllPatients(biobankId)){
//            patient = patientService.eagerGet(patient.getId(), true);
//            samples.addAll(patient.getSamples());
//        }
        // return samples;
        return sampleService.all();
    }

    public List<SampleQuestion> getBiobankSampleRequests(Long biobankId) {
        notNull(biobankId);
        return sampleQuestionService.getSampleRequests(biobankId, null);
    }

    public List<SampleQuestion> getNewSampleRequests(Long biobankId) {
        return sampleQuestionService.getSampleRequests(biobankId, RequestState.NEW);
    }

    public Patient getPatient(Long patientId) {
        return patientService.get(patientId);
    }

    public Infrastructure getInfrastructure(Long infrastructureId) {

        return infrastructureService.get(infrastructureId); //eagerGet(infrastructureId, true, true);
    }

    public Container getContainer(Long containerId) {
        return containerService.get(containerId);
    }

    public Rack getRack(Long rackId) {
        return rackService.get(rackId);
        // return rackService.eagerGet(rackId, true);
    }

    public Box getBox(Long boxId) {
        return boxService.get(boxId);
    }

    public boolean createInfrastructure(Long biobankId) {
        Infrastructure infrastructure = infrastructureService.initialize(biobankService.get(biobankId));
        // This method is not caused intentionally by user so there is no need to create any un-success messages

        if (infrastructure == null) {
            logger.debug("Infrastructure was not created");
            return false;
        }

        return true;
    }

    public boolean createContainer(Long infrastructureId, Container container, ValidationErrors errors) {
        notNull(infrastructureId);
        notNull(container);
        notNull(errors);

        if (containerService.create(infrastructureId, container) == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.containercreatefailed"));
            return false;
        }
        return true;
    }


    public boolean createRack(Long containerId, Rack rack, ValidationErrors errors) {
        notNull(containerId);
        notNull(rack);
        notNull(errors);

        if (rackService.create(containerId, rack) == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.rackcreatefailed"));
            return false;
        }
        return true;
    }

    public boolean createStandaloneBox(Long infrastructureId, StandaloneBox box, ValidationErrors errors) {
        notNull(infrastructureId);
        notNull(box);
        notNull(errors);

        if (boxService.createStandaloneBox(infrastructureId, box) == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.boxcreatefailed"));
            return false;
        }
        return true;
    }

    public boolean createBox(Long rackId, RackBox box, ValidationErrors errors) {
        notNull(rackId);
        notNull(box);
        notNull(errors);

        if (boxService.createRackBox(rackId, box) == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.boxcreatefailed"));
            return false;
        }
        return true;
    }

    public boolean createPatient(Patient patient, Long biobankId, ValidationErrors errors) {
        notNull(patient);
        if (patientService.create(patient, biobankId) == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.patientCreateFailed"));
            return false;
        }
        return true;
    }

    public List<Patient> find(Patient patient, int requiredResults) {
        if (patient == null) {
            return null;
        }
        if (requiredResults < 1) {
            requiredResults = MAXIMUM_FIND_RESULTS;
        }

        return patientService.find(patient, requiredResults);
    }

    public List<Biobank> allOrderedBy(String orderByParam, boolean desc) {
        return biobankService.allOrderedBy(orderByParam, desc);
    }

    public List<Patient> getSortedPatients(Long biobankId, String orderByParam, boolean desc) {
        if (biobankId == null) {
            logger.debug("BiobankId can't be null");
            return null;
        }
        return patientService.getSorted(biobankId, orderByParam, desc);
    }

    public List<Container> getSortedContainers(Long biobankId, String orderByParam, boolean desc) {
        return containerService.getSortedContainers(biobankId, orderByParam, desc);
    }

    public List<Rack> getSortedRacks(Long biobankId, String orderByParam, boolean desc) {
        return rackService.getSortedRacks(biobankId, orderByParam, desc);
    }

    public List<StandaloneBox> getSortedStandAloneBoxes(Long biobankId, String orderByParam, boolean desc) {
        return boxService.getSortedStandAloneBoxes(biobankId, orderByParam, desc);
    }

    public List<RackBox> getSortedStandRackBoxes(Long rackId, String orderByParam, boolean desc) {
        return boxService.getSortedRackBoxes(rackId, orderByParam, desc);
    }

//    @Scheduled(cron = "1 * * * * *")
//    public void checkBiobankFolders(){
//        for(Biobank biobank : all()){
//            List<File> files = FacadeUtils.getFiles(biobank.getBiobankFolderPath());
//            logger.debug("Biobank: " + biobank.getName() + " files: " + files);
//        }
//    }

}
