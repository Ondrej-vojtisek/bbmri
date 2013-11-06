package bbmri.facade;

import bbmri.entities.*;
import bbmri.entities.enumeration.Permission;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.9.13
 * Time: 11:34
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankFacade {

    List<User> getBiobankAdministrators(Long biobankId);

    Set<BiobankAdministrator> getBiobankAdministrators2(Long biobankId);

    void createBiobank(Biobank biobank, Long newAdministratorId);

    void updateBiobank(Biobank biobank);

    void removeBiobank(Long biobankId);

    void assignAdministratorToBiobank(Long biobank, Long loggedUser, Long newAdministrator, Permission permission);

    void changeBiobankAdministratorPermission(Long biobankAdministrator, Permission permission, Long loggedUser);

    void removeBiobankAdministrator(Long biobankAdministrator, Long loggedUser);

    List<RequestGroup> getNewRequestGroups(Long biobankId);

    List<Request> getRequests(Long requestGroupId);

    void approveRequestGroup(Long requestGroupId);

    void rejectRequestGroup(Long requestGroupId);

    List<Biobank> all();

    boolean hasPermission(Permission permission, Long biobankId, Long userId);

    Biobank get(Long biobankId);

    BiobankAdministrator getBiobankAdministrator(Long id);

}
