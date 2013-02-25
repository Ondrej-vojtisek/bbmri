package bbmri.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "FUNDING_ORGANIZATION")
    private String fundingOrganization;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "project_users", joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<User>();

    @Enumerated(EnumType.STRING)
    private ProjectState projectState;

    @ManyToOne(cascade = CascadeType.ALL)
    private User judgedByUser;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<RequestGroup> requestGroups = new ArrayList<RequestGroup>();

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    Attachment agreement;

    public String getFundingOrganization() {
        return fundingOrganization;
    }

    public void setFundingOrganization(String fundingOrganization) {
        this.fundingOrganization = fundingOrganization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    public User getJudgedByUser() {
        return judgedByUser;
    }

    public void setJudgedByUser(User judgedByUser) {
        this.judgedByUser = judgedByUser;
    }

    public Attachment getAgreement() {
        return agreement;
    }

    public void setAgreement(Attachment agreement) {
        this.agreement = agreement;
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
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", fundingOrganization='" + fundingOrganization + '\'' +
                ", users=" + users +
                ", projectState=" + projectState +
                '}';
    }
}