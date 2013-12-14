package cz.bbmri.facade.impl;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.User;
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

    public boolean createBiobank(Biobank biobank, Long newAdministratorId, ValidationErrors errors, String bbmriPath) {
        notNull(biobank);
        notNull(newAdministratorId);
        notNull(errors);
        notNull(bbmriPath);

        try {

            biobank = biobankService.create(biobank, newAdministratorId);

        } catch (DuplicitBiobankException ex) {

            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.duplicitBiobankException"));
            return false;

        } catch (Exception ex) {

            // Return DBG info that something went wrong. In final version there should be logging instead.
            fatalError(errors);
            return false;
        }

        // Base folder

        if (!FacadeUtils.folderExists(bbmriPath)) {
            if (FacadeUtils.createFolder(bbmriPath, errors) != SUCCESS) {
                biobankService.remove(biobank.getId());
                return false;
            }
        }

        // Biobanks folder

        if (!FacadeUtils.folderExists(bbmriPath + Biobank.BIOBANK_FOLDER)) {
            if (FacadeUtils.createFolder(bbmriPath + Biobank.BIOBANK_FOLDER, errors) != SUCCESS) {
                biobankService.remove(biobank.getId());
                return false;
            }
        }

        // Folder for the biobank

        if (FacadeUtils.createFolder(bbmriPath + Biobank.BIOBANK_FOLDER_PATH + biobank.getId().toString(), errors) < 0) {
            biobankService.remove(biobank.getId());
            return false;
        }

        return true;
    }

    public boolean updateBiobank(Biobank biobank, ValidationErrors errors) {
        notNull(biobank);
        notNull(errors);

        try {

            biobankService.update(biobank);
            return true;

        } catch (Exception ex) {

            // Return DBG info that something went wrong. In final version there should be logging instead.

            fatalError(errors);

            return false;
        }
    }

    public boolean removeBiobank(Long biobankId, ValidationErrors errors, String bbmriPath) {
        notNull(biobankId);
        notNull(errors);
        notNull(bbmriPath);

        try {

            if(biobankService.remove(biobankId)){
                return FacadeUtils.recursiveDeleteFolder(bbmriPath + Biobank.BIOBANK_FOLDER_PATH + biobankId.toString(), errors) == SUCCESS;
            }

            return false;

        } catch (Exception ex) {

            // Return DBG info that something went wrong. In final version there should be logging instead.
            fatalError(errors);
            return false;

        }


    }

    public boolean assignAdministrator(Long objectId,
                                       Long newAdministratorId,
                                       Permission permission,
                                       ValidationErrors errors) {
        notNull(objectId);
        notNull(newAdministratorId);
        notNull(permission);
        notNull(errors);

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

        try {
            biobankService.assignAdministrator(biobankDB, newAdministratorId, permission);
            return true;
        } catch (Exception ex) {
            fatalError(errors);
            return false;
        }

    }

    public boolean changeAdministratorPermission(Long objectAdministratorId,
                                                 Permission permission,
                                                 ValidationErrors errors) {
        notNull(objectAdministratorId);
        notNull(permission);
        notNull(errors);

        BiobankAdministrator ba = biobankAdministratorService.get(objectAdministratorId);

        if (ba == null) {
            fatalError(errors);
            return false;
        }

        try {
            biobankService.changeAdministratorPermission(ba, permission);
            return true;
        } catch (LastManagerException ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.service.exceptions.LastBiobankManagerException"));
            return false;
        } catch (Exception ex) {
            fatalError(errors);
            return false;
        }
    }

    public boolean removeAdministrator(Long objectAdministratorId, ValidationErrors errors) {
        notNull(objectAdministratorId);
        notNull(errors);

        BiobankAdministrator ba = biobankAdministratorService.get(objectAdministratorId);
        if (ba == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.dbg.null"));
            return false;
        }
        try {
            biobankService.removeAdministrator(ba);
            return true;
        } catch (LastManagerException ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.service.exceptions.LastBiobankManagerException"));
            return false;
        }
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

        User userDB = userService.eagerGet(userId, false, false, true);
        if (userDB == null) {
            return null;
        }
        List<Biobank> biobanks = new ArrayList<Biobank>();
        for (BiobankAdministrator ba : userDB.getBiobankAdministrators()) {
            biobanks.add(ba.getBiobank());
        }
        return biobanks;
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
