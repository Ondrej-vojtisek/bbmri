package bbmri.entities;

import bbmri.entities.enumeration.SystemRole;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 10.10.12
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */

@Table(name = "Users")
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "EMAIL")
    private String email;

    //naive temporal prosthesis
    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<ProjectAdministrator> projectAdministrators = new ArrayList<ProjectAdministrator>();

    @OneToMany(mappedBy = "user")
   // private List<BiobankAdministrator> biobankAdministrators = new ArrayList<BiobankAdministrator>();
    private Set<BiobankAdministrator> biobankAdministrators = new HashSet<BiobankAdministrator>();

    @OneToMany(mappedBy = "judgedByUser")
    private List<Project> judgedProjects = new ArrayList<Project>();

    @ElementCollection(targetClass = SystemRole.class, fetch = FetchType.EAGER)
    @JoinTable(name = "roleType", joinColumns = @JoinColumn(name = "userID"))
    @Column(name = "Role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<SystemRole> systemRoles = new HashSet<SystemRole>();

    @Type(type = "timestamp")
    private Date created;

    @Type(type = "timestamp")
    private Date lastLogin;

    public User() {}

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public boolean getIsOperator() {
        return systemRoles.contains(SystemRole.BIOBANK_OPERATOR);
    }

    public boolean getIsAdministrator() {
         return systemRoles.contains(SystemRole.ADMINISTRATOR);
    }

    public List<Project> getJudgedProjects() {
        return judgedProjects;
    }

    public void setJudgedProjects(List<Project> judgedProjects) {
        this.judgedProjects = judgedProjects;
    }

    public String getWholeName() {
        return name + " " + surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<BiobankAdministrator> getBiobankAdministrators() {
        return biobankAdministrators;
    }

    public void setBiobankAdministrators(Set<BiobankAdministrator> biobankAdministrators) {
        this.biobankAdministrators = biobankAdministrators;
    }

    public List<ProjectAdministrator> getProjectAdministrators() {
        return projectAdministrators;
    }

    public void setProjectAdministrators(List<ProjectAdministrator> projectAdministrators) {
        this.projectAdministrators = projectAdministrators;
    }

    public Set<SystemRole> getSystemRoles() {
        return systemRoles;
    }

    public void setSystemRoles(Set<SystemRole> systemRoles) {
        this.systemRoles = systemRoles;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if (this.id == null || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + id + " " + name + " " + surname;
    }


}


