package cz.bbmri.action.map;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public enum Table {

    /**
     * Contains the instance.
     */
    INSTANCE;

    private static final String LOCATION = "/webpages/component/table/";

    public String getEmptyTable() {
        return LOCATION + "empty.jsp";
    }

    public String getArrows() {
        return LOCATION + "arrows.jsp";
    }

    public String getHeaderLayout() {
           return LOCATION + "header-layout.jsp";
       }

}
