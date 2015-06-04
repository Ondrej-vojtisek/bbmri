package cz.bbmri.action.map;

import cz.bbmri.entity.*;

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

           private static final String LOCATION = "/webpages/";
           private static final String COMPONENT = "/component/ribbon.jsp";

           public String getArchive() {
               return LOCATION + Archive.FOLDER + COMPONENT;
           }

           public String getBiobank() {
               return LOCATION + Biobank.FOLDER + COMPONENT;
           }

           public String getBiobankUser() {
               return LOCATION + BiobankUser.FOLDER + COMPONENT;
           }

           public String getBox() {
               return LOCATION + Box.FOLDER + COMPONENT;
           }

           public String getCollection() {
               return LOCATION + Collection.FOLDER + COMPONENT;
           }

           public String getMonitoring() {
               return LOCATION + Monitoring.FOLDER + COMPONENT;
           }

           public String getPatient() {
               return LOCATION + Patient.FOLDER + COMPONENT;
           }

           public String getPosition() {
               return LOCATION + Position.FOLDER + COMPONENT;
           }

           public String getProject() {
               return LOCATION + Project.FOLDER + COMPONENT;
           }

           public String getQuestion() {
               return LOCATION + Question.FOLDER + COMPONENT;
           }

           public String getRecord() {
               return LOCATION + Record.FOLDER + COMPONENT;
           }

           public String getReservation() {
               return LOCATION + Reservation.FOLDER + COMPONENT;
           }

           public String getSample() {
               return LOCATION + Sample.FOLDER + COMPONENT;
           }

           public String getUser() {
               return LOCATION + User.FOLDER + COMPONENT;
           }

           public String Withdraw() {
               return LOCATION + Withdraw.FOLDER + COMPONENT;
           }

}
