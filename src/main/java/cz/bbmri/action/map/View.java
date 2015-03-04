package cz.bbmri.action.map;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public enum View {

    /**
     * Contains the instance.
     */
    INSTANCE;

    // Definition of absolute path to view templates source
    private static final String LOCATION = "/webpages";

    public static class User{
        private static final String DIRECTORY = "user/";

        // Available locations defined within section

        public static final String NOTFOUND = LOCATION + DIRECTORY + "not-found.jsp";
        public static final String ALL = LOCATION + DIRECTORY + "default.jsp";
        public static final String DETAIL = LOCATION + DIRECTORY + "detail.jsp";
        public static final String PERSON_DETAIL = LOCATION + DIRECTORY + "personDetail.jsp";
        public static final String ADD = LOCATION + DIRECTORY + "add.jsp";
    }

}
