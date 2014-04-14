package cz.bbmri.service;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.service.exceptions.DuplicitBiobankException;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankService extends BasicService<Biobank>, PermissionService<Biobank, BiobankAdministrator> {


    List<Biobank> allOrderedBy(String orderByParam, boolean desc);

    Biobank create(Biobank biobank, Long administratorId) throws DuplicitBiobankException;

    Biobank getBiobankByAbbreviation(String abbreviation);

    boolean hasPermission(Permission permission, Long objectId, Long userId);

    boolean changeAdministratorPermission(Long objectAdministratorId, Permission permission, ValidationErrors errors,
                                          Long loggedUserId);

    boolean removeAdministrator(Long objectAdministratorId, ValidationErrors errors, Long loggedUserId);

    boolean assignAdministrator(Long objectId, Long newAdministratorId, Permission permission, ValidationErrors errors,
                                Long loggedUserId);

    boolean createBiobank(Biobank biobank, Long newAdministratorId, ValidationErrors errors);

    boolean updateBiobank(Biobank biobank, ValidationErrors errors, Long loggedUserId);

    boolean removeBiobank(Long biobankId, ValidationErrors errors, Long loggedUserId);

}
