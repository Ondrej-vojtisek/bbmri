package cz.bbmri.action.base;

import cz.bbmri.extension.context.TheActionBeanContext;
import cz.bbmri.entities.Request;
import cz.bbmri.entities.RequestGroup;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.extension.localization.LocalePicker;
import cz.bbmri.service.*;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
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
           if (path != null) { sb.append(path); }

           // Now the request parameters
           sb.append('?');
           Map<String,String[]> map =
               new HashMap<String,String[]>(req.getParameterMap());

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

}
