package tests.daoAndServiceTest;

import bbmri.service.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tests.AbstractTest;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 3.9.13
 * Time: 13:48
 * To change this template use File | Settings | File Templates.
 */
public class SampleQuestionComplexTest extends AbstractTest {


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
    private SampleQuestionService sampleQuestionService;

    @Test
    public void createRequestGroupTest() {

        /* ********* GIVEN ********** */


        /* ********* WHEN ********** */



        /* ********* THEN ********** */


    }

}
