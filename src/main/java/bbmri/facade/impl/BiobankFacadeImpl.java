package bbmri.facade.impl;

import bbmri.entities.*;
import bbmri.entities.enumeration.Permission;
import bbmri.entities.enumeration.RequestState;
import bbmri.entities.enumeration.SystemRole;
import bbmri.facade.BiobankFacade;
import bbmri.service.*;
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
@Controller
public class BiobankFacadeImpl extends BasicFacade implements BiobankFacade {


    @Autowired
    BiobankService biobankService;

    @Autowired
    RequestGroupService requestGroupService;

    @Autowired
    RequestService requestService;

    @Autowired
    UserService userService;

    @Autowired
    BiobankAdministratorService biobankAdministratorService;

    public List<User> getBiobankAdministrators(Long biobankId) {
        notNull(biobankId);
        Biobank biobankDB = biobankService.get(biobankId);
        List<User> users = new ArrayList<User>();
        for (BiobankAdministrator ba : biobankDB.getBiobankAdministrators()) {
            users.add(ba.getUser());
        }
        return users;
    }


    public Set<BiobankAdministrator> getBiobankAdministrators2(Long biobankId) {
        notNull(biobankId);
        Biobank biobankDB = biobankService.get(biobankId);
        if (biobankDB == null) {
            return null;
            // TODO exception
        }
        return biobankDB.getBiobankAdministrators();
    }


    public void createBiobank(Biobank biobank, Long newAdministratorId) {
        notNull(biobank);
        notNull(newAdministratorId);

//        logger.debug("Facade: Biobank: " + biobank + " id: " + newAdministratorId );
        biobankService.create(biobank, newAdministratorId);

    }

    public void updateBiobank(Biobank biobank) {
        notNull(biobank);
        biobankService.update(biobank);
    }

    public void removeBiobank(Long biobankId) {
        notNull(biobankId);
        biobankService.remove(biobankId);
    }

    public void assignAdministratorToBiobank(Long biobank, Long loggedUser, Long newAdministrator, Permission permission) {

    }

    public void removeAdministratorFromBiobank(Long biobank, Long loggedUser, Long newAdministrator) {

    }

    public void changePermissionOfAdministrator(Long biobank, Long loggedUser, Long newAdministrator, Permission permission) {

    }

    public void changeBiobankAdministratorPermission(Long biobankAdministrator, Permission permission, Long loggedUser) {
        notNull(biobankAdministrator);
        notNull(permission);
        notNull(loggedUser);

        BiobankAdministrator ba = biobankAdministratorService.get(biobankAdministrator);
        if (ba == null) {
            return;
            // TODO: exception
        }

        User userDB = userService.get(loggedUser);
        if (userDB == null) {
            return;
            //TODO: exception
        }
        // TODO: Question: will there be an permission check? Can I remove my own permissions?
        // TODO: There must solved situation of last administrator remove

        ba.setPermission(permission);
        biobankAdministratorService.update(ba);
    }

    public void removeBiobankAdministrator(Long biobankAdministrator, Long loggedUser) {
        notNull(biobankAdministrator);
        notNull(loggedUser);

        BiobankAdministrator ba = biobankAdministratorService.get(biobankAdministrator);
        if (ba == null) {
            return;
            // TODO: exception
        }

        User userDB = userService.get(loggedUser);
        if (userDB == null) {
            return;
            //TODO: exception
        }

        // TODO: There must solved situation of last administrator remove

        biobankAdministratorService.remove(ba.getId());
        userDB = userService.eagerGet(userDB.getId(), false, false, true);
        if(userDB.getBiobankAdministrators().size() < 1){
            // If userDB doesn't manage other Biobank than the deleted one -> remove its system role
            userService.removeSystemRole(userDB.getId(), SystemRole.BIOBANK_OPERATOR);
        }

    }


    public List<RequestGroup> getNewRequestGroups(Long biobankId) {
        notNull(biobankId);
        return requestGroupService.getByBiobankAndState(biobankId, RequestState.NEW);
    }

    public List<Request> getRequests(Long requestGroupId) {
        notNull(requestGroupId);
        RequestGroup rqg = requestGroupService.eagerGet(requestGroupId, true);
        return rqg.getRequests();
    }

    public void approveRequestGroup(Long requestGroupId) {
        notNull(requestGroupId);
        requestGroupService.approveRequestGroup(requestGroupId);
    }

    public void rejectRequestGroup(Long requestGroupId) {
        notNull(requestGroupId);
        requestGroupService.denyRequestGroup(requestGroupId);
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

}
