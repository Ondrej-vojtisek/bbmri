package bbmri.service;

import bbmri.entities.Biobank;
import bbmri.entities.BiobankAdministrator;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankService  extends BasicService<Biobank>, PermissionService<Biobank, BiobankAdministrator> {

   Biobank create(Biobank biobank, Long administratorId);

 //  void removeAdministratorFromBiobank(Long biobankId, Long userId) throws LastManagerException;

 //  User assignAdministrator(Long userId, Long biobankId, Permission permission);

   Biobank eagerGet(Long id, boolean samples, boolean requestGroups, boolean sampleQuestions);

  // void changeAdministratorPermission(Long userId, Long biobankId, Permission permission) throws LastManagerException;


}
