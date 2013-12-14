package bbmri.action.base;

import bbmri.extension.context.TheActionBeanContext;
import bbmri.entities.Request;
import bbmri.entities.RequestGroup;
import bbmri.entities.User;
import bbmri.entities.enumeration.SystemRole;
import bbmri.service.*;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
public class BasicActionBean extends Links implements ActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /* Shibboleth headers*/

    private static final String SHIB_EPPN = "eppn";

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

    private static final String MY_PROJECTS = "/webpages/project/project_my_projects.jsp";

    private TheActionBeanContext ctx;

    @Override
    public void setContext(ActionBeanContext ctx) {
        this.ctx = (TheActionBeanContext) ctx;
    }

    @Override
    public TheActionBeanContext getContext() {
        return ctx;
    }
//
//    public Resolution primary_menu_project() {
//        return new ForwardResolution(MY_PROJECTS);
//    }

    public User getLoggedUser() {
        Long id = ctx.getMyId();
        return userService.get(id);
    }

    public Set<SystemRole> getRoles() {
        Long id = ctx.getMyId();
        return userService.get(id).getSystemRoles();

    }

    public RequestGroup getRequestGroupBSC() {

        Long id = ctx.getRequestGroupId();
        return requestGroupService.get(id);
    }

    public Request getRequestBSC() {

        Long id = ctx.getRequestId();
        return requestService.get(id);
    }

    public String getName() {
        return this.getClass().getName();
    }


    protected void successMsg(String msg) {
        if (msg == null) {
            getContext().getMessages().add(
                    new LocalizableMessage("bbmri.action.base.BasicActionBean.success"));
            return;
        }
        getContext().getMessages().add(new LocalizableMessage("msg"));
    }



}
