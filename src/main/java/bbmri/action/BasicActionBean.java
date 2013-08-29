package bbmri.action;

import bbmri.entities.*;
import bbmri.service.*;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
public class BasicActionBean implements ActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    protected UserService userService;

    @SpringBean
    protected BiobankService biobankService;

    @SpringBean
    protected ProjectService projectService;

    @SpringBean
    protected RequestService requestService;

    @SpringBean
    protected SampleService sampleService;

    @SpringBean
    protected RequestGroupService requestGroupService;

    @SpringBean
    protected SampleQuestionService sampleQuestionService;

    @SpringBean
    protected AttachmentService attachmentService;


    private static final String MY_PROJECTS = "/project_my_projects.jsp";

    private TheActionBeanContext ctx;

    @Override
    public void setContext(ActionBeanContext ctx) {
        this.ctx = (TheActionBeanContext) ctx;
    }

    @Override
    public TheActionBeanContext getContext() {
        return ctx;
    }

    public Resolution primary_menu_project(){
        return new ForwardResolution(MY_PROJECTS);
    }

    public User getLoggedUser(){

        Long id = ctx.getIdentifier();
        return userService.get(id);
    }

    public Set<Role> getRoles(){
        Long id = ctx.getIdentifier();
        return userService.get(id).getRoles();

    }

    public RequestGroup getRequestGroupBSC(){

            Long id = ctx.getRequestGroupId();
            return requestGroupService.get(id);
        }

    public Request getRequestBSC(){

               Long id = ctx.getRequestId();
               return requestService.get(id);
           }



}
