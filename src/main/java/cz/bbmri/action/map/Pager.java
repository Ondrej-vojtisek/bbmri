package cz.bbmri.action.map;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public enum Pager {

    /**
     * Contains the instance.
     */
    INSTANCE;

    private static final String LOCATION = "/webpages/component/pager/";

    public String getPager() {
        return LOCATION + "pager.jsp";
    }
}
