package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.ProjectState;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Table
@Entity
public class Project implements Serializable {

    public final static String PROJECT_FOLDER = File.separator + "project_files";
    private final static String PROJECT_FOLDER_PATH = PROJECT_FOLDER + File.separator;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String annotation;

    private String fundingOrganization;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private Set<ProjectAdministrator> projectAdministrators = new HashSet<ProjectAdministrator>();

    @Enumerated(EnumType.STRING)
    private ProjectState projectState;

    @ManyToOne
    private User judgedByUser;

    @OneToMany(mappedBy = "project")
    private List<Attachment> attachments = new ArrayList<Attachment>();

    @OneToMany(mappedBy = "project")
    private List<SampleRequest> sampleRequests = new ArrayList<SampleRequest>();

    /*Which member of ethical committee approved project*/
    private String approvedBy;

    @Type(type = "timestamp")
    private Date approvalDate;

    private String principalInvestigator;

    private String homeInstitution;

    /*Where the approval is stored - in which institution*/
    private String approvalStorage;

    @Type(type = "timestamp")
    private Date created;

    public String getApprovalStorage() {
        return approvalStorage;
    }

    public void setApprovalStorage(String approvalStorage) {
        this.approvalStorage = approvalStorage;
    }

    public String getHomeInstitution() {
        return homeInstitution;
    }

    public void setHomeInstitution(String homeInstitution) {
        this.homeInstitution = homeInstitution;
    }

    public String getPrincipalInvestigator() {
        return principalInvestigator;
    }

    public void setPrincipalInvestigator(String principalInvestigator) {
        this.principalInvestigator = principalInvestigator;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getFundingOrganization() {
        return fundingOrganization;
    }

    public void setFundingOrganization(String fundingOrganization) {
        this.fundingOrganization = fundingOrganization;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public ProjectState getProjectState() {
        return projectState;
    }

    public void setProjectState(ProjectState projectState) {
        this.projectState = projectState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getJudgedByUser() {
        return judgedByUser;
    }

    public void setJudgedByUser(User judgedByUser) {
        this.judgedByUser = judgedByUser;
    }

    public List<SampleRequest> getSampleRequests() {
        return sampleRequests;
    }

    public void setSampleRequests(List<SampleRequest> sampleRequests) {
        this.sampleRequests = sampleRequests;
    }

    public Set<ProjectAdministrator> getProjectAdministrators() {
        return projectAdministrators;
    }

    public void setProjectAdministrators(Set<ProjectAdministrator> projectAdministrators) {
        this.projectAdministrators = projectAdministrators;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if (this.id == null || (!this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", annotation='" + annotation + '\'' +
                ", fundingOrganization='" + fundingOrganization + '\'' +
                ", projectState=" + projectState +
                '}';
    }

    public String getProjectFolderPath() {
           if (this.getId() == null) {
               return null;
           }

           return Project.PROJECT_FOLDER_PATH + this.getId().toString();
       }
}