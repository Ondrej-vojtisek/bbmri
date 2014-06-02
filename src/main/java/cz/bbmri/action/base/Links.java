package cz.bbmri.action.base;

/**
 * All .jsp files are defined here
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class Links {

    protected static final String INDEX = "/index.jsp";
    protected static final String LOGIN = "/login.jsp";
    protected static final String DASHBOARD = "/dashboard.jsp";

    private static final String BASIC_PATH = "/webpages";

    // user folder
    private static final String USER = BASIC_PATH + "/user/";
    protected static final String USER_ALL = USER + "all.jsp";
    protected static final String USER_CREATE = USER + "create.jsp";
    protected static final String USER_PERSONAL_DATA = USER + "personal_data.jsp";
    protected static final String USER_ROLES = USER + "roles.jsp";
    protected static final String USER_FIND = USER + "find.jsp";
    protected static final String USER_PASSWORD = USER + "password.jsp";
    protected static final String USER_SETTING = USER + "mySetting.jsp";

    // biobank folder
    private static final String BIOBANK = BASIC_PATH + "/biobank/";
    protected static final String BIOBANK_ALL = BIOBANK + "all.jsp";

    // biobank/create folder
    private static final String BIOBANK_CREATE = BIOBANK + "create/";
    protected static final String BIOBANK_CREATE_GENERAL = BIOBANK_CREATE + "1_general.jsp";
    protected static final String BIOBANK_CREATE_ADMINISTRATORS = BIOBANK_CREATE + "2_administrators.jsp";
    protected static final String BIOBANK_CREATE_CONFIRM = BIOBANK_CREATE + "3_confirm.jsp";

    // biobank/detail folder
    private static final String BIOBANK_DETAIL = BIOBANK + "detail/";
    protected static final String BIOBANK_DETAIL_SAMPLES = BIOBANK_DETAIL + "samples.jsp";
    protected static final String BIOBANK_DETAIL_PATIENTS = BIOBANK_DETAIL + "patients.jsp";
    protected static final String BIOBANK_DETAIL_GENERAL = BIOBANK_DETAIL + "general.jsp";
    protected static final String BIOBANK_DETAIL_ADMINISTRATORS = BIOBANK_DETAIL + "administrators.jsp";
    protected static final String BIOBANK_DETAIL_ADD_ADMINISTRATOR = BIOBANK_DETAIL + "addAdministrator.jsp";
    protected static final String BIOBANK_DETAIL_SAMPLE_REQUESTS = BIOBANK_DETAIL + "sampleRequests.jsp";
    protected static final String BIOBANK_DETAIL_MONITORING = BIOBANK_DETAIL + "monitoring.jsp";
    protected static final String BIOBANK_DETAIL_ATTACHMENTS = BIOBANK_DETAIL + "attachments.jsp";
    protected static final String BIOBANK_DETAIL_ATTACHMENT_ADD = BIOBANK_DETAIL + "addAttachment.jsp";
    protected static final String BIOBANK_DETAIL_WITHDRAWN = BIOBANK_DETAIL + "withdrawn.jsp";

    // project folder
    private static final String PROJECT = BASIC_PATH + "/project/";
    protected static final String PROJECT_ALL = PROJECT + "all.jsp";
    protected static final String PROJECT_MY = PROJECT + "all_my.jsp";

    // project/detail folder
    private static final String PROJECT_DETAIL = PROJECT + "detail/";
    protected static final String PROJECT_DETAIL_GENERAL = PROJECT_DETAIL + "detail.jsp";
    protected static final String PROJECT_DETAIL_ATTACHMENTS = PROJECT_DETAIL + "attachments.jsp";
    protected static final String PROJECT_DETAIL_ADMINISTRATORS = PROJECT_DETAIL + "administrators.jsp";
    protected static final String PROJECT_DETAIL_ADMINISTRATORS_ADD = PROJECT_DETAIL + "addAdministrator.jsp";
    protected static final String PROJECT_DETAIL_ATTACHMENT_ADD = PROJECT_DETAIL + "addAttachment.jsp";
    protected static final String PROJECT_DETAIL_SAMPLE_REQUESTS = PROJECT_DETAIL + "sampleRequests.jsp";

    // project/create folder
    private static final String PROJECT_CREATE = PROJECT + "create/";
    protected static final String PROJECT_CREATE_INIT = PROJECT_CREATE + "1_initial.jsp";
    protected static final String PROJECT_CREATE_GENERAL = PROJECT_CREATE + "2_general.jsp";
    protected static final String PROJECT_CREATE_FINANCED = PROJECT_CREATE + "3_financed.jsp";
    protected static final String PROJECT_CREATE_ANNOTATION = PROJECT_CREATE + "4_annotation.jsp";
    protected static final String PROJECT_CREATE_MTA = PROJECT_CREATE + "6_mta.jsp";
    protected static final String PROJECT_CREATE_CONFIRM = PROJECT_CREATE + "5_confirm.jsp";

    // support folder
    protected static final String SUPPORT = BASIC_PATH + "/support/support.jsp";
    protected static final String ARCHIVE = BASIC_PATH + "/support/archive.jsp";

    // request folder
    private static final String REQUEST = BASIC_PATH + "/request/";
    protected static final String REQUEST_DETAIL = REQUEST + "detail.jsp";
    protected static final String CREATE_REQUESTS = REQUEST + "create/createRequests.jsp";
    protected static final String REQUEST_CREATE_SAMPLE_REQUEST = REQUEST + "create/createSampleRequest.jsp";
    protected static final String REQUEST_CREATE_SAMPLE_RESERVATION = REQUEST + "create/createSampleReservation.jsp";
    protected static final String REQUEST_ASSIGN_RESERVATION_TO_PROJECT = REQUEST + "assignToProject.jsp";
    protected static final String REQUEST_EXPORT = REQUEST + "export.jsp";

    // sample folder
    private static final String SAMPLE = BASIC_PATH + "/sample/";
    protected static final String SAMPLE_DETAIL = SAMPLE + "detail.jsp";
    protected static final String SAMPLE_RESERVATIONS = SAMPLE + "reservations.jsp";
    protected static final String SAMPLE_PROJECTS = SAMPLE + "projects.jsp";
    protected static final String SAMPLE_POSITIONS = SAMPLE + "positions.jsp";

    // sample/create folder
    private static final String SAMPLE_CREATE = SAMPLE + "create/";
    protected static final String SAMPLE_CREATE_INITIAL = SAMPLE_CREATE + "1_initial.jsp";
    protected static final String SAMPLE_CREATE_TYPE = SAMPLE_CREATE + "2_type.jsp";
    protected static final String SAMPLE_CREATE_CLASIFICATIONS = SAMPLE_CREATE + "3_clasifications.jsp";
    protected static final String SAMPLE_CREATE_CONFIRM = SAMPLE_CREATE + "4_confirm.jsp";

    // patient folder
    private static final String PATIENT = BASIC_PATH + "/patient/";
    protected static final String PATIENT_DETAIL = PATIENT + "detail.jsp";
    protected static final String PATIENT_MODULESTS = PATIENT + "modulests.jsp";
    protected static final String PATIENT_MODULELTS = PATIENT + "modulelts.jsp";
    protected static final String PATIENT_CREATE_INITIAL = PATIENT + "create/1_initial.jsp";
    protected static final String PATIENT_FIND = PATIENT + "findPatient.jsp";

    // infrastructure folder
    private static final String INFRASTRUCTURE = BASIC_PATH + "/infrastructure/";

    // infrastructure/create folder
    private static final String INFRASTRUCTURE_CREATE = INFRASTRUCTURE + "create/";

    protected static final String INFRASTRUCTURE_CREATE_CONTAINER = INFRASTRUCTURE_CREATE + "createContainer.jsp";
    protected static final String INFRASTRUCTURE_CREATE_RACK = INFRASTRUCTURE_CREATE + "createRack.jsp";
    protected static final String INFRASTRUCTURE_CREATE_RACKBOX = INFRASTRUCTURE_CREATE + "createRackBox.jsp";
    protected static final String INFRASTRUCTURE_CREATE_STANDALONEBOX = INFRASTRUCTURE_CREATE + "createStandaloneBox.jsp";
    protected static final String INFRASTRUCTURE_CREATE_MONITORING = INFRASTRUCTURE_CREATE + "createMonitoring.jsp";

    // infrastructure/detail folder
    private static final String INFRASTRUCTURE_DETAIL = INFRASTRUCTURE + "detail/";
    protected static final String INFRASTRUCTURE_DETAIL_INFRASTRUCTURE = INFRASTRUCTURE_DETAIL + "infrastructureDetail.jsp";
    protected static final String INFRASTRUCTURE_DETAIL_CONTAINER = INFRASTRUCTURE_DETAIL + "containerDetail.jsp";
    protected static final String INFRASTRUCTURE_DETAIL_BOX = INFRASTRUCTURE_DETAIL + "boxDetail.jsp";
    protected static final String INFRASTRUCTURE_DETAIL_RACK = INFRASTRUCTURE_DETAIL + "rackDetail.jsp";
    protected static final String INFRASTRUCTURE_DETAIL_MONITORING = INFRASTRUCTURE_DETAIL + "monitoring.jsp";

    // reservation folder
    private static final String RESERVATION = BASIC_PATH + "/reservation/";
    protected static final String RESERVATION_ALL = RESERVATION + "all.jsp";

    // global settings folder
    protected static final String GLOBAL_SETTINGS = BASIC_PATH + "/globalSettings/settings.jsp";
}
