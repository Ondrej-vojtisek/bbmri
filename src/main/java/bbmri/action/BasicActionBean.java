package bbmri.action;

import bbmri.entities.User;
import net.sourceforge.stripes.action.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
public class BasicActionBean implements ActionBean {

    private static final String MY_PROJECTS = "/project_my_projects.jsp";

    private MyActionBeanContext ctx;

    public void setContext(ActionBeanContext ctx) {
        this.ctx = (MyActionBeanContext) ctx;
    }

    public MyActionBeanContext getContext() {
        return ctx;
    }

    public User getLoggedUser() {
        return ctx.getLoggedUser();
    }

    @HandlesEvent("releaseContext")
    public void releaseContext(){
        System.err.println("ReleaseContext");

        getContext().setProject(null);
        getContext().setBiobank(null);
        getContext().setRequest(null);
        getContext().setRequestGroup(null);
        getContext().setSampleQuestion(null);
        getContext().setSample(null);
    }

    public Resolution primary_menu_project(){
        return new ForwardResolution(MY_PROJECTS);
    }


}
