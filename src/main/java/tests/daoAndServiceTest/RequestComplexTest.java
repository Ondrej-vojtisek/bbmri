package tests.daoAndServiceTest;

import cz.bbmri.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 3.9.13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class RequestComplexTest extends AbstractDaoAndServiceTest {

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

    /* ********* GIVEN ********** */
    /* ********* WHEN ********** */
    /* ********* THEN ********** */

//    @Test
//    public void createRequestTest() {
//    /* ********* GIVEN ********** */
//        User user = createTestUser(1);
//        userService.create(user);
//
//        Project project = createTestProject(1);
//        projectService.create(project, user.getId());
//
//        Biobank biobank = createTestBiobank(1);
//        biobankService.create(biobank, user.getId());
//
//        Sample sample = createTestSample(1);
//        sampleService.create(sample, biobank.getId());
//
//        /* ********* WHEN ********** */
//
//        Request request = requestService.create(sample.getId(), 1);
//
//        /* ********* THEN ********** */
//
//        assertEquals(true, requestService.get(request.getId()) != null);
//
//    }

//    @Test
//    public void removeRequestTest() {
//        /* Test of cascade - in case request is removed, sample is not involved*/
//
//        /* ********* GIVEN ********** */
//
//        User user = createTestUser(1);
//        userService.create(user);
//
//        Project project = createTestProject(1);
//        projectService.create(project, user.getId());
//
//        Biobank biobank = createTestBiobank(1);
//        biobankService.create(biobank, user.getId());
//
//        Sample sample = createTestSample(1);
//        sampleService.create(sample, biobank.getId());
//
//        Request request = requestService.create(sample.getId(), 1);
//
//        /* ********* WHEN ********** */
//
//        requestService.remove(request.getId());
//
//        /* ********* THEN ********** */
//
//        assertEquals(true, requestService.get(request.getId()) == null);
//    }

//    @Test
//    public void updateRequestTest() {
//
//        /* ********* GIVEN ********** */
//
//        User user = createTestUser(1);
//        userService.create(user);
//
//        Project project = createTestProject(1);
//        projectService.create(project, user.getId());
//
//        Biobank biobank = createTestBiobank(1);
//        biobankService.create(biobank, user.getId());
//
//        Sample sample = createTestSample(1);
//        sampleService.create(sample, biobank.getId());
//
//        Request request = requestService.create(sample.getId(), 1);
//
//        /* ********* WHEN ********** */
//
//        request.setNumOfRequested(2);
//        requestService.update(request);
//
//        /* ********* THEN ********** */
//
//        request = requestService.get(request.getId());
//        assertEquals(true, request.getNumOfRequested().equals(2));
//
//    }
}
