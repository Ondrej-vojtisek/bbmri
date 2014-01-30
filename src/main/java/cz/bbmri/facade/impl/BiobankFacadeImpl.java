package cz.bbmri.facade.impl;

import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.facade.BiobankFacade;
import cz.bbmri.service.*;
import cz.bbmri.service.exceptions.DuplicitBiobankException;
import cz.bbmri.service.exceptions.LastManagerException;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private RequestGroupService requestGroupService;

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

    public boolean createBiobank(Biobank biobank, Long newAdministratorId, ValidationErrors errors, String bbmriPath) {

        notNull(biobank);
        notNull(newAdministratorId);
        notNull(errors);
        notNull(bbmriPath);

        logger.debug("CreateBiobank - controle") ;

        try {
            biobank = biobankService.create(biobank, newAdministratorId);

            logger.debug("CreateBiobank - created") ;

        } catch (DuplicitBiobankException ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.duplicitBiobankException"));
            return false;

        } catch (Exception ex) {
            // Return DBG info that something went wrong. In final version there should be logging instead.
            fatalError(errors);
            return false;
        }

        logger.debug("CreateBiobank - create folders") ;

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
        if (FacadeUtils.createFolder(bbmriPath + Biobank.BIOBANK_FOLDER_PATH + biobank.getId().toString(), errors) != SUCCESS) {

            logger.debug("CreateBiobank - Create folder for specific biobank failed");

            biobankService.remove(biobank.getId());
            return false;
        }

        return true;
    }

    private List<User> getOtherProjectWorkers(Biobank biobank, Long excludedUserId) {
        Biobank biobankDB = biobankService.eagerGet(biobank.getId(), false, false, false);
        Set<BiobankAdministrator> biobankAdministrators = biobankDB.getBiobankAdministrators();
        BiobankAdministrator baExclude = biobankAdministratorService.get(biobank.getId(), excludedUserId);
        biobankAdministrators.remove(baExclude);

        List<User> users = new ArrayList<User>();
        for (BiobankAdministrator ba : biobankAdministrators) {
            users.add(ba.getUser());
        }

        return users;
    }

    public boolean updateBiobank(Biobank biobank, ValidationErrors errors, Long loggedUserId) {
        notNull(biobank);
        notNull(errors);
        notNull(loggedUserId);

        try {

            biobankService.update(biobank);

            String msg = "Biobank " + biobank.getName() + " was updated.";

            notificationService.create(getOtherProjectWorkers(biobank, loggedUserId),
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
                result = FacadeUtils.recursiveDeleteFolder(bbmriPath + Biobank.BIOBANK_FOLDER_PATH + biobankId.toString(), errors) == SUCCESS;
            }

        } catch (Exception ex) {

            // Return DBG info that something went wrong. In final version there should be logging instead.
            fatalError(errors);
            return false;

        }

        if (result) {
            String msg = "Biobank " + biobankDB.getName() + " was removed.";

            notificationService.create(getOtherProjectWorkers(biobankDB, loggedUserId),
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

            notificationService.create(getOtherProjectWorkers(biobankDB, loggedUserId),
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

            notificationService.create(getOtherProjectWorkers(biobank, loggedUserId),
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

            notificationService.create(getOtherProjectWorkers(biobank, loggedUserId),
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

        User userDB = userService.eagerGet(userId, false, false, true, false);
        if (userDB == null) {
            return null;
        }
        List<Biobank> biobanks = new ArrayList<Biobank>();
        for (BiobankAdministrator ba : userDB.getBiobankAdministrators()) {
            biobanks.add(ba.getBiobank());
        }
        return biobanks;
    }

    public List<Patient> getAllPatients(Long biobankId){
        notNull(biobankId);
        Biobank biobankDB = biobankService.eagerGet(biobankId, true, false, false);
        return biobankDB.getPatients();
    }

    public List<Sample> getAllSamples(Long biobankId){
        notNull(biobankId);
        List<Sample> samples = new ArrayList<Sample>();
        for(Patient patient : getAllPatients(biobankId)){
            patient = patientService.eagerGet(patient.getId(), true);
            samples.addAll(patient.getSamples());
        }
        return samples;
    }

    public List<SampleRequest> getBiobankSampleRequests(Long biobankId){
        notNull(biobankId);

        Biobank biobankDB = biobankService.eagerGet(biobankId, false, false, true);
        return biobankDB.getSampleRequests();
    }

    /*
        public List<RequestGroup> getNewRequestGroups(Long biobankId) {
            notNull(biobankId);
            return requestGroupService.getByBiobankAndState(biobankId, RequestState.NEW);
        }
    */


//    public List<Request> getRequests(Long requestGroupId) {
//        notNull(requestGroupId);
//        RequestGroup rqg = requestGroupService.eagerGet(requestGroupId, true);
//        return rqg.getRequests();
//    }
//
//    public void approveRequestGroup(Long requestGroupId) {
//        notNull(requestGroupId);
//        requestGroupService.approveRequestGroup(requestGroupId);
//    }
//
//    public void rejectRequestGroup(Long requestGroupId) {
//        notNull(requestGroupId);
//        requestGroupService.denyRequestGroup(requestGroupId);
//    }


}
