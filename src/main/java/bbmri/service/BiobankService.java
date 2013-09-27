package bbmri.service;

import bbmri.entities.Permission;
import bbmri.entities.Biobank;
import bbmri.entities.User;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankService  extends BasicService<Biobank> {

   Biobank create(Biobank biobank, Long administratorId);

   //List<Sample> getAllSamples(Long biobankId);

   User removeAdministratorFromBiobank(Long userId, Long biobankId);

  // List<User> getAllAdministrators(Long biobankId);

   User assignAdministrator(Long userId, Long biobankId, Permission permission);

   Biobank eagerGet(Long id, boolean samples, boolean requestGroups, boolean sampleQuestions);

   void removeAdministrator(Long loggedUserId, Long userId, Long biobankId, Permission permission);

   void changeAdministratorPermission(Long loggedUserId, Long userId, Long biobankId, Permission permission);


}
