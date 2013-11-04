package bbmri.action;

import bbmri.entities.*;
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
public class BasicActionBean implements ActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /*************************************
    * LINKS
    **************************************/
    private static final String BASIC_PATH = "/webpages";
    /*************************************/

    // MY ACCOUNT
    private static final String USER = BASIC_PATH + "/user/";
    /*************************************/

    // USER
    protected static final String USER_ALL = USER + "all.jsp";
    protected static final String USER_CREATE = USER + "create.jsp";
    protected static final String USER_PERSONAL_DATA = USER + "personal_data.jsp";
    protected static final String USER_ROLES = USER + "roles.jsp";
    protected static final String USER_FIND = USER + "find.jsp";
    protected static final String USER_PASSWORD = USER + "password.jsp";

    // BIOBANK
    private static final String BIOBANK = BASIC_PATH + "/biobank/";
    /*************************************/
    protected static final String BIOBANK_ALL = BIOBANK + "all.jsp";
    protected static final String BIOBANK_CREATE = BIOBANK + "create.jsp";
    protected static final String BIOBANK_EDIT = BIOBANK + "detail_W.jsp";
    protected static final String BIOBANK_DETAIL = BIOBANK + "detail_R.jsp";
    protected static final String BIOBANK_ADMINISTRATORS = BIOBANK + "administrators_R.jsp";
    protected static final String BIOBANK_ADMINISTRATORS_WRITE = BIOBANK + "administrators_W.jsp";
    protected static final String BIOBANK_ADD_ADMINISTRATOR = BIOBANK + "addAdministrator.jsp";
    protected static final String BIOBANK_CREATE_ADMINISTRATORS = BIOBANK + "create_administrators.jsp";
    protected static final String BIOBANK_CREATE_CONFIRM = BIOBANK + "confirm.jsp";
    protected static final String BIOBANK_SAMPLES = BIOBANK + "samples.jsp";



    // PROJECTS
    protected static final String PROJECT_MY_PROJECTS = "/webpages/project/project_my_projects.jsp";

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

    public Resolution primary_menu_project(){
        return new ForwardResolution(MY_PROJECTS);
    }

    public User getLoggedUser(){
        Long id = ctx.getMyId();
        return userService.get(id);
    }

    public Set<SystemRole> getRoles(){
        Long id = ctx.getMyId();
        return userService.get(id).getSystemRoles();

    }

    public RequestGroup getRequestGroupBSC(){

            Long id = ctx.getRequestGroupId();
            return requestGroupService.get(id);
        }

    public Request getRequestBSC(){

               Long id = ctx.getRequestId();
               return requestService.get(id);
           }

    public String getPrimaryMenu(){
       String s = getContext().getRequest().getServletPath();
        /* for example: ServletPath: /webpages/account/personal_data.jsp*/
        s = s.substring(10);
        /* for example: ServletPath: account/personal_data.jsp*/
       /* return for example: ServletPath: account*/
        return s.substring(0, s.indexOf("/"));
    }

    public String getSecondaryMenu(){
           String s = getContext().getRequest().getServletPath();
            /* for example: ServletPath: /webpages/account/personal_data.jsp*/
            s = s.substring(10);
            /* for example: account/personal_data.jsp*/
            s = s.substring(s.indexOf("/") + 1);
            /* for example: personal_data.jsp*/
           /* return for example: ServletPath: my_account*/
            return s.substring(0, s.indexOf("."));
        }

    public String getLastUrl(){
        // TODO
        return "LastURL - not implemented yet";
    }

    public String getName(){
       return this.getClass().getName();
    }



}
