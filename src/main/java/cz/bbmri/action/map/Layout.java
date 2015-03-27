package cz.bbmri.action.map;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public enum Layout {

    /**
     * Contains the instance.
     */
    INSTANCE;

    private static final String LOCATION = "/layouts/";

    public String getContent() {
        return LOCATION + "layout-content.jsp";
    }

    public String getEmpty() {
        return LOCATION + "layout-empty.jsp";
    }

//    public String getLogin() {
//        return LOCATION + "layout_login.jsp";
//    }

    public String getErrorOutside() {
        return LOCATION + "layout-error-outside.jsp";
    }

    public String getErrorInside() {
        return LOCATION + "layout-error-inside.jsp";
    }


}
