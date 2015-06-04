package cz.bbmri.action.map;

import cz.bbmri.entity.*;

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
        return LOCATION + Biobank.FOLDER + COMPONENT;
    }

    public String getPatient() {
        return LOCATION + Patient.FOLDER + COMPONENT;
    }

    public String getProject() {
        return LOCATION + Project.FOLDER + COMPONENT;
    }

    public String getSample() {
        return LOCATION + Sample.FOLDER + COMPONENT;
    }

    public String getUser() {
        return LOCATION + User.FOLDER + COMPONENT;
    }


}
