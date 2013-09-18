package tests.daoAndServiceTest;

import bbmri.entities.Biobank;
import bbmri.entities.Sample;
import bbmri.entities.User;
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

        assertEquals(biobank, user.getBiobank());
        assertEquals(user, biobank.getAdministrators().get(0));
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

        assertEquals(null, user.getBiobank());
        assertEquals(true, biobank.getAdministrators().isEmpty());
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
        user = userService.get(user.getId());
        assertEquals(null, user.getBiobank());
        assertEquals(null, sampleService.get(sample1.getId()));
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
        assertEquals(true, biobank.getAdministrators().isEmpty());
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
        // TODO it should throw exception
        assertEquals(user, biobank.getOwner());
        assertEquals(false, biobank.getAdministrators().isEmpty());
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

        biobankService.assignAdministrator(user2.getId(), biobank.getId());

           /* ********* THEN ********** */
        user2 = userService.get(user2.getId());
        biobank = biobankService.get(biobank.getId());

        assertEquals(user, biobank.getOwner());
        assertEquals(biobank, user2.getBiobank());
        assertEquals(2, biobank.getAdministrators().size());
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

        biobankService.assignAdministrator(user2.getId(), biobank.getId());

        /* ********* WHEN ********** */

        biobankService.removeAdministratorFromBiobank(user.getId(), biobank.getId());

        /* ********* THEN ********** */

        user = userService.get(user.getId());
        user2 = userService.get(user2.getId());
        biobank = biobankService.get(biobank.getId());

        assertEquals(user2, biobank.getOwner());
        assertEquals(1, biobank.getAdministrators().size());
        assertEquals(null, user.getBiobank());
    }


    // TODO
    public void changeOwnershipTest() {
        /* ********* GIVEN ********** */

        User user = createTestUser(1);
        userService.create(user);

        User user2 = createTestUser(2);
        userService.create(user2);


        Biobank biobank = createTestBiobank(1);
        biobankService.create(biobank, user2.getId());

        biobankService.assignAdministrator(user.getId(), biobank.getId());

        /* ********* WHEN ********** */

        biobankService.changeOwnership(biobank.getId(), user.getId());

        /* ********* THEN ********** */

        user = userService.get(user.getId());
        user2 = userService.get(user2.getId());
        biobank = biobankService.get(biobank.getId());

//        assertEquals(biobank, user2.getBiobank());
//        assertEquals(biobank, user.getBiobank());
        log("Administrators: " + biobank.getAdministrators());
        log("Administrators[0]: " + biobank.getAdministrators().get(0));
//        assertEquals(2, biobank.getAdministrators().size());
//        assertEquals(user2, biobank.getAdministrators().get(0));

    }

}
