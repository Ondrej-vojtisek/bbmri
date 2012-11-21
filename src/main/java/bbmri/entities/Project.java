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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String fundingOrganization;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "project_researchers", joinColumns = @JoinColumn(name = "projects_id"),
            inverseJoinColumns = @JoinColumn(name = "researchers_id"))
    private List<Researcher> researchers = new ArrayList<Researcher>();

    @Enumerated(EnumType.STRING)
    private ProjectState projectState;

    @OneToMany(mappedBy="project",cascade=CascadeType.ALL)
    private List<Request> requests = new ArrayList<Request>();

    public String getFundingOrganization() {return fundingOrganization;}
    public void setFundingOrganization(String fundingOrganization) {this.fundingOrganization = fundingOrganization;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public ProjectState getProjectState() {return projectState;}
    public void setProjectState(ProjectState projectState) {this.projectState = projectState;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public List<Researcher> getResearchers() {return researchers;}
    public void setResearchers(List<Researcher> researchers) {this.researchers = researchers;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public List<Request> getRequests() {return requests;}
    public void setRequests(List<Request> requests) {this.requests = requests;}

    public Researcher getOwner(){
        if(!researchers.isEmpty()){
            return researchers.get(0);
        }
        return null;
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
        return "id=" + id + " ,name: " + name + ", description: " + description + ", state: " + projectState;
    }
}