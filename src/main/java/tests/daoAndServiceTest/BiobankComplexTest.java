package tests.daoAndServiceTest;

import bbmri.entities.Biobank;
import bbmri.entities.Sample;
import bbmri.entities.User;
import bbmri.service.BiobankService;
import bbmri.service.SampleService;
import bbmri.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tests.AbstractTest;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 28.8.13
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */
public class BiobankComplexTest extends AbstractTest {

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


        assertEquals(user.getBiobank(), null);
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

        assertEquals(biobankService.get(biobank.getId()), null);
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
        userService.remove(user.getId());

        Sample sample1 = createTestSample(1);
        sampleService.create(sample1, biobank.getId());

        /* ********* WHEN ********** */

        sampleService.remove(sample1.getId());

        /* ********* THEN ********** */

        assertEquals(biobankService.get(biobank.getId()), biobank);
        assertEquals(userService.get(user.getId()), null);
        assertEquals(sampleService.get(sample1.getId()), null);
        biobank = biobankService.get(biobank.getId());
        assertEquals(true, biobank.getAdministrators().isEmpty());
        assertEquals(true, biobank.getSamples().isEmpty());
    }

    @Test
    public void getAllSamplesTest() {
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

        List<Sample> results = biobank.getSamples();
        assertEquals(true, results.contains(sample1));
        assertEquals(true, results.contains(sample2));
        assertEquals(2, results.size());

        assertEquals(true, biobank2.getSamples().contains(sample3));
        assertEquals(1, biobank2.getSamples().size());

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

        assertEquals(null, user.getBiobank());
        assertEquals(true, biobank.getAdministrators().isEmpty());
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

        assertEquals(user, biobank.getOwner());
        assertEquals(biobank, user2.getBiobank());
        assertEquals(2, biobank.getAdministrators().size());
    }

    @Test
    public void changeOwnershipTest() {
        /* ********* GIVEN ********** */

        User user = createTestUser(1);
        userService.create(user);

        User user2 = createTestUser(2);
        userService.create(user2);

        Biobank biobank = createTestBiobank(1);
        biobankService.create(biobank, user.getId());

        biobankService.assignAdministrator(user2.getId(), biobank.getId());

        /* ********* WHEN ********** */

         biobankService.changeOwnership(biobank.getId(), user2.getId());

        /* ********* THEN ********** */

        assertEquals(biobank, user2.getBiobank());
        assertEquals(biobank, user.getBiobank());
        assertEquals(2, biobank.getAdministrators().size());
        assertEquals(user2, biobank.getOwner());

    }

}