package bbmri.facade.impl;

import bbmri.entities.*;
import bbmri.entities.enumeration.Permission;
import bbmri.facade.BiobankFacade;
import bbmri.service.*;
import bbmri.service.exceptions.LastManagerException;
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
@Controller
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

//    public List<User> getBiobankAdministrators(Long biobankId) {
//        notNull(biobankId);
//        Biobank biobankDB = biobankService.get(biobankId);
//        List<User> users = new ArrayList<User>();
//        for (BiobankAdministrator ba : biobankDB.getBiobankAdministrators()) {
//            users.add(ba.getUser());
//        }
//        return users;
//    }


//    public Set<BiobankAdministrator> getBiobankAdministrators2(Long biobankId, ValidationErrors errors) {
//        notNull(biobankId);
//        Biobank biobankDB = biobankService.get(biobankId);
//        if (biobankDB == null) {
//            errors.addGlobalError(new LocalizableError("bbmri.facade.impl.BasicFacade.dbg.null"));
//            return null;
//        }
//        return biobankDB.getBiobankAdministrators();
//    }


    public boolean createBiobank(Biobank biobank, Long newAdministratorId, ValidationErrors errors) {
        notNull(biobank);
        notNull(newAdministratorId);
        try {
            biobankService.create(biobank, newAdministratorId);
            return true;
        } catch (Exception ex) {

            // Return DBG info that something went wrong. In final version there should be logging instead.
            fatalError(errors);
            return false;
        }

    }

    public boolean updateBiobank(Biobank biobank, ValidationErrors errors) {
        notNull(biobank);
        try {
            biobankService.update(biobank);
            return true;
        } catch (Exception ex) {

            // Return DBG info that something went wrong. In final version there should be logging instead.

            fatalError(errors);

            return false;
        }
    }

    public boolean removeBiobank(Long biobankId, ValidationErrors errors) {
        notNull(biobankId);
        try {
            biobankService.remove(biobankId);
            return true;
        } catch (Exception ex) {
            // Return DBG info that something went wrong. In final version there should be logging instead.
            fatalError(errors);
            return false;
        }
    }

    public boolean assignAdministratorToBiobank(Long biobank, Long loggedUser,
                                                Long newAdministrator,
                                                Permission permission,
                                                ValidationErrors errors) {
        notNull(biobank);
        notNull(loggedUser);
        notNull(newAdministrator);
        notNull(permission);

        Biobank biobankDB = biobankService.get(biobank);
        User logged = userService.get(loggedUser);
        User newAdmin = userService.get(newAdministrator);

        if (biobankDB == null || logged == null || newAdmin == null) {
            fatalError(errors);
            return false;
        }

        if (biobankAdministratorService.get(biobank, newAdministrator) != null) {
            errors.addGlobalError(new LocalizableError("bbmri.facade.impl.BiobankFacadeImpl.adminAlreadyExists"));
            return false;
        }

        try {
            biobankService.assignAdministrator(biobankDB, newAdministrator, permission);
            return true;
        } catch (Exception ex) {
            fatalError(errors);
            return false;
        }

    }

    public boolean changeBiobankAdministratorPermission(Long biobankAdministratorId,
                                                        Permission permission,
                                                        ValidationErrors errors) {
       notNull(biobankAdministratorId);
        notNull(permission);

        BiobankAdministrator ba = biobankAdministratorService.get(biobankAdministratorId);

        if(ba == null){
            fatalError(errors);
            return false;
        }

        try {
            biobankService.changeAdministratorPermission(ba, permission);
            return true;
        } catch (LastManagerException ex) {
            errors.addGlobalError(new LocalizableError("bbmri.service.exceptions.LastBiobankManagerException"));
            return true;
        } catch (Exception ex) {
            fatalError(errors);
            return false;
        }
    }

    public boolean removeBiobankAdministrator(Long biobankAdministrator, ValidationErrors errors) {
        notNull(biobankAdministrator);

        BiobankAdministrator ba = biobankAdministratorService.get(biobankAdministrator);
        if (ba == null) {
            errors.addGlobalError(new LocalizableError("bbmri.facade.impl.BasicFacade.dbg.null"));
            return false;
        }
        try {
            biobankService.removeAdministrator(ba);
            return true;
        } catch (LastManagerException ex) {
            errors.addGlobalError(new LocalizableError("bbmri.service.exceptions.LastBiobankManagerException"));
            return false;
        }
    }

    public List<Biobank> all() {
        return biobankService.all();
    }

    public boolean hasPermission(Permission permission, Long biobankId, Long userId) {
        notNull(permission);
        notNull(biobankId);
        notNull(userId);

        BiobankAdministrator ba = biobankAdministratorService.get(biobankId, userId);

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
