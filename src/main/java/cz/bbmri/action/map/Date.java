package cz.bbmri.action.map;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public enum Date {

    /**
     * Contains the instance.
     */
    INSTANCE;

    private static final String LOCATION = "/webpages/new/component/date/";

    public String getDate() {
        return LOCATION + "date.jsp";
    }


}
