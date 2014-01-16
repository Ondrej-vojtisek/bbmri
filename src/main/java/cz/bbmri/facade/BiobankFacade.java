package cz.bbmri.facade;

import cz.bbmri.entities.*;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.9.13
 * Time: 11:34
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankFacade extends PermissionFacade {

    boolean createBiobank(Biobank biobank, Long newAdministratorId, ValidationErrors errors, String bbmriPath);

    boolean updateBiobank(Biobank biobank, ValidationErrors errors, Long loggedUserId);

    boolean removeBiobank(Long biobankId, ValidationErrors errors, String bbmriPath, Long loggedUserId);

    List<Biobank> all();

    Biobank get(Long biobankId);

    BiobankAdministrator getBiobankAdministrator(Long id);

    List<Biobank> getBiobanksByUser(Long userId);

    List<Patient> getAllPatients(Long biobankId);

    List<Sample> getAllSamples(Long biobankId);

    // List<RequestGroup> getNewRequestGroups(Long biobankId);

    // List<Request> getRequests(Long requestGroupId);

    // void approveRequestGroup(Long requestGroupId);

    // void rejectRequestGroup(Long requestGroupId);

}
