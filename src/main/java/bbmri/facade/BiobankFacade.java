package bbmri.facade;

import bbmri.entities.*;
import bbmri.entities.enumeration.Permission;
import net.sourceforge.stripes.action.Message;
import net.sourceforge.stripes.validation.ValidationError;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.9.13
 * Time: 11:34
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankFacade extends PermissionFacade {

    boolean createBiobank(Biobank biobank, Long newAdministratorId, ValidationErrors errors, String bbmriPath);

    boolean updateBiobank(Biobank biobank, ValidationErrors errors);

    boolean removeBiobank(Long biobankId, ValidationErrors errors, String bbmriPath);

    List<Biobank> all();

    Biobank get(Long biobankId);

    BiobankAdministrator getBiobankAdministrator(Long id);

    List<Biobank> getBiobanksByUser(Long userId);

    // List<RequestGroup> getNewRequestGroups(Long biobankId);

    // List<Request> getRequests(Long requestGroupId);

    // void approveRequestGroup(Long requestGroupId);

    // void rejectRequestGroup(Long requestGroupId);

}
