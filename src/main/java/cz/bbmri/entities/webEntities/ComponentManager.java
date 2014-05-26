package cz.bbmri.entities.webEntities;

/**
 * Manager of component printed on .jsp pages. Components are used the minimize of copied code of .jsp pages. During
 * call of actionBean constructor also the component managed is initialized.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class ComponentManager {
    /**
     * Name of folders with components
     */
    public static final String PROJECT_ATTACHMENT_DETAIL = "attachmentProject";
    public static final String BIOBANK_ATTACHMENT_DETAIL = "attachmentBiobank";
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
    public static final String ARCHIVE_DETAIL = "archive";

    /**
     * Path to component structure
     */
    private static final String COMPONENT_PATH = "/webpages/component/detail/";

    /**
     * Table header of given type
     */
    private static final String HEADER = "/header.jsp";

    /**
     * One one-row table of ribbon - combination of table header and data about one instance
     */
    private static final String RIBBON = "/ribbon.jsp";

    /**
     * Row of table
     */
    private static final String ROW = "/row.jsp";

    /**
     * Table header with active header which allows to sort by clicking
     */
    private static final String SORTABLE_HEADER = "/sortableHeader.jsp";

    /**
     * Breadcrumb component
     */
    private static final String BREADCRUMBS = "/webpages/component/detail/breadcrumb/breadcrumb.jsp";

    /**
     * Name of primary object used in .jsp managed by actionBean
     */
    private String primaryObjectName;

    /**
     * Name of secondary object used in .jsp managed by actionBean. For instance on sample detail we want to print
     * component with sample data as primary component and data about patient as secondary
     */
    private String secondaryObjectName;

    // In create wizard there is no need to define any object name
    public ComponentManager() {
    }

    /**
     * PrimaryObject is enough
     *
     * @param primaryObjectName
     */
    public ComponentManager(String primaryObjectName) {
        this.primaryObjectName = primaryObjectName;
    }

    /**
     * Both objects primary and secondary are defined
     *
     * @param primaryObjectName
     * @param secondaryObjectName
     */
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

/*
Way how to easily access component from component
 */
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
