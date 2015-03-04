package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.entities.systemAdministration.UserSetting;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Anyone with access to system
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Entity
@Table(name="users") // table _user_ is used by PostgreSQL by default
public class User implements Serializable /*, Comparable<User>*/ {

    private static final long serialVersionUID = 1L;

    /**
     * EduId defines few values of field affiliation. In BBMRI only users with employee@ are allowed to access
     */
    private static final String AFFILIATION_EMPLOYEE = "employee@";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    // Stored as SHA-256 hash
    private String password;

    @Type(type = "timestamp")
    private Date created;

    @Type(type = "timestamp")
    private Date lastLogin;


    @OneToOne
    @PrimaryKeyJoinColumn
    private UserSetting userSetting;

    @OneToMany(mappedBy = "user")
    private Set<ProjectAdministrator> projectAdministrators = new HashSet<ProjectAdministrator>();

    @OneToMany(mappedBy = "user")
    private Set<BiobankAdministrator> biobankAdministrators = new HashSet<BiobankAdministrator>();

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications = new ArrayList<Notification>();

    @OneToMany(mappedBy = "user")
    private List<SampleReservation> sampleReservations = new ArrayList<SampleReservation>();

    @ElementCollection(targetClass = SystemRole.class, fetch = FetchType.EAGER)
    @JoinTable(name = "roleType", joinColumns = @JoinColumn(name = "userID"))
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<SystemRole> systemRoles = new HashSet<SystemRole>();

    @OneToOne(mappedBy="user", targetEntity=cz.bbmri.entities.Shibboleth.class, fetch=FetchType.LAZY)
   	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})
   	private Shibboleth shibboleth;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getWholeName() {
        if(shibboleth != null){
            if(shibboleth.getDisplayName() != null){
                return shibboleth.getDisplayName();
            } else{
                 // Fallback
                return shibboleth.getName() + " " + shibboleth.getSurname();
            }
        }

        return "empty name";
    }

    public Set<BiobankAdministrator> getBiobankAdministrators() {
        return biobankAdministrators;
    }

    public void setBiobankAdministrators(Set<BiobankAdministrator> biobankAdministrators) {
        this.biobankAdministrators = biobankAdministrators;
    }

    public Set<ProjectAdministrator> getProjectAdministrators() {
        return projectAdministrators;
    }

    public void setProjectAdministrators(Set<ProjectAdministrator> projectAdministrators) {
        this.projectAdministrators = projectAdministrators;
    }

    public Set<SystemRole> getSystemRoles() {
        return systemRoles;
    }

    public void setSystemRoles(Set<SystemRole> systemRoles) {
        this.systemRoles = systemRoles;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
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

    public List<SampleReservation> getSampleReservations() {
        return sampleReservations;
    }

    public void setSampleReservations(List<SampleReservation> sampleReservations) {
        this.sampleReservations = sampleReservations;
    }

    public UserSetting getUserSetting() {
        return userSetting;
    }

    public void setUserSetting(UserSetting userSetting) {
        this.userSetting = userSetting;
    }

    public Shibboleth getShibboleth() {
        return shibboleth;
    }

    public void setShibboleth(Shibboleth shibboleth) {
        this.shibboleth = shibboleth;
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
        if (this.id == null || (!this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                '}';
    }
}




