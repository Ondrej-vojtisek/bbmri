package tests.daoAndServiceTest;

import bbmri.entities.*;
import bbmri.entities.enumeration.Permission;
import bbmri.entities.enumeration.SystemRole;
import bbmri.service.BiobankAdministratorService;
import bbmri.service.BiobankService;
import bbmri.service.SampleService;
import bbmri.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 28.8.13
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */
public class BiobankComplexTest extends AbstractDaoAndServiceTest {

    @Autowired
    private BiobankService biobankService;

    @Autowired
    private UserService userService;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private BiobankAdministratorService biobankAdministratorService;

    @Test
    public void createBiobankTest() {
        /* Relationship is usable from both sides */

        /* ********* GIVEN ********** */

        Biobank biobank = createTestBiobank(1);
        User user = createTestUser(1);
        userService.create(user);

        /* ********* WHEN ********** */

        biobankService.create(biobank, user.getId());

        /* ********* THEN ********** */


        user = userService.get(user.getId());
        biobank = biobankService.eagerGet(biobank.getId(), false, false, false);
        BiobankAdministrator ba = biobankAdministratorService.get(biobank.getId(), user.getId());

        assertEquals(true, ba.getBiobank().equals(biobank));
        assertEquals(Permission.MANAGER, ba.getPermission());
        assertEquals(true, user.getSystemRoles().contains(SystemRole.BIOBANK_OPERATOR));
    }

    @Test
    public void createBiobankTest2() {
        /* User not present in DB */

        /* ********* GIVEN ********** */

        User user = createTestUser(1);
        Biobank biobank = createTestBiobank(1);
        userService.create(user);

        /* ********* WHEN ********** */

        biobankService.create(biobank, new Long(5));

         /* ********* THEN ********** */

        assertEquals(true, biobankService.all().isEmpty());
    }

    @Test
    public void removeBiobankTest() {
        /* Test if remove method involves second side of relationship */

        /* ********* GIVEN ********** */

        Biobank biobank = createTestBiobank(1);
        User user = createTestUser(1);
        userService.create(user);
        biobankService.create(biobank, user.getId());
        Sample sample1 = createTestSample(1);
        sampleService.create(sample1, biobank.getId());

        /* ********* WHEN ********** */

        biobankService.remove(biobank.getId());

        /* ********* THEN ********** */

        assertEquals(null, biobankService.get(biobank.getId()));
        assertEquals(user, userService.get(user.getId()));
        user = userService.eagerGet(user.getId(), false, false, true);
        assertEquals(true, user.getBiobankAdministrators().isEmpty());
        assertEquals(null, sampleService.get(sample1.getId()));
        assertEquals(false, user.getSystemRoles().contains(SystemRole.BIOBANK_OPERATOR));
    }

    @Test
    public void removeBiobankTest2() {
         /* Test if remove involves second side of relationship */

        /* ********* GIVEN ********** */

        Biobank biobank = createTestBiobank(1);
        User user = createTestUser(1);
        userService.create(user);
        biobankService.create(biobank, user.getId());

        Sample sample1 = createTestSample(1);
        sampleService.create(sample1, biobank.getId());

        /* ********* WHEN ********** */

        userService.remove(user.getId());
        sampleService.remove(sample1.getId());

        /* ********* THEN ********** */

        assertEquals(biobank, biobankService.get(biobank.getId()));
        assertEquals(null, userService.get(user.getId()));
        assertEquals(null, sampleService.get(sample1.getId()));
        biobank = biobankService.eagerGet(biobank.getId(), true, false, false);
        assertEquals(true, biobank.getBiobankAdministrators().isEmpty());
        assertEquals(true, biobank.getSamples().isEmpty());
    }


    public void removeBiobankTest3() {
           /* Test if remove involves second side of relationship - RequestGroup */
        //TODO
          /* ********* GIVEN ********** */

          /* ********* WHEN ********** */


          /* ********* THEN ********** */

    }

    public void removeBiobankTest4() {
              /* Test if remove involves second side of relationship - SampleQuestion */
        // TODO
             /* ********* GIVEN ********** */

             /* ********* WHEN ********** */


             /* ********* THEN ********** */

    }

