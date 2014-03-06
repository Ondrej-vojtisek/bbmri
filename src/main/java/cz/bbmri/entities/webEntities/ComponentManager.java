package cz.bbmri.entities.webEntities;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 6.3.14
 * Time: 19:05
 * To change this template use File | Settings | File Templates.
 */
public class ComponentManager {

    private static final String COMPONENT_PATH = "/webpages/component/detail/";

    private static final String HEADER = "/header.jsp";

    private static final String RIBBON = "/ribbon.jsp";

    private static final String ROW = "/row.jsp";

    private static final String SORTABLE_HEADER = "/sortableHeader.jsp";

    private String objectName;

    public ComponentManager(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getTableHeader() {

        return COMPONENT_PATH + objectName + HEADER;
    }

    public String getTableRow() {
        return COMPONENT_PATH + objectName + ROW;
    }

    public String getRibbon() {
        return COMPONENT_PATH + objectName + RIBBON;
    }

    public String getSortableHeader() {
        return COMPONENT_PATH + objectName + SORTABLE_HEADER;
    }
}
