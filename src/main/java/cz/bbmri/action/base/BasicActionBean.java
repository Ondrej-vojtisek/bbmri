package cz.bbmri.action.base;

import cz.bbmri.action.DashboardActionBean;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.enumeration.ProjectState;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.extension.context.TheActionBeanContext;
import cz.bbmri.extension.localization.LocalePicker;
import cz.bbmri.facade.ProjectFacade;
import cz.bbmri.facade.UserFacade;
import cz.bbmri.facade.exceptions.AuthorizationException;
import cz.bbmri.service.*;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
@HttpCache(allow = false)
public class BasicActionBean extends Links implements ActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private UserFacade userFacade;

    @SpringBean
    private ProjectFacade projectFacade;

    /* Shibboleth headers*/

    private static final String SHIB_EPPN = "eppn";

//    @SpringBean
//    protected UserService userService;
//
//    @SpringBean
//    protected BiobankService biobankService;
//
//    @SpringBean
//    protected ProjectService projectService;
//
//    @SpringBean
//    protected RequestService requestService;
//
//    @SpringBean
//    protected SampleService sampleService;
//
//    @SpringBean
//    protected RequestGroupService requestGroupService;
//
//    @SpringBean
//    protected SampleQuestionService sampleQuestionService;
//
//    @SpringBean
//    protected AttachmentService attachmentService;

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

    public boolean isShibbolethUser() {
        return null != getContext().getShibbolethSession();
    }

    public User initializeShibbolethUser() {
        User user = new User();
        user.setDisplayName(getContext().getShibbolethDisplayName());
        user.setEmail(getContext().getShibbolethMail());
        user.setEppn(getContext().getShibbolethEppn());
        user.setTargetedId(getContext().getShibbolethTargetedId());
        user.setPersistentId(getContext().getShibbolethPersistentId());
        user.setOrganization(getContext().getShibbolethOrganization());
        user.setAffiliation(getContext().getShibbolethAffiliation());
        user.setName(getContext().getShibbolethGivenName());
        user.setSurname(getContext().getShibbolethSn());
        user.setShibbolethUser(true);
        return user;
    }

    public boolean shibbolethSignIn(User user) {
        Long id = null;

        try {
            id = userFacade.loginShibbolethUser(user);
            logger.debug("User have sufficient rights to access BBMRI - " +
                    "user: " + user + "affiliation: " + user.getAffiliation());
        } catch (AuthorizationException ex) {
            logger.debug("User doesn't have sufficient rights to access BBMRI - " +
                    "user: " + user + "affiliation: " + user.getAffiliation());
            return false;
        }

        if (id == null) {
            logger.debug("Id of user is null during sign in - " +
                    "user: " + user);
            return false;
        }

        getContext().setMyId(id);

        return true;
    }

    // This method is used if user is accessing LoginActionBean
    // It is dedicated for user accessing directly index page
    public Resolution signInShibbolethOnIndex() {
        User user = initializeShibbolethUser();

        if (!shibbolethSignIn(user)) {
            return new RedirectResolution("/errors/not_authorized_to_access.jsp");
        }

        // Continue to Dashboard
        return new RedirectResolution(DashboardActionBean.class);
    }


    public User getLoggedUser() {
        Long id = ctx.getMyId();
        return userFacade.get(id);
    }

    public Set<SystemRole> getRoles() {
        Long id = ctx.getMyId();
        return userFacade.get(id).getSystemRoles();

    }

    public String getName() {
        return this.getClass().getName();
    }


    protected void successMsg(String msg) {
        if (msg == null) {
            getContext().getMessages().add(
                    new LocalizableMessage("cz.bbmri.action.base.BasicActionBean.success"));
            return;
        }
        getContext().getMessages().add(new LocalizableMessage("msg"));
    }

    public String getLastUrl() {
        HttpServletRequest req = getContext().getRequest();
        StringBuilder sb = new StringBuilder();

        // Start with the URI and the path
        String uri = (String)
                req.getAttribute("javax.servlet.forward.request_uri");
        String path = (String)
                req.getAttribute("javax.servlet.forward.path_info");
        if (uri == null) {
            uri = req.getRequestURI();
            path = req.getPathInfo();
        }
        sb.append(uri);
        if (path != null) {
            sb.append(path);
        }

        // Now the request parameters
        sb.append('?');
        Map<String, String[]> map =
                new HashMap<String, String[]>(req.getParameterMap());

        // Remove previous locale parameter, if present.
        map.remove(LocalePicker.LOCALE);

        // Append the parameters to the URL
        for (String key : map.keySet()) {
            String[] values = map.get(key);
            for (String value : values) {
                sb.append(key).append('=').append(value).append('&');
            }
        }
        // Remove the last '&'
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

//    public boolean getAllowedManager() {
//        return projectFacade.hasPermission(Permission.MANAGER, id, getContext().getMyId()) && !isFinished();
//    }
//
//    public boolean getAllowedEditor() {
//        return projectFacade.hasPermission(Permission.EDITOR, id, getContext().getMyId()) && !isFinished();
//    }
//
//    public boolean getAllowedExecutor() {
//        return projectFacade.hasPermission(Permission.EXECUTOR, id, getContext().getMyId()) && !isFinished();
//    }
//
//    public boolean getAllowedVisitor() {
//        return projectFacade.hasPermission(Permission.VISITOR, id, getContext().getMyId());
//    }

}
