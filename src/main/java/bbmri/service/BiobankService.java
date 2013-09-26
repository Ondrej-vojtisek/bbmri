package bbmri.service;

import bbmri.entities.AdministratorRole;
import bbmri.entities.Biobank;
import bbmri.entities.Sample;
import bbmri.entities.User;

import java.util.List;

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

   User assignAdministrator(Long userId, Long biobankId, AdministratorRole administratorRole);

   Biobank eagerGet(Long id, boolean samples, boolean requestGroups, boolean sampleQuestions);

   void removeAdministrator(Long loggedUserId, Long userId, Long biobankId, AdministratorRole administratorRole);

   void changeAdministratorPermission(Long loggedUserId, Long userId, Long biobankId, AdministratorRole administratorRole);


}
