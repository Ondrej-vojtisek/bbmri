package cz.bbmri.entity;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Research Project uploaded into system
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class Project implements Serializable {

    public static final String FOLDER = "project";

    public static final String PROP_ID = "id";
    public static final String PROP_NAME = "name";
    public static final String PROP_ANNOTATION = "annotation";
    public static final String PROP_FUNDING_ORGANIZATION = "fundingOrganization";
    public static final String PROP_APPROVAL_DATE = "approvalDate";
    public static final String PROP_CREATED = "created";
    public static final String PROP_PRINCIPAL_INVESTIGATOR = "principalInvestigator";
    public static final String PROP_APPROVAL_STORAGE = "approvalStorage";
    public static final String PROP_PROJECT_STATE = "projectState";
    public static final String PROP_PROJECT_USER = "projectUser";
    public static final String PROP_QUESTION = "question";
    public static final String PROP_ATTACHMENT = "attachment";
    public static final String PROP_CLINICAL_TRIAL = "clinicalTrial";
   	public static final String PROP_EUDRA_CT_NUMBER = "eudraCtNumber";

    public final static String PROJECT_FOLDER = File.separator + "project_files";
    private final static String PROJECT_FOLDER_PATH = PROJECT_FOLDER + File.separator;


    private long id;
    private String name;
    private String annotation;
    private String fundingOrganization;
    private Date approvalDate;
    private Date created = new Date();
    private String principalInvestigator;
    private String approvalStorage;
    private ProjectState projectState = ProjectState.NEW;
    private boolean clinicalTrial = false;
    private String eudraCtNumber;

    private Set<ProjectUser> projectUser = new HashSet<ProjectUser>();
    private Set<Question> question = new HashSet<Question>();
    private Set<Attachment> attachment = new HashSet<Attachment>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getFundingOrganization() {
        return fundingOrganization;
    }

    public void setFundingOrganization(String fundingOrganization) {
        this.fundingOrganization = fundingOrganization;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getPrincipalInvestigator() {
        return principalInvestigator;
    }

    public void setPrincipalInvestigator(String principalInvestigator) {
        this.principalInvestigator = principalInvestigator;
    }

    public String getApprovalStorage() {
        return approvalStorage;
    }

    public void setApprovalStorage(String approvalStorage) {
        this.approvalStorage = approvalStorage;
    }

    public ProjectState getProjectState() {
        return projectState;
    }

    public void setProjectState(ProjectState projectState) {
        this.projectState = projectState;
    }

    public Set<ProjectUser> getProjectUser() {
        return projectUser;
    }

    public void setProjectUser(Set<ProjectUser> projectUser) {
        this.projectUser = projectUser;
    }

    public Set<Question> getQuestion() {
        return question;
    }

    public void setQuestion(Set<Question> question) {
        this.question = question;
    }

    public Set<Attachment> getAttachment() {
        return attachment;
    }

    public void setAttachment(Set<Attachment> attachment) {
        this.attachment = attachment;
    }

    public boolean getClinicalTrial() {
        return clinicalTrial;
    }

    public void setClinicalTrial(boolean clinicalTrial) {
        this.clinicalTrial = clinicalTrial;
    }

    public String getEudraCtNumber() {
        return eudraCtNumber;
    }

    public void setEudraCtNumber(String eudraCtNumber) {
        this.eudraCtNumber = eudraCtNumber;
    }

    public String getProjectFolderPath() {

        return Project.PROJECT_FOLDER_PATH + id;
    }

    public boolean getHasMta() {
        if (attachment == null) {
            return false;
        }
        for (Attachment attach : attachment) {
            if (attach.getAttachmentType().equals(AttachmentType.MATERIAL_TRANSFER_AGREEMENT)) {
                return true;
            }
        }
        return false;
    }

    public boolean getIsNew() {
        return projectState.equals(ProjectState.NEW);
    }

    public boolean getIsConfirmed() {
        return projectState.equals(ProjectState.CONFIRMED);
    }

    public boolean getIsDenied() {
        return projectState.equals(ProjectState.DENIED);
    }

    public boolean getIsCanceled() {
        return projectState.equals(ProjectState.CANCELED);
    }

    public boolean getIsFinished() {
        return projectState.equals(ProjectState.FINISHED);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", created=" + created +
                ", projectState=" + projectState +
                '}';
    }
}