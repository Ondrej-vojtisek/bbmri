package tests.testUtils;

import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.ProjectState;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.entities.sample.Sample;
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
      //  biobank.setAddress("Address" + i);
        return biobank;
    }

    protected Project createTestProject(int i) {
        Project project = new Project();
        project.setName("TestProject" + i);
        project.setProjectState(ProjectState.NEW);
        return project;
    }


    protected Sample createTestSample(int i){
        Sample sample = new Sample();
//        sample.setDiagnosis("a" + i);
//        sample.setMorphology("a" + i);
//        sample.setpTNM("a" + i);
//        sample.setTissueType("a" + i);
//        sample.setTNM("a" + i);
        return sample;
    }

    protected SampleRequest createTestSampleQuestion(int i){
        SampleRequest sampleRequest = new SampleRequest();
        sampleRequest.setSpecification("Specification" + i);
        sampleRequest.setRequestState(RequestState.NEW);
        return sampleRequest;
    }

    protected Request createTestRequest(int i){
           Request request = new Request();
           request.setNumOfRequested(4);
           return request;
       }

    protected ProjectAdministrator createTestProjectAdministrator(){
            return new ProjectAdministrator();
        }

    protected BiobankAdministrator createTestBiobankAdministrator(){
                return new BiobankAdministrator();
            }
}
