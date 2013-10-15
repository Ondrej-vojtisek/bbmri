package bbmri.facade.impl;

import bbmri.entities.*;
import bbmri.entities.enumeration.Permission;
import bbmri.entities.enumeration.RequestState;
import bbmri.facade.BiobankFacade;
import bbmri.service.*;
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


    public List<BiobankAdministrator> getBiobankAdministrators2(Long biobankId) {
        notNull(biobankId);
        Biobank biobankDB = biobankService.get(biobankId);
        if (biobankDB == null) {
            return null;
            // TODO exception
        }
        return biobankDB.getBiobankAdministrators();
    }


    public void createBiobank(Biobank biobank, Long loggedUserId) {
        notNull(biobank);
        notNull(loggedUserId);
        biobankService.create(biobank, loggedUserId);

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

        if(ba == null){
            return false;
        }

        return ba.getPermission().include(permission);
    }

    public Biobank get(Long biobankId){
        notNull(biobankId);
        return biobankService.get(biobankId);
    }

}
