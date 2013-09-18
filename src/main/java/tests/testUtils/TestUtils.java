package tests.testUtils;

import bbmri.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 29.8.13
 * Time: 10:38
 * To change this template use File | Settings | File Templates.
 */
public class TestUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public void log(String msg){
           logger.debug(msg);

    }

    protected User createTestUser(int i) {
        User user = new User();
        user.setName("Pokusny" + i);
        user.setSurname("Uzivatel" + i);
        return user;
    }

    protected Biobank createTestBiobank(int i) {
        Biobank biobank = new Biobank();
        biobank.setName("Biobank" + i);
        biobank.setAddress("Address" + i);
        return biobank;
    }

    protected Project createTestProject(int i) {
        Project project = new Project();
        project.setName("TestProject" + i);
        project.setProjectState(ProjectState.NEW);
        return project;
    }

    protected RequestGroup createRequestGroup(int i) {
        RequestGroup requestGroup = new RequestGroup();
        return requestGroup;
    }

    protected Sample createTestSample(int i){
        Sample sample = new Sample();
        sample.setDiagnosis("a" + i);
        sample.setGrading(new Integer(i));
        sample.setMorphology("a" + i);
        sample.setpTNM("a" + i);
        sample.setTissueType("a" + i);
        sample.setTNM("a" + i);
        return sample;
    }

    protected SampleQuestion createTestSampleQuestion(int i){
        SampleQuestion sampleQuestion = new SampleQuestion();
        sampleQuestion.setSpecification("Specification" + i);
        return sampleQuestion;
    }

    protected Request createTestRequest(int i){
           Request request = new Request();
           request.setNumOfRequested(new Integer(4));
           return request;
       }
}
