package tests.daoAndServiceTest;

import cz.bbmri.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.9.13
 * Time: 12:57
 * To change this template use File | Settings | File Templates.
 */
public class SampleComplexTest extends AbstractDaoAndServiceTest {


    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BiobankService biobankService;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private SampleRequestService sampleRequestService;

//    @Test
//    public void createSampleTest() {
//
//        /* ********* GIVEN ********** */
//
//        Biobank biobank = createTestBiobank(1);
//        User user = createTestUser(1);
//        userService.create(user);
//        biobankService.create(biobank, user.getId());
//        Sample sample = createTestSample(1);
//
//
//        /* ********* WHEN ********** */
//
//        sampleService.create(sample, biobank.getId());
//
//        /* ********* THEN ********** */
//
//        biobank = biobankService.eagerGet(biobank.getId(), true, false, false);
//
//        assertEquals(biobank, sample.getBiobank());
//        assertEquals(sample, biobank.getSamples().get(0));
//    }

//    @Test
//    public void removeSampleTest() {
//
//        /* ********* GIVEN ********** */
//
//        Biobank biobank = createTestBiobank(1);
//        User user = createTestUser(1);
//        userService.create(user);
//        biobankService.create(biobank, user.getId());
//        Sample sample = createTestSample(1);
//        sampleService.create(sample, biobank.getId());
//
//        /* ********* WHEN ********** */
//
//        sampleService.remove(sample.getId());
//
//        /* ********* THEN ********** */
//
//        assertEquals(true, biobank.getSamples().isEmpty());
//        assertEquals(null, sampleService.get(sample.getId()));
//    }

//    @Test
//    public void decreaseCountTest() {
//
//                  /* ********* GIVEN ********** */
//        Biobank biobank = createTestBiobank(1);
//        User user = createTestUser(1);
//        userService.create(user);
//        biobankService.create(biobank, user.getId());
//        Sample sample = createTestSample(1);
//        sample.setNumOfSamples(5);
//        sample.setNumOfAvailable(2);
//        sampleService.create(sample, biobank.getId());
//
//        /* ********* WHEN ********** */
//
//        sampleService.decreaseCount(sample.getId(), 2);
//
//        /* ********* THEN ********** */
//
//        sample = sampleService.get(sample.getId());
//        assertEquals(true, sample.getNumOfAvailable().equals(0));
//        assertEquals(true, sample.getNumOfSamples().equals(3));
//    }

//    @Test
//    public void decreaseCountTest2() {
//
//            /* ********* GIVEN ********** */
//        Biobank biobank = createTestBiobank(1);
//        User user = createTestUser(1);
//        userService.create(user);
//        biobankService.create(biobank, user.getId());
//        Sample sample = createTestSample(1);
//        sample.setNumOfSamples(5);
//        sample.setNumOfAvailable(2);
//        sampleService.create(sample, biobank.getId());
//
//            /* ********* WHEN ********** */
//
//        sampleService.decreaseCount(sample.getId(), 3);
//
//            /* ********* THEN ********** */
//
//        sample = sampleService.get(sample.getId());
//        assertEquals(true, sample.getNumOfAvailable().equals(2));
//        assertEquals(true, sample.getNumOfSamples().equals(5));
//    }

//   @Test
//    public void withdrawSampleTest() {
//
//               /* ********* GIVEN ********** */
//        Biobank biobank = createTestBiobank(1);
//        User user = createTestUser(1);
//        userService.create(user);
//        biobankService.create(biobank, user.getId());
//        Sample sample = createTestSample(1);
//        sample.setNumOfSamples(5);
//        sample.setNumOfAvailable(2);
//        sampleService.create(sample, biobank.getId());
//
//               /* ********* WHEN ********** */
//
//        sampleService.withdrawSample(sample.getId(), 2);
//
//               /* ********* THEN ********** */
//
//        sample = sampleService.get(sample.getId());
//
//        assertEquals(true, sample.getNumOfAvailable().equals(0));
//        assertEquals(true, sample.getNumOfSamples().equals(3));
//    }

//   @Test
//    public void withdrawSampleTest2() {
//
//        /* ********* GIVEN ********** */
//
//        Biobank biobank = createTestBiobank(1);
//        User user = createTestUser(1);
//        userService.create(user);
//        biobankService.create(biobank, user.getId());
//        Sample sample = createTestSample(1);
//        sample.setNumOfSamples(5);
//        sample.setNumOfAvailable(2);
//        sampleService.create(sample, biobank.getId());
//
//        /* ********* WHEN ********** */
//
//        sampleService.withdrawSample(sample.getId(), 3);
//
//        /* ********* THEN ********** */
//
//        sample = sampleService.get(sample.getId());
//
//        assertEquals(true, sample.getNumOfAvailable().equals(0));
//        assertEquals(true, sample.getNumOfSamples().equals(2));
//    }


//    @Test
//    public void samplesByQuery() {
//
//        /* ********* GIVEN ********** */
//
//        Biobank biobank = createTestBiobank(1);
//        User user = createTestUser(1);
//        userService.create(user);
//        biobankService.create(biobank, user.getId());
//        Sample sample = createTestSample(1);
//        sampleService.create(sample, biobank.getId());
//
//        Sample sample2 = createTestSample(2);
//        sampleService.create(sample2, biobank.getId());
//
//        /* ******************************************************************************* */
//
//        Sample question = new Sample();
//        question.setGrading(1);
//
//        /* ********* WHEN ********** */
//
//        List<Sample> samples = sampleService.getSamplesByQuery(question);
//
//        /* ********* THEN ********** */
//
//        assertEquals(true, samples.contains(sample));
//        assertEquals(false, samples.contains(sample2));
//
//        /* ******************************************************************************* */
//
//        question = new Sample();
//        question.setMorphology("a");
//        sample.setMorphology("a");
//        sampleService.update(sample);
//
//        /* ********* WHEN ********** */
//
//        samples = sampleService.getSamplesByQuery(question);
//
//        /* ********* THEN ********** */
//
//        assertEquals(true, samples.contains(sample));
//        assertEquals(false, samples.contains(sample2));
//
//        /* ******************************************************************************* */
//
//        question = new Sample();
//        question.setDiagnosis("a");
//        sample.setDiagnosis("a");
//        sampleService.update(sample);
//
//        /* ********* WHEN ********** */
//
//        samples = sampleService.getSamplesByQuery(question);
//
//        /* ********* THEN ********** */
//
//        assertEquals(true, samples.contains(sample));
//        assertEquals(false, samples.contains(sample2));
//
//        /* ******************************************************************************* */
//
//        question = new Sample();
//        question.setpTNM("a");
//        sample.setpTNM("a");
//        sampleService.update(sample);
//
//        /* ********* WHEN ********** */
//
//        samples = sampleService.getSamplesByQuery(question);
//
//        /* ********* THEN ********** */
//
//        assertEquals(true, samples.contains(sample));
//        assertEquals(false, samples.contains(sample2));
//
//        /* ******************************************************************************* */
//
//        question = new Sample();
//        question.setTissueType("a");
//        sample.setTissueType("a");
//        sampleService.update(sample);
//
//        /* ********* WHEN ********** */
//
//        samples = sampleService.getSamplesByQuery(question);
//
//        /* ********* THEN ********** */
//
//        assertEquals(true, samples.contains(sample));
//        assertEquals(false, samples.contains(sample2));
//
//        /* ******************************************************************************* */
//
//        question = new Sample();
//        question.setTNM("a");
//        sample.setTNM("a");
//        sampleService.update(sample);
//
//        /* ********* WHEN ********** */
//
//        samples = sampleService.getSamplesByQuery(question);
//
//        /* ********* THEN ********** */
//
//        assertEquals(true, samples.contains(sample));
//        assertEquals(false, samples.contains(sample2));
//
//        /* ******************************************************************************* */
//
//    }

//    @Test
//    public void samplesByQuery2() {
//
//        /* ********* GIVEN ********** */
//
//        Biobank biobank = createTestBiobank(1);
//        User user = createTestUser(1);
//        userService.create(user);
//        biobankService.create(biobank, user.getId());
//        Sample sample = createTestSample(1);
//        sampleService.create(sample, biobank.getId());
//
//        Sample sample2 = createTestSample(2);
//        sampleService.create(sample2, biobank.getId());
//
//        Sample sample3 = createTestSample(3);
//        sampleService.create(sample3, biobank.getId());
//
//        /* ******************************************************************************* */
//
//        sample.setDiagnosis("a");
//        sampleService.update(sample);
//
//        sample2.setDiagnosis("a");
//        sampleService.update(sample2);
//
//        sample3.setDiagnosis("a");
//        sampleService.update(sample3);
//
//
//        Sample question = new Sample();
//        question.setGrading(1);
//        question.setDiagnosis("a");
//
//        /* ********* WHEN ********** */
//
//        List<Sample> samples = sampleService.getSamplesByQuery(question);
//
//        /* ********* THEN ********** */
//
//        assertEquals(true, samples.contains(sample));
//        assertEquals(false, samples.contains(sample2));
//        assertEquals(false, samples.contains(sample3));
//
//
//    }

//    @Test
//    public void samplesByQuery3() {
//
//        /* ********* GIVEN ********** */
//
//        Biobank biobank = createTestBiobank(1);
//        User user = createTestUser(1);
//        userService.create(user);
//        biobankService.create(biobank, user.getId());
//        Sample sample = createTestSample(1);
//        sampleService.create(sample, biobank.getId());
//
//        Sample sample2 = createTestSample(2);
//        sampleService.create(sample2, biobank.getId());
//
//        Sample question = new Sample();
//        question.setGrading(1);
//
//        /* ********* WHEN ********** */
//
//        List<Sample> samples = sampleService.getSamplesByQuery(question);
//
//        /* ********* THEN ********** */
//
//        assertEquals(true, samples.contains(sample));
//        assertEquals(false, samples.contains(sample2));
//    }

//    @Test
//    public void getAllByBiobankTest() {
//
//        /* ********* GIVEN ********** */
//
//        Biobank biobank = createTestBiobank(1);
//        User user = createTestUser(1);
//        userService.create(user);
//        biobankService.create(biobank, user.getId());
//        Sample sample = createTestSample(1);
//        sampleService.create(sample, biobank.getId());
//
//        Sample sample2 = createTestSample(2);
//        sampleService.create(sample2, biobank.getId());
//
//        Biobank biobank2 = createTestBiobank(2);
//        User user2 = createTestUser(2);
//        userService.create(user2);
//        biobankService.create(biobank2, user2.getId());
//
//        Sample sample3 = createTestSample(3);
//        sampleService.create(sample3, biobank2.getId());
//
//
//           /* ********* WHEN ********** */
//
//        List<Sample> samples = sampleService.getAllByBiobank(biobank.getId());
//        List<Sample> samplesBiobank2 = sampleService.getAllByBiobank(biobank2.getId());
//
//           /* ********* THEN ********** */
//
//        assertEquals(true, samples.contains(sample));
//        assertEquals(true, samples.contains(sample2));
//        assertEquals(false, samples.contains(sample3));
//        assertEquals(false, samplesBiobank2.contains(sample));
//        assertEquals(false, samplesBiobank2.contains(sample2));
//        assertEquals(true, samplesBiobank2.contains(sample3));
//    }

}
