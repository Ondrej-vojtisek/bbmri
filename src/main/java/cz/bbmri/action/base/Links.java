package cz.bbmri.action.base;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.12.13
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */
public class Links {

    /**
     * **********************************
     * LINKS
     * **********************************
     */
    protected static final String INDEX = "/index.jsp";
    protected static final String LOGIN = "/login.jsp";
    private static final String BASIC_PATH = "/webpages";
    /* ********************************** */
    protected static final String DASHBOARD = "/dashboard.jsp";

    // MY ACCOUNT
    private static final String USER = BASIC_PATH + "/user/";
    /************************************/
    // USER
    protected static final String USER_ALL = USER + "all.jsp";
    protected static final String USER_CREATE = USER + "create.jsp";
    protected static final String USER_PERSONAL_DATA = USER + "personal_data.jsp";
    protected static final String USER_ROLES = USER + "roles.jsp";
    protected static final String USER_FIND = USER + "find.jsp";
    protected static final String USER_PASSWORD = USER + "password.jsp";

    // BIOBANK
    private static final String BIOBANK = BASIC_PATH + "/biobank/";
    /* ********************************** */
    protected static final String BIOBANK_ALL = BIOBANK + "all.jsp";


    // BIOBANK/CREATE
    private static final String BIOBANK_CREATE = BIOBANK + "create/";
    /* ********************************** */
    protected static final String BIOBANK_CREATE_GENERAL = BIOBANK_CREATE + "1_general.jsp";
    protected static final String BIOBANK_CREATE_ADMINISTRATORS = BIOBANK_CREATE + "2_administrators.jsp";
    protected static final String BIOBANK_CREATE_CONFIRM = BIOBANK_CREATE + "3_confirm.jsp";

    // BIOBANK/DETAIL
    private static final String BIOBANK_DETAIL = BIOBANK + "detail/";
     /* ********************************** */
    protected static final String BIOBANK_DETAIL_SAMPLES = BIOBANK_DETAIL + "samples.jsp";
    protected static final String BIOBANK_DETAIL_PATIENTS = BIOBANK_DETAIL + "patients.jsp";
    protected static final String BIOBANK_DETAIL_GENERAL = BIOBANK_DETAIL + "general.jsp";
    protected static final String BIOBANK_DETAIL_ADMINISTRATORS = BIOBANK_DETAIL + "administrators.jsp";
    protected static final String BIOBANK_DETAIL_ADD_ADMINISTRATOR = BIOBANK_DETAIL + "addAdministrator.jsp";
    protected static final String BIOBANK_DETAIL_SAMPLE_REQUESTS = BIOBANK_DETAIL + "sampleRequests.jsp";

    // PROJECTS
    private static final String PROJECT = BASIC_PATH + "/project/";
    /* ********************************** */
    protected static final String PROJECT_ALL = PROJECT + "all.jsp";
    protected static final String PROJECT_MY = PROJECT + "all_my.jsp";
    protected static final String PROJECT_CREATE_SAMPLE_REQUEST = PROJECT + "createSampleRequest.jsp";

    // PROJECTS/CREATE
    private static final String PROJECT_DETAIL = PROJECT + "detail/";
    /* ********************************** */
    protected static final String PROJECT_DETAIL_GENERAL = PROJECT_DETAIL + "detail.jsp";
    protected static final String PROJECT_DETAIL_ATTACHMENTS = PROJECT_DETAIL + "attachments.jsp";
    protected static final String PROJECT_DETAIL_ADMINISTRATORS = PROJECT_DETAIL + "administrators.jsp";
    protected static final String PROJECT_DETAIL_ADMINISTRATORS_ADD = PROJECT_DETAIL + "addAdministrator.jsp";
    protected static final String PROJECT_DETAIL_ATTACHMENT_ADD = PROJECT_DETAIL + "addAttachment.jsp";
    protected static final String PROJECT_DETAIL_SAMPLE_REQUESTS = PROJECT_DETAIL + "sampleRequests.jsp";


    // PROJECTS/CREATE
    private static final String PROJECT_CREATE = PROJECT + "create/";
    /* ********************************** */
    protected static final String PROJECT_CREATE_INIT = PROJECT_CREATE + "1_initial.jsp";
    protected static final String PROJECT_CREATE_GENERAL = PROJECT_CREATE + "2_general.jsp";
    protected static final String PROJECT_CREATE_FINANCED = PROJECT_CREATE + "3_financed.jsp";
    protected static final String PROJECT_CREATE_ANNOTATION = PROJECT_CREATE + "4_annotation.jsp";
    protected static final String PROJECT_CREATE_MTA = PROJECT_CREATE + "6_mta.jsp";
    protected static final String PROJECT_CREATE_CONFIRM = PROJECT_CREATE + "5_confirm.jsp";

    protected static final String SAMPLE_REQUEST = "/webpages/project/sample_request.jsp";

     // SUPPORT
    protected static final String SUPPORT = BASIC_PATH + "/support/support.jsp";

    // REQUEST
    private static final String REQUEST = BASIC_PATH + "/request/";
    /* ********************************** */
    protected static final String REQUEST_DETAIL = REQUEST + "detail.jsp";
}
