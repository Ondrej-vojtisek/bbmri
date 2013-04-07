package bbmri.action;

import bbmri.entities.*;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.FileBean;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 24.10.12
 * Time: 0:45
 * To change this template use File | Settings | File Templates.
 */
public class MyActionBeanContext extends ActionBeanContext {
    public void setLoggedUser(User loggedUser) {
        getRequest().getSession().setAttribute("loggedUser", loggedUser);
    }

    public User getLoggedUser() {
        return (User) getRequest().getSession().getAttribute("loggedUser");
    }

    public Project getProject() {
        return (Project) getRequest().getSession().getAttribute("project");
    }

    public void setProject(Project project) {
        getRequest().getSession().setAttribute("project", project);
    }

    public Biobank getBiobank() {
        return (Biobank) getRequest().getSession().getAttribute("biobank");
    }

    public void setBiobank(Biobank biobank) {
        getRequest().getSession().setAttribute("biobank", biobank);
    }

    public RequestGroup getRequestGroup() {
        return (RequestGroup) getRequest().getSession().getAttribute("requestGroup");
    }

    public void setRequestGroup(RequestGroup requestGroup) {
        getRequest().getSession().setAttribute("requestGroup", requestGroup);
    }

    public Sample getSample() {
        return (Sample) getRequest().getSession().getAttribute("sample");
    }

    public void setSample(Sample sample) {
        getRequest().getSession().setAttribute("sample", sample);
    }

    public SampleQuestion getSampleQuestion() {
           return (SampleQuestion) getRequest().getSession().getAttribute("sampleQuestion");
       }

       public void setSampleQuestion(SampleQuestion sampleQuestion) {
           getRequest().getSession().setAttribute("sampleQuestion", sampleQuestion);
       }

}
