package tests.daoAndServiceTest;

import bbmri.entities.*;
import bbmri.service.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tests.AbstractTest;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 3.9.13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class RequestComplexTest extends AbstractTest {

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

    @Test
    public void createRequestTest() {
    /* ********* GIVEN ********** */
        User user = createTestUser(1);
        userService.create(user);

        Project project = createTestProject(1);
        projectService.create(project, user.getId());

        Biobank biobank = createTestBiobank(1);
        biobankService.create(biobank, user.getId());

        Sample sample = createTestSample(1);
        sampleService.create(sample, biobank.getId());

        /* ********* WHEN ********** */

        Request request = requestService.create(sample.getId(), new Integer(1));

        /* ********* THEN ********** */

        List<Request> all = requestService.all();
        assertEquals(1, all.size());
        assertEquals(request, all.get(0));

    }

    @Test
    public void removeRequestTest() {
        /* Test of cascade - in case request is removed, sample is not involved*/

        /* ********* GIVEN ********** */

        User user = createTestUser(1);
        userService.create(user);

        Project project = createTestProject(1);
        projectService.create(project, user.getId());

        Biobank biobank = createTestBiobank(1);
        biobankService.create(biobank, user.getId());

        Sample sample = createTestSample(1);
        sampleService.create(sample, biobank.getId());

        Request request = requestService.create(sample.getId(), new Integer(1));

            /* ********* WHEN ********** */

        requestService.remove(request.getId());

            /* ********* THEN ********** */

        List<Sample> all = sampleService.all();
        assertEquals(1, all.size());
        assertEquals(sample, all.get(0));

    }

    @Test
    public void updateRequestTest() {

            /* ********* GIVEN ********** */

        User user = createTestUser(1);
        userService.create(user);

        Project project = createTestProject(1);
        projectService.create(project, user.getId());

        Biobank biobank = createTestBiobank(1);
        biobankService.create(biobank, user.getId());

        Sample sample = createTestSample(1);
        sampleService.create(sample, biobank.getId());

        Request request = requestService.create(sample.getId(), new Integer(1));

                /* ********* WHEN ********** */

        request.setNumOfRequested(new Integer(2));
        requestService.update(request);

                /* ********* THEN ********** */

        List<Request> all = requestService.all();
        assertEquals(1, all.size());
        assertEquals(new Integer(2), all.get(0).getNumOfRequested());

    }
}
