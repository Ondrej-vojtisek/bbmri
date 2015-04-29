package cz.bbmri.action.map;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public enum Menu {

    /**
     * Contains the instance.
     */
    INSTANCE;

    private static final String LOCATION = "/webpages/";
    private static final String COMPONENT = "/component/menu.jsp";

    public String getBiobank() {
        return LOCATION + "biobank" + COMPONENT;
    }

    public String getPatient() {
        return LOCATION + "patient" + COMPONENT;
    }

    public String getProject() {
        return LOCATION + "project" + COMPONENT;
    }

    public String getSample() {
        return LOCATION + "sample" + COMPONENT;
    }

    public String getUser() {
        return LOCATION + "user" + COMPONENT;
    }


}
