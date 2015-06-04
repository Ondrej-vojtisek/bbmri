package cz.bbmri.action.map;

import cz.bbmri.entity.Archive;

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



    public static class Error {

        private static final String DIRECTORY = LOCATION + "errors/";

        public static final String NOT_AUTHORIZED = DIRECTORY + "not-authorized.jsp";
    }



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
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Archive.FOLDER + "/";

        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";

    }

    /**
     * Attachment
     */
    public static class Attachment {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Attachment.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ADD_BIOBANK_ATTACHMENT = DIRECTORY + "add-to-biobank.jsp";
        public static final String ADD_PROJECT_ATTACHMENT = DIRECTORY + "add-to-project.jsp";

    }

    /**
     * Attachment type
     */
    public static class AttachmentType {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.AttachmentType.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";

    }

    /**
     * Biobank
     */
    public static class Biobank {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Biobank.FOLDER + "/";

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
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.BiobankUser.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ADD = DIRECTORY + "add.jsp";
    }

    /**
     * Box
     */
    public static class Box {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Box.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";

    }

    /**
     * Collection
     */
    public static class Collection {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Collection.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";

    }

    /**
     * Contact
     */
    public static class Contact {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Contact.FOLDER + "/";

    }

    /**
     * Container
     */
    public static class Container {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Container.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Country
     */
    public static class Country {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Country.FOLDER + "/";

        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Diagnosis
     */
    public static class Diagnosis {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Diagnosis.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Global setting
     */
    public static class GlobalSetting {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.GlobalSetting.FOLDER + "/";

    }

    /**
     * Material type
     */
    public static class MaterialType {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.MaterialType.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ALL = DIRECTORY + "all.jsp";
    }

    /**
     * Measurement type
     */
    public static class MeasurementType {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.MeasurementType.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Monitoring
     */
    public static class Monitoring {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Monitoring.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Morphology
     */
    public static class Morphology {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Morphology.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Notification
     */
    public static class Notification {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Notification.FOLDER + "/";

        public static final String DASHBOARD = DIRECTORY + "dashboard.jsp";
        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";

    }

    /**
     * Patient
     */
    public static class Patient {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Patient.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String DETAIL = DIRECTORY + "detail.jsp";
        public static final String SAMPLES = DIRECTORY + "samples.jsp";
    }

    /**
     * Permission
     */
    public static class Permission {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Permission.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Position
     */
    public static class Position {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Position.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Project
     */
    public static class Project {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Project.FOLDER + "/";

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
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.ProjectState.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Project user
     */
    public static class ProjectUser {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.ProjectUser.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ADD = DIRECTORY + "add.jsp";
    }

    /**
     * PTNM
     */
    public static class Ptnm {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Ptnm.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Quantity
     */
    public static class Quantity {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Quantity.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Question
     */
    public static class Question {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Question.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String DETAIL = DIRECTORY + "detail.jsp";
        public static final String ADD = DIRECTORY + "add.jsp";
    }

    /**
     * Question state
     */
    public static class QuestionState {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.QuestionState.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Record
     */
    public static class Record {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Record.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Request
     */
    public static class Request {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Request.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Reservation
     */
    public static class Reservation {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Reservation.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String DETAIL = DIRECTORY + "detail.jsp";
        public static final String ADD = DIRECTORY + "add.jsp";
    }

    /**
     * Reservation state
     */
    public static class ReservationState {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.ReservationState.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Retrieved
     */
    public static class Retrieved {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Retrieved.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Role
     */
    public static class Role {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Role.FOLDER + "/";

        public static final String USERDETAIL = DIRECTORY + "user-detail.jsp";
        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Sample
     */
    public static class Sample {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Sample.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String ALL = DIRECTORY + "all.jsp";
        public static final String DETAIL = DIRECTORY + "detail.jsp";
    }

    /**
     * Settings
     */
    public static class Settings {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Settings.FOLDER + "/";

        public static final String USERDETAIL = DIRECTORY + "user-detail.jsp";
        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Sex
     */
    public static class Sex {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Sex.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * Shibboleth
     */
    public static class Shibboleth {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Shibboleth.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * TNM
     */
    public static class Tnm {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Tnm.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
    }

    /**
     * User
     */
    public static class User {
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.User.FOLDER + "/";

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
        private static final String DIRECTORY = LOCATION + cz.bbmri.entity.Withdraw.FOLDER + "/";

        public static final String NOTFOUND = DIRECTORY + "not-found.jsp";
        public static final String DETAIL = DIRECTORY + "detail.jsp";
    }


}
