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
    private static final String LOCATION = "/webpages/new/";

    public static class Archive {
        private static final String DIRECTORY = LOCATION + "archive/";

    }

    public static class Attachment {
        private static final String DIRECTORY = LOCATION + "attachment/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ADD_BIOBANK_ATTACHMENT = DIRECTORY + "add-to-biobank.jsp";
        public static final String ADD_PROJECT_ATTACHMENT = DIRECTORY + "add-to-project.jsp";

    }

    public static class AttachmentType {
        private static final String DIRECTORY = LOCATION + "attachmentType/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";

    }


    public static class Biobank {
        private static final String DIRECTORY = LOCATION + "biobank/";

        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String DETAIL = DIRECTORY + "detail.jsp";
        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ATTACHMENTS = DIRECTORY + "attachments.jsp";

    }

    public static class BiobankUser {
        private static final String DIRECTORY = LOCATION + "biobankUser/";

    }

    public static class Box {
        private static final String DIRECTORY = LOCATION + "box/";

    }

    public static class Collection {
        private static final String DIRECTORY = LOCATION + "collection/";

    }

    public static class Contact {
        private static final String DIRECTORY = LOCATION + "contact/";
    }

    public static class Container {
        private static final String DIRECTORY = LOCATION + "container/";
    }

    public static class Country {
        private static final String DIRECTORY = LOCATION + "country/";

        public static final String ALL = DIRECTORY + "all.jsp";
    }

    public static class Diagnosis {
        private static final String DIRECTORY = LOCATION + "diagnosis/";
    }

    public static class GlobalSetting {
        private static final String DIRECTORY = LOCATION + "globalSetting/";
    }

    public static class MaterialType {
        private static final String DIRECTORY = LOCATION + "materialType/";
    }

    public static class MeasurementType {
        private static final String DIRECTORY = LOCATION + "measurementType/";
    }

    public static class Monitoring {
        private static final String DIRECTORY = LOCATION + "monitoring/";
    }

    public static class Morphology {
        private static final String DIRECTORY = LOCATION + "morphology/";
    }

    public static class Notification {
        private static final String DIRECTORY = LOCATION + "notification/";

        public static final String DASHBOARD = DIRECTORY + "dashboard.jsp";

    }

    public static class Patient {
        private static final String DIRECTORY = LOCATION + "patient/";
    }

    public static class Permission {
        private static final String DIRECTORY = LOCATION + "permission/";
    }

    public static class Position {
        private static final String DIRECTORY = LOCATION + "position/";
    }

    public static class Project {
        private static final String DIRECTORY = LOCATION + "project/";
    }

    public static class ProjectState {
        private static final String DIRECTORY = LOCATION + "projectState/";
    }

    public static class ProjectUser {
        private static final String DIRECTORY = LOCATION + "projectUser/";
    }

    public static class Ptnm {
        private static final String DIRECTORY = LOCATION + "Ptnm/";
    }

    public static class Quantity {
        private static final String DIRECTORY = LOCATION + "quantity/";
    }

    public static class Question {
        private static final String DIRECTORY = LOCATION + "question/";
    }

    public static class QuestionState {
        private static final String DIRECTORY = LOCATION + "questionState/";
    }

    public static class Record {
        private static final String DIRECTORY = LOCATION + "record/";
    }

    public static class Request {
        private static final String DIRECTORY = LOCATION + "request/";
    }

    public static class Reservation {
        private static final String DIRECTORY = LOCATION + "reservation/";
    }

    public static class ReservationState {
        private static final String DIRECTORY = LOCATION + "reservationState/";
    }

    public static class Retrieved {
        private static final String DIRECTORY = LOCATION + "retrieved/";
    }

    public static class Role {
        private static final String DIRECTORY = LOCATION + "role/";

        public static final String USERDETAIL = DIRECTORY + "user-detail.jsp";
    }

    public static class Sample {
        private static final String DIRECTORY = LOCATION + "sample/";
    }

    public static class Settings {
        private static final String DIRECTORY = LOCATION + "settings/";

        public static final String USERDETAIL = DIRECTORY + "user-detail.jsp";
    }

    public static class Sex {
        private static final String DIRECTORY = LOCATION + "sex/";
    }

    public static class Shibboleth {
        private static final String DIRECTORY = LOCATION + "shibboleth/";
    }

    public static class Tnm {
        private static final String DIRECTORY = LOCATION + "tnm/";
    }

    public static class User {
        private static final String DIRECTORY = LOCATION + "user/";

        // Available locations defined within section

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String DETAIL = DIRECTORY + "detail.jsp";
        public static final String PASSWORD = DIRECTORY + "password.jsp";
        public static final String SHIBBOLETH = DIRECTORY + "shibboleth.jsp";
    }


    public static class Withdraw {
        private static final String DIRECTORY = LOCATION + "withdraw/";
    }


}