    @Test
    public void getEagerGetTest() {
         /* ********* GIVEN ********** */
        User user = createTestUser(1);
        userService.create(user);

        User user2 = createTestUser(2);
        userService.create(user2);

        Biobank biobank = createTestBiobank(1);
        biobankService.create(biobank, user.getId());

        Biobank biobank2 = createTestBiobank(2);
        biobankService.create(biobank2, user2.getId());

        Sample sample1 = createTestSample(1);
        sampleService.create(sample1, biobank.getId());
        Sample sample2 = createTestSample(2);
        sampleService.create(sample2, biobank.getId());

        Sample sample3 = createTestSample(3);
        sampleService.create(sample3, biobank2.getId());

        /* ********* WHEN ********** */

        /* ********* THEN ********** */

        biobank = biobankService.eagerGet(biobank.getId(), true, false, false);

        List<Sample> results = biobank.getSamples();
        assertEquals(true, results.contains(sample1));
        assertEquals(true, results.contains(sample2));
        assertEquals(2, results.size());

        biobank2 = biobankService.eagerGet(biobank2.getId(), true, false, false);

        assertEquals(true, biobank2.getSamples().contains(sample3));
        assertEquals(1, biobank2.getSamples().size());

    }


    public void getEagerGetTest2() {
             /* ********* GIVEN ********** */
        // TODO Test relationship to RequestGroup
            /* ********* WHEN ********** */

            /* ********* THEN ********** */

    }

    public void getEagerGetTest3() {
                /* ********* GIVEN ********** */
        // TODO Test relationship to SampleQuestion
               /* ********* WHEN ********** */

               /* ********* THEN ********** */

    }

    @Test
    public void removeAdministratorTest() {

         /* ********* GIVEN ********** */

        User user = createTestUser(1);
        userService.create(user);

        Biobank biobank = createTestBiobank(1);
        biobankService.create(biobank, user.getId());

        /* ********* WHEN ********** */

        biobankService.removeAdministratorFromBiobank(user.getId(), biobank.getId());

        /* ********* THEN ********** */
        // TODO  should throw exception. User is only administrator and he can't be removed

        BiobankAdministrator ba = biobankAdministratorService.get(biobank.getId(), user.getId());

        user = userService.get(user.getId());

        assertEquals(biobank, ba.getBiobank());
        assertEquals(false, biobank.getBiobankAdministrators().isEmpty());
        assertEquals(true, user.getSystemRoles().contains(SystemRole.BIOBANK_OPERATOR));
    }

    @Test
    public void assignAdministratorTest() {

            /* ********* GIVEN ********** */

        User user = createTestUser(1);
        userService.create(user);

        User user2 = createTestUser(2);
        userService.create(user2);

        Biobank biobank = createTestBiobank(1);
        biobankService.create(biobank, user.getId());

           /* ********* WHEN ********** */

        biobankService.assignAdministrator(user2.getId(), biobank.getId(), Permission.EDITOR);

           /* ********* THEN ********** */
        user2 = userService.get(user2.getId());
        biobank = biobankService.get(biobank.getId());

        BiobankAdministrator ba = biobankAdministratorService.get(biobank.getId(), user2.getId());

        assertEquals(2, biobank.getBiobankAdministrators().size());
        assertEquals(Permission.EDITOR, ba.getPermission());
        assertEquals(true, user2.getSystemRoles().contains(SystemRole.BIOBANK_OPERATOR));
    }

    @Test
    public void removeAdministratorTest2() {

             /* ********* GIVEN ********** */

        User user = createTestUser(1);
        userService.create(user);

        User user2 = createTestUser(2);
        userService.create(user2);

        Biobank biobank = createTestBiobank(1);
        biobankService.create(biobank, user.getId());

        biobankService.assignAdministrator(user2.getId(), biobank.getId(), Permission.EDITOR);

        /* ********* WHEN ********** */

        biobankService.removeAdministratorFromBiobank(biobank.getId(), user.getId());

        /* ********* THEN ********** */

        user = userService.eagerGet(user.getId(), false, false, true);
        user2 = userService.eagerGet(user2.getId(), false, false, true);
        biobank = biobankService.get(biobank.getId());

        BiobankAdministrator ba = biobankAdministratorService.get(biobank.getId(), user2.getId());

        assertEquals(true, ba != null);

        assertEquals(1, biobank.getBiobankAdministrators().size());
        assertEquals(true, user.getBiobankAdministrators().isEmpty());
        assertEquals(false, user.getSystemRoles().contains(SystemRole.BIOBANK_OPERATOR));
    }

}
