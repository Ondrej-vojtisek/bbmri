package bbmri.action;

import bbmri.entities.Biobank;
import bbmri.entities.Project;
import bbmri.entities.RequestGroup;
import bbmri.entities.User;
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

    public FileBean getFile() {
        return (FileBean) getRequest().getSession().getAttribute("file");
    }

    public void setFile(FileBean file) {
        getRequest().getSession().setAttribute("file", file);
    }

}
