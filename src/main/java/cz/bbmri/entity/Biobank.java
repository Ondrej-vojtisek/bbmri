package cz.bbmri.entity;


import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Institution involved in project - biobank, hospital, third party sample storage.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class Biobank implements Serializable {

    /**
     * Folder names on server
     */
    public final static String BIOBANK_FOLDER = File.separator + "biobank_files";
    private final static String BIOBANK_FOLDER_PATH = BIOBANK_FOLDER + File.separator;
    private final static String PATIENT_DATA_FOLDER = "patient_data";
    private final static String MONITORING_DATA_FOLDER = "monitoring_data";
    private final static String CALIBRATION_DATA_FOLDER = "calibration_data";
    private final static String TEMPERATURE_FOLDER = "temperature_data";
    private final static String PATIENT_DATA_ARCHIVE_FOLDER = "patient_data_archive";
    private final static String MONITORING_DATA_ARCHIVE_FOLDER = "monitoring_data_archive";
    private final static String TEMPERATURE_ARCHIVE_FOLDER = "temperature_data_archive";

    public static final String PROP_ID = "id";
    public static final String PROP_INSTITUTIONAL_ID = "institutionalId";
    public static final String PROP_ACRONYM = "acronym";
    public static final String PROP_NAME = "name";
    public static final String PROP_DESCRIPTION = "description";
    public static final String PROP_COUNTRY = "country";
	public static final String PROP_NAME_ENGLISH = "nameEnglish";
    public static final String PROP_BIOBANK_USER = "biobankUser";
    public static final String PROP_CONTACT = "contact";
    public static final String PROP_PATIENT = "patient";
    public static final String PROP_CONTAINER = "container";
    public static final String PROP_COLLECTION = "collection";
    public static final String PROP_WITHDRAW = "withdraw";
    public static final String PROP_ATTACHMENT = "attachment";

    private int id;

    private String institutionalId;
    private String acronym;
    private String name;
    private String description;
	private String nameEnglish;

    private Set<BiobankUser> biobankUser = new HashSet<BiobankUser>();
    private Contact contact;
    private Set<Patient> patient = new HashSet<Patient>();
    private Set<Container> container = new HashSet<Container>();
    private Set<Collection> collection = new HashSet<Collection>();
    private Set<Withdraw> withdraw = new HashSet<Withdraw>();
    private Set<Attachment> attachment = new HashSet<Attachment>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstitutionalId() {
        return institutionalId;
    }

    public void setInstitutionalId(String institutionalId) {
        this.institutionalId = institutionalId;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<BiobankUser> getBiobankUser() {
        return biobankUser;
    }

    public void setBiobankUser(Set<BiobankUser> biobankUser) {
        this.biobankUser = biobankUser;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Set<Patient> getPatient() {
        return patient;
    }

    public void setPatient(Set<Patient> patient) {
        this.patient = patient;
    }

    public Set<Container> getContainer() {
        return container;
    }

    public void setContainer(Set<Container> container) {
        this.container = container;
    }

    public Set<Collection> getCollection() {
        return collection;
    }

    public void setCollection(Set<Collection> collection) {
        this.collection = collection;
    }

    public Set<Withdraw> getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(Set<Withdraw> withdraw) {
        this.withdraw = withdraw;
    }

    public Set<Attachment> getAttachment() {
        return attachment;
    }

    public void setAttachment(Set<Attachment> attachment) {
        this.attachment = attachment;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getBiobankFolderPath() {
        return Biobank.BIOBANK_FOLDER_PATH + id;
    }

    public String getBiobankMonitoringFolder() {
        return getBiobankFolderPath() + File.separator + Biobank.MONITORING_DATA_FOLDER;
    }

    public String getBiobankMonitoringArchiveFolder() {
        return getBiobankFolderPath() + File.separator + Biobank.MONITORING_DATA_ARCHIVE_FOLDER;
    }

    public String getBiobankPatientDataFolder() {
        return getBiobankFolderPath() + File.separator + Biobank.PATIENT_DATA_FOLDER;
    }

    public String getBiobankPatientArchiveDataFolder() {
        return getBiobankFolderPath() + File.separator + Biobank.PATIENT_DATA_ARCHIVE_FOLDER;
    }

    public String getBiobankTemperatureFolder() {
        return getBiobankFolderPath() + File.separator + Biobank.TEMPERATURE_FOLDER;
    }

    public String getBiobankTemperatureArchiveFolder() {
        return getBiobankFolderPath() + File.separator + Biobank.TEMPERATURE_ARCHIVE_FOLDER;
    }

    public String getBiobankCalibrationDataFolder() {
        return getBiobankFolderPath() + File.separator + Biobank.CALIBRATION_DATA_FOLDER;
    }
}
