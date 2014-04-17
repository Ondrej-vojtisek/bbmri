package cz.bbmri.entities.webEntities;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class ComponentManager {
    /* all path names for ribbon, header etc.*/
    public static final String ATTACHMENT_DETAIL = "attachment";
    public static final String BIOBANK_DETAIL = "biobank";
    public static final String BOX_DETAIL = "box";
    public static final String CONTAINER_DETAIL = "container";
    public static final String GENOME_DETAIL = "genome";
    public static final String NOTIFICATION_DETAIL = "notification";
    public static final String PATIENT_DETAIL = "patient";
    public static final String POSITION_DETAIL = "position";
    public static final String PROJECT_DETAIL = "project";
    public static final String RACK_DETAIL = "rack";
    public static final String REQUEST_DETAIL = "request";
    public static final String ROLE_DETAIL = "role";
    public static final String SAMPLE_DETAIL = "sample";
    public static final String SAMPLEQUESTION_DETAIL = "sampleQuestion";
    public static final String SERUM_DETAIL = "serum";
    public static final String TISSUE_DETAIL = "tissue";
    public static final String USER_DETAIL = "user";

    private static final String COMPONENT_PATH = "/webpages/component/detail/";

    private static final String HEADER = "/header.jsp";

    private static final String RIBBON = "/ribbon.jsp";

    private static final String ROW = "/row.jsp";

    private static final String SORTABLE_HEADER = "/sortableHeader.jsp";

    private static final String BREADCRUMBS = "/webpages/component/detail/breadcrumb/breadcrumb.jsp";


    // for instance view of all Samples
    private String primaryObjectName;

    // for instance banner of biobank
    private String secondaryObjectName;

    // In create wizard there is no need to define any object name
    public ComponentManager() {}

    public ComponentManager(String primaryObjectName) {
        this.primaryObjectName = primaryObjectName;
    }

    public ComponentManager(String primaryObjectName, String secondaryObjectName) {
        this.primaryObjectName = primaryObjectName;
        this.secondaryObjectName = secondaryObjectName;
    }

    public String getPrimaryObjectName() {
        return primaryObjectName;
    }

    public void setPrimaryObjectName(String primaryObjectName) {
        this.primaryObjectName = primaryObjectName;
    }

    public String getSecondaryObjectName() {
        return secondaryObjectName;
    }

    public void setSecondaryObjectName(String secondaryObjectName) {
        this.secondaryObjectName = secondaryObjectName;
    }

    public String getTableHeader() {

        return COMPONENT_PATH + primaryObjectName + HEADER;
    }

    public String getTableRow() {
        return COMPONENT_PATH + primaryObjectName + ROW;
    }

    public String getRibbon() {
        return COMPONENT_PATH + primaryObjectName + RIBBON;
    }

    public String getSortableHeader() {
        return COMPONENT_PATH + primaryObjectName + SORTABLE_HEADER;
    }

    public String getSecondaryRibbon() {
        return COMPONENT_PATH + secondaryObjectName + RIBBON;
    }

    public String getBreadcrumbsComponent() {
            return BREADCRUMBS;
        }


}
