package tests.daoAndServiceTest;

import bbmri.entities.*;
import bbmri.service.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tests.AbstractTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 29.8.13
 * Time: 16:19
 * To change this template use File | Settings | File Templates.
 */
public class RequestGroupComplexTest extends AbstractTest {

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
    private RequestGroupService requestGroupService;

    /* ********* GIVEN ********** */
    /* ********* WHEN ********** */
    /* ********* THEN ********** */

    @Test
    public void createRequestGroupTest() {
    /* samples from one biobank*/
    /* ********* GIVEN ********** */
        User user = createTestUser(1);
        userService.create(user);
        Project project = createTestProject(1);
        projectService.create(project, user.getId());
        Biobank biobank = createTestBiobank(1);
        biobankService.create(biobank, user.getId());
        Sample sample = createTestSample(1);
        sampleService.create(sample, biobank.getId());
        Sample sample2 = createTestSample(2);
        sampleService.create(sample2, biobank.getId());
        Sample sample3 = createTestSample(3);
        sampleService.create(sample3, biobank.getId());

        Request request = requestService.create(sample.getId(), new Integer(1));
        Request request2 = requestService.create(sample2.getId(), new Integer(2));
        Request request3 = requestService.create(sample3.getId(), new Integer(3));

        List<Request> requestList = new ArrayList<Request>();
        requestList.add(request);
        requestList.add(request2);
        requestList.add(request3);

        /* ********* WHEN ********** */

        requestGroupService.create(requestList, project.getId());

        /* ********* THEN ********** */

        List<RequestGroup> rqgList = requestGroupService.all();
        assertEquals(1, rqgList.size());
        RequestGroup rqg = rqgList.get(0);
        assertEquals(true, rqg.getProject().equals(project));
        assertEquals(true, rqg.getBiobank().equals(biobank));
        assertEquals(true, rqg.getRequestState().equals(RequestState.NEW));
        assertEquals(true, rqg.getRequests().contains(request));
        assertEquals(true, rqg.getRequests().contains(request2));
        assertEquals(true, rqg.getRequests().contains(request3));
        assertEquals(3, rqg.getRequests().size());

    }

    @Test
    public void createRequestGroupTest2() {
       /* samples from two biobanks*/
       /* ********* GIVEN ********** */
        User user = createTestUser(1);
        userService.create(user);

        User user2 = createTestUser(2);
        userService.create(user2);

        Project project = createTestProject(1);
        projectService.create(project, user.getId());
        Biobank biobank = createTestBiobank(1);
        biobankService.create(biobank, user.getId());

        Biobank biobank2 = createTestBiobank(2);
        biobankService.create(biobank2, user2.getId());

        Sample sample = createTestSample(1);
        sampleService.create(sample, biobank.getId());
        Sample sample2 = createTestSample(2);
        sampleService.create(sample2, biobank.getId());

        Sample sample3 = createTestSample(3);
        sampleService.create(sample3, biobank2.getId());

        Request request = requestService.create(sample.getId(), new Integer(1));
        Request request2 = requestService.create(sample2.getId(), new Integer(2));
        Request request3 = requestService.create(sample3.getId(), new Integer(3));

        List<Request> requestList = new ArrayList<Request>();
        requestList.add(request);
        requestList.add(request2);
        requestList.add(request3);

           /* ********* WHEN ********** */

        requestGroupService.create(requestList, project.getId());

           /* ********* THEN ********** */

        List<RequestGroup> rqgList = requestGroupService.all();
        assertEquals(2, rqgList.size());
        RequestGroup rqg = rqgList.get(0);
        assertEquals(true, rqg.getProject().equals(project));
        assertEquals(true, rqg.getBiobank().equals(biobank));
        assertEquals(true, rqg.getRequestState().equals(RequestState.NEW));
        assertEquals(true, rqg.getRequests().contains(request));
        assertEquals(true, rqg.getRequests().contains(request2));
        assertEquals(false, rqg.getRequests().contains(request3));
        assertEquals(2, rqg.getRequests().size());

        RequestGroup rqg2 = rqgList.get(1);
        assertEquals(true, rqg2.getProject().equals(project));
        assertEquals(true, rqg2.getBiobank().equals(biobank2));
        assertEquals(true, rqg2.getRequestState().equals(RequestState.NEW));
        assertEquals(false, rqg2.getRequests().contains(request));
        assertEquals(false, rqg2.getRequests().contains(request2));
        assertEquals(true, rqg2.getRequests().contains(request3));
        assertEquals(1, rqg2.getRequests().size());


    }

}
