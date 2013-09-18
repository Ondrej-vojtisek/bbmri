package tests.daoAndServiceTest;

import bbmri.entities.Biobank;
import bbmri.entities.Project;
import bbmri.entities.SampleQuestion;
import bbmri.entities.User;
import bbmri.service.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 3.9.13
 * Time: 13:48
 * To change this template use File | Settings | File Templates.
 */
public class SampleQuestionComplexTest extends AbstractDaoAndServiceTest {


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
    public void createSampleQuestionTest() {

        /* ********* GIVEN ********** */

        Biobank biobank = createTestBiobank(1);
        User user = createTestUser(1);
        userService.create(user);
        biobankService.create(biobank, user.getId());
        Project project = createTestProject(1);
        projectService.create(project, user.getId());
        SampleQuestion sampleQuestion = createTestSampleQuestion(1);

        /* ********* WHEN ********** */

        sampleQuestionService.create(sampleQuestion, biobank.getId(), project.getId());

        /* ********* THEN ********** */

        biobank = biobankService.eagerGet(biobank.getId(), false, false, true);
        project = projectService.eagerGet(project.getId(), false, false, false, true);

        assertEquals(project, sampleQuestion.getProject());
        assertEquals(biobank, sampleQuestion.getBiobank());
        assertEquals(true, project.getSampleQuestions().contains(sampleQuestion));
        assertEquals(true, biobank.getSampleQuestions().contains(sampleQuestion));
        assertEquals(false, sampleQuestion.isProcessed());
    }

    @Test
    public void withdrawSampleQuestionTest() {

        /* ********* GIVEN ********** */

        Biobank biobank = createTestBiobank(1);
        User user = createTestUser(1);
        userService.create(user);
        biobankService.create(biobank, user.getId());
        SampleQuestion sampleQuestion = createTestSampleQuestion(1);

        /* ********* WHEN ********** */

        sampleQuestionService.withdraw(sampleQuestion, biobank.getId());

        /* ********* THEN ********** */

        biobank = biobankService.get(biobank.getId());
        assertEquals(null, sampleQuestion.getProject());
        assertEquals(biobank, sampleQuestion.getBiobank());
        assertEquals(false, sampleQuestion.isProcessed());

        biobank = biobankService.eagerGet(biobank.getId(), false, false, true);

        assertEquals(true, biobank.getSampleQuestions().contains(sampleQuestion));

    }

    @Test
    public void removeSampleQuestionTest() {

        /* ********* GIVEN ********** */

        Biobank biobank = createTestBiobank(1);
        User user = createTestUser(1);
        userService.create(user);
        biobankService.create(biobank, user.getId());
        Project project = createTestProject(1);
        projectService.create(project, user.getId());
        SampleQuestion sampleQuestion = createTestSampleQuestion(1);
        sampleQuestionService.create(sampleQuestion, biobank.getId(), project.getId());

        /* ********* WHEN ********** */

        sampleQuestionService.remove(sampleQuestion.getId());

        /* ********* THEN ********** */

        assertEquals(true, project.getSampleQuestions().isEmpty());
        assertEquals(true, biobank.getSampleQuestions().isEmpty());
        assertEquals(null, sampleQuestionService.get(sampleQuestion.getId()));
    }

    @Test
    public void getAllByProjectTest() {

        /* ********* GIVEN ********** */

        Biobank biobank = createTestBiobank(1);
        User user = createTestUser(1);
        userService.create(user);
        biobankService.create(biobank, user.getId());
        Project project = createTestProject(1);
        projectService.create(project, user.getId());
        SampleQuestion sampleQuestion = createTestSampleQuestion(1);
        sampleQuestionService.create(sampleQuestion, biobank.getId(), project.getId());

        SampleQuestion sampleQuestion2 = createTestSampleQuestion(2);
        sampleQuestionService.create(sampleQuestion2, biobank.getId(), project.getId());

        SampleQuestion sampleQuestion3 = createTestSampleQuestion(3);
        sampleQuestionService.create(sampleQuestion3, biobank.getId(), project.getId());

        /* ********* WHEN ********** */

        List<SampleQuestion> sqs =  sampleQuestionService.getAllByProject(project);

        /* ********* THEN ********** */

        assertEquals(true, sqs.contains(sampleQuestion));
        assertEquals(true, sqs.contains(sampleQuestion2));
        assertEquals(true, sqs.contains(sampleQuestion3));
        assertEquals(3, sqs.size());

    }


    @Test
    public void getAllByBiobankTest() {

        /* ********* GIVEN ********** */

        Biobank biobank = createTestBiobank(1);
        User user = createTestUser(1);
        userService.create(user);
        biobankService.create(biobank, user.getId());

        Biobank biobank2 = createTestBiobank(2);
        User user2 = createTestUser(2);
        userService.create(user2);
        biobankService.create(biobank2, user.getId());


        Project project = createTestProject(1);
        projectService.create(project, user.getId());
        SampleQuestion sampleQuestion = createTestSampleQuestion(1);
        sampleQuestionService.create(sampleQuestion, biobank.getId(), project.getId());

        SampleQuestion sampleQuestion2 = createTestSampleQuestion(2);
        sampleQuestionService.create(sampleQuestion2, biobank.getId(), project.getId());

        SampleQuestion sampleQuestion3 = createTestSampleQuestion(3);
        sampleQuestionService.create(sampleQuestion3, biobank2.getId(), project.getId());

        /* ********* WHEN ********** */

        List<SampleQuestion> sqs = sampleQuestionService.getAllByBiobank(biobank);

        /* ********* THEN ********** */

        assertEquals(true, sqs.contains(sampleQuestion));
        assertEquals(true, sqs.contains(sampleQuestion2));
        assertEquals(false, sqs.contains(sampleQuestion3));
        assertEquals(2, sqs.size());

    }


}
