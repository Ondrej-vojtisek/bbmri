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

    public static final String LOGIN = "/login.jsp";
    public static final String INDEX = "/index.jsp";


    // Definition of absolute path to view templates source
    private static final String LOCATION = "/webpages/";

    /**
     * Ajax
     */
    public static class Ajax {
        private static final String DIRECTORY = LOCATION + "ajax/";

        public static final String PARTIAL = DIRECTORY + "partial.jsp";

    }


    /**
     * Archive
     */
    public static class Archive {
        private static final String DIRECTORY = LOCATION + "archive/";

        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";

    }

    /**
     * Attachment
     */
    public static class Attachment {
        private static final String DIRECTORY = LOCATION + "attachment/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ADD_BIOBANK_ATTACHMENT = DIRECTORY + "add-to-biobank.jsp";
        public static final String ADD_PROJECT_ATTACHMENT = DIRECTORY + "add-to-project.jsp";

    }

    /**
     * Attachment type
     */
    public static class AttachmentType {
        private static final String DIRECTORY = LOCATION + "attachmentType/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";

    }

    /**
     * Biobank
     */
    public static class Biobank {
        private static final String DIRECTORY = LOCATION + "biobank/";

        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String DETAIL = DIRECTORY + "detail.jsp";
        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ATTACHMENTS = DIRECTORY + "attachments.jsp";
        public static final String BIOBANK_USER = DIRECTORY + "biobankuser.jsp";
        public static final String PATIENTS = DIRECTORY + "patients.jsp";
        public static final String WITHDRAWS = DIRECTORY + "withdraws.jsp";
        public static final String QUESTIONS = DIRECTORY + "questions.jsp";
        public static final String SAMPLES = DIRECTORY + "samples.jsp";

    }

    /**
     * Biobank User
     */
    public static class BiobankUser {
        private static final String DIRECTORY = LOCATION + "biobankUser/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ADD = DIRECTORY + "add.jsp";
    }

    /**
     * Box
     */
    public static class Box {
        private static final String DIRECTORY = LOCATION + "box/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";

    }

    /**
     * Collection
     */
    public static class Collection {
        private static final String DIRECTORY = LOCATION + "collection/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";

    }

    /**
     * Contact
     */
    public static class Contact {
        private static final String DIRECTORY = LOCATION + "contact/";

    }

    /**
     * Container
     */
    public static class Container {
        private static final String DIRECTORY = LOCATION + "container/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Country
     */
    public static class Country {
        private static final String DIRECTORY = LOCATION + "country/";

        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Diagnosis
     */
    public static class Diagnosis {
        private static final String DIRECTORY = LOCATION + "diagnosis/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Global setting
     */
    public static class GlobalSetting {
        private static final String DIRECTORY = LOCATION + "globalSetting/";

    }

    /**
     * Material type
     */
    public static class MaterialType {
        private static final String DIRECTORY = LOCATION + "materialType/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Measurement type
     */
    public static class MeasurementType {
        private static final String DIRECTORY = LOCATION + "measurementType/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Monitoring
     */
    public static class Monitoring {
        private static final String DIRECTORY = LOCATION + "monitoring/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Morphology
     */
    public static class Morphology {
        private static final String DIRECTORY = LOCATION + "morphology/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Notification
     */
    public static class Notification {
        private static final String DIRECTORY = LOCATION + "notification/";

        public static final String DASHBOARD = DIRECTORY + "dashboard.jsp";
        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";

    }

    /**
     * Patient
     */
    public static class Patient {
        private static final String DIRECTORY = LOCATION + "patient/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String DETAIL = DIRECTORY + "detail.jsp";
        public static final String SAMPLES = DIRECTORY + "samples.jsp";
    }

    /**
     * Permission
     */
    public static class Permission {
        private static final String DIRECTORY = LOCATION + "permission/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Position
     */
    public static class Position {
        private static final String DIRECTORY = LOCATION + "position/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Project
     */
    public static class Project {
        private static final String DIRECTORY = LOCATION + "project/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String DETAIL = DIRECTORY + "detail.jsp";
        public static final String ATTACHMENTS = DIRECTORY + "attachments.jsp";
        public static final String PROJECTUSER = DIRECTORY + "projectuser.jsp";
        public static final String QUESTIONS = DIRECTORY + "questions.jsp";

        public static final String CREATE_FIRST = DIRECTORY + "create-1.jsp";
        public static final String CREATE_SECOND = DIRECTORY + "create-2.jsp";
        public static final String CREATE_THIRD = DIRECTORY + "create-3.jsp";
        public static final String CREATE_FOURTH = DIRECTORY + "create-4.jsp";
        public static final String CREATE_FIFTH = DIRECTORY + "create-5.jsp";
    }

    /**
     * Project state
     */
    public static class ProjectState {
        private static final String DIRECTORY = LOCATION + "projectState/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Project user
     */
    public static class ProjectUser {
        private static final String DIRECTORY = LOCATION + "projectUser/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ADD = DIRECTORY + "add.jsp";
    }

    /**
     * PTNM
     */
    public static class Ptnm {
        private static final String DIRECTORY = LOCATION + "Ptnm/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Quantity
     */
    public static class Quantity {
        private static final String DIRECTORY = LOCATION + "quantity/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Question
     */
    public static class Question {
        private static final String DIRECTORY = LOCATION + "question/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String DETAIL = DIRECTORY + "detail.jsp";
        public static final String ADD = DIRECTORY + "add.jsp";
    }

    /**
     * Question state
     */
    public static class QuestionState {
        private static final String DIRECTORY = LOCATION + "questionState/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Record
     */
    public static class Record {
        private static final String DIRECTORY = LOCATION + "record/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Request
     */
    public static class Request {
        private static final String DIRECTORY = LOCATION + "request/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Reservation
     */
    public static class Reservation {
        private static final String DIRECTORY = LOCATION + "reservation/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String DETAIL = DIRECTORY + "detail.jsp";
        public static final String ADD = DIRECTORY + "add.jsp";
    }

    /**
     * Reservation state
     */
    public static class ReservationState {
        private static final String DIRECTORY = LOCATION + "reservationState/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Retrieved
     */
    public static class Retrieved {
        private static final String DIRECTORY = LOCATION + "retrieved/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Role
     */
    public static class Role {
        private static final String DIRECTORY = LOCATION + "role/";

        public static final String USERDETAIL = DIRECTORY + "user-detail.jsp";
        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Sample
     */
    public static class Sample {
        private static final String DIRECTORY = LOCATION + "sample/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String DETAIL = DIRECTORY + "detail.jsp";
    }

    /**
     * Settings
     */
    public static class Settings {
        private static final String DIRECTORY = LOCATION + "settings/";

        public static final String USERDETAIL = DIRECTORY + "user-detail.jsp";
        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Sex
     */
    public static class Sex {
        private static final String DIRECTORY = LOCATION + "sex/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Shibboleth
     */
    public static class Shibboleth {
        private static final String DIRECTORY = LOCATION + "shibboleth/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * TNM
     */
    public static class Tnm {
        private static final String DIRECTORY = LOCATION + "tnm/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * User
     */
    public static class User {
        private static final String DIRECTORY = LOCATION + "user/";

        // Available locations defined within section

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String DETAIL = DIRECTORY + "detail.jsp";
        public static final String PASSWORD = DIRECTORY + "password.jsp";
        public static final String SHIBBOLETH = DIRECTORY + "shibboleth.jsp";

        public static final String TABLE = DIRECTORY + "table.jsp";
    }

    /**
     * Withdraw
     */
    public static class Withdraw {
        private static final String DIRECTORY = LOCATION + "withdraw/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ADD = DIRECTORY + "add.jsp";
    }


}
