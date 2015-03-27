package cz.bbmri.action.map;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public enum Ribbon {

    /**
            * Contains the instance.
            */
           INSTANCE;

           private static final String LOCATION = "/webpages/new/";
           private static final String COMPONENT = "/component/ribbon.jsp";

           public String getArchive() {
               return LOCATION + "archive" + COMPONENT;
           }

           public String getBiobank() {
               return LOCATION + "biobank" + COMPONENT;
           }

           public String getBiobankUser() {
               return LOCATION + "biobankUser" + COMPONENT;
           }

           public String getBox() {
               return LOCATION + "box" + COMPONENT;
           }

           public String getCollection() {
               return LOCATION + "collection" + COMPONENT;
           }

           public String getMonitoring() {
               return LOCATION + "monitoring" + COMPONENT;
           }

           public String getPatient() {
               return LOCATION + "patient" + COMPONENT;
           }

           public String getPosition() {
               return LOCATION + "position" + COMPONENT;
           }

           public String getProject() {
               return LOCATION + "project" + COMPONENT;
           }

           public String getQuestion() {
               return LOCATION + "question" + COMPONENT;
           }

           public String getRecord() {
               return LOCATION + "record" + COMPONENT;
           }

           public String getReservation() {
               return LOCATION + "reservation" + COMPONENT;
           }

           public String getSample() {
               return LOCATION + "sample" + COMPONENT;
           }

           public String getUser() {
               return LOCATION + "user" + COMPONENT;
           }

           public String Withdraw() {
               return LOCATION + "withdraw" + COMPONENT;
           }

}
