package bbmri.entities;

import bbmri.entities.enumeration.ProjectState;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 10.10.12
 * Time: 21:18
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "Project")
@Entity
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    private String name;

    @Column(columnDefinition="TEXT")
    private String annotation;

    private String fundingOrganization;

    @OneToMany(mappedBy = "project")
    private List<ProjectAdministrator> projectAdministrators = new ArrayList<ProjectAdministrator>();

    @Enumerated(EnumType.STRING)
    private ProjectState projectState;

    @ManyToOne
    private User judgedByUser;

    @OneToMany(mappedBy = "project")
    private List<RequestGroup> requestGroups = new ArrayList<RequestGroup>();

    @OneToMany(mappedBy = "project")
    private List<Attachment> attachments = new ArrayList<Attachment>();

    @OneToMany(mappedBy = "project")
    private List<SampleQuestion> sampleQuestions = new ArrayList<SampleQuestion>();

    /*Which member of ethical committee approved project*/
    private String approvedBy;

    private Date approvalDate;

    private String mainInvestigator;

    private String homeInstitution;

    /*Where the approval is stored - in which institution*/
    private String approvalStorage;

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

    public String getMainInvestigator() {
        return mainInvestigator;
    }

    public void setMainInvestigator(String mainInvestigator) {
        this.mainInvestigator = mainInvestigator;
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

    public List<SampleQuestion> getSampleQuestions() {
        return sampleQuestions;
    }

    public void setSampleQuestions(List<SampleQuestion> sampleQuestions) {
        this.sampleQuestions = sampleQuestions;
    }

    public List<ProjectAdministrator> getProjectAdministrators() {
        return projectAdministrators;
    }

    public void setProjectAdministrators(List<ProjectAdministrator> projectAdministrators) {
        this.projectAdministrators = projectAdministrators;
    }

    public List<RequestGroup> getRequestGroups() {
        return requestGroups;
    }

    public void setRequestGroups(List<RequestGroup> requestGroups) {
        this.requestGroups = requestGroups;
    }
    @Fetch(value= FetchMode.SELECT)
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
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
        if (this.id == null || (this.id != null && !this.id.equals(other.id))) {
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
}