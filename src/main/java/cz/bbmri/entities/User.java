package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.entities.systemAdministration.UserSetting;
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

@Entity
@Table(name = "Users")
public class User implements Serializable /*, Comparable<User>*/ {

    private static final String AFFILIATION_EMPLOYEE = "employee@";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    /* eppn in Shibboleth*/
    private String eppn;

    /* targeted-id in Shibboleth*/
    private String targetedId;

    /* persistent-id in Shibboleth*/
    private String persistentId;

    /* GivenName in Shibboleth*/
    private String name;

    /* sn in Shibboleth */
    private String surname;

    /* mail in Shibboleth*/
    private String email;

    /* o in Shibboleth*/
    private String organization;

    /* displayName in Shibboleth*/
    private String displayName;

    /* mefaPerson in Shibboleth*/
    private boolean mefaPerson;

    /* affiliation in Shibboleth*/
    private String affiliation;

    //naive temporal prosthesis
    private String password;

    @OneToOne(mappedBy = "user")
    private UserSetting userSetting;

    @OneToMany(mappedBy = "user")
    private Set<ProjectAdministrator> projectAdministrators = new HashSet<ProjectAdministrator>();

    @OneToMany(mappedBy = "user")
    private Set<BiobankAdministrator> biobankAdministrators = new HashSet<BiobankAdministrator>();

    @OneToMany(mappedBy = "judgedByUser")
    private List<Project> judgedProjects = new ArrayList<Project>();

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications = new ArrayList<Notification>();

    @OneToMany(mappedBy = "user")
    private List<SampleReservation> sampleReservations = new ArrayList<SampleReservation>();

    @ElementCollection(targetClass = SystemRole.class, fetch = FetchType.EAGER)
    @JoinTable(name = "roleType", joinColumns = @JoinColumn(name = "userID"))
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<SystemRole> systemRoles = new HashSet<SystemRole>();

    @Type(type = "timestamp")
    private Date created;

    @Type(type = "timestamp")
    private Date lastLogin;

    private boolean shibbolethUser;

    public User() {
    }

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
        if (displayName != null)
            return displayName;

        // Fallback for non-shibboleth access

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

    public String getEppn() {
        return eppn;
    }

    public void setEppn(String eppn) {
        this.eppn = eppn;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isMefaPerson() {
        return mefaPerson;
    }

    public void setMefaPerson(boolean mefaPerson) {
        this.mefaPerson = mefaPerson;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public boolean isShibbolethUser() {
        return shibbolethUser;
    }

    public void setShibbolethUser(boolean shibbolethUser) {
        this.shibbolethUser = shibbolethUser;
    }

    public String getTargetedId() {
        return targetedId;
    }

    public void setTargetedId(String targetedId) {
        this.targetedId = targetedId;
    }

    public String getPersistentId() {
        return persistentId;
    }

    public void setPersistentId(String persistentId) {
        this.persistentId = persistentId;
    }

    public List<SampleReservation> getSampleReservations() {
        return sampleReservations;
    }

    public void setSampleReservations(List<SampleReservation> sampleReservations) {
        this.sampleReservations = sampleReservations;
    }

    /* Only employee is taken into account now */
    public boolean isEmployee() {
        if (affiliation == null) return false;
        return affiliation.contains(AFFILIATION_EMPLOYEE);
    }

    public UserSetting getUserSetting() {
        return userSetting;
    }

    public void setUserSetting(UserSetting userSetting) {
        this.userSetting = userSetting;
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
        return "User{" +
                "id=" + id +
                ", eppn='" + eppn + '\'' +
                ", targetedId='" + targetedId + '\'' +
                ", persistentId='" + persistentId + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", organization='" + organization + '\'' +
                ", displayName='" + displayName + '\'' +
                ", mefaPerson=" + mefaPerson +
                ", affiliation='" + affiliation + '\'' +
                '}';
    }

//    public int compareTo(User compareUser) {
//
//        if (this.getId() > compareUser.getId())
//            return 1;
//        else if (this.getId() < compareUser.getId())
//            return -1;
//        else
//            return 0;
//    }


//    public static Comparator<User> UserIdComparator
//            = new Comparator<User>() {
//
//        public int compare(User user1, User user2) {
//
//            Long userAtr1 = user1.getId();
//            Long userAtr2 = user2.getId();
//
//            //ascending order
//            return userAtr1.compareTo(userAtr2);
//
//        }
//
//    };
//
//    public static Comparator<User> UserSurnameComparator
//            = new Comparator<User>() {
//
//        public int compare(User user1, User user2) {
//
//            String userAtr1 = user1.getSurname();
//            String userAtr2 = user2.getSurname();
//
//            //ascending order
//            return userAtr1.compareTo(userAtr2);
//        }
//
//    };
//
//    public static Comparator<User> UserOrganizationComparator
//            = new Comparator<User>() {
//
//        public int compare(User user1, User user2) {
//
//            String userAtr1 = user1.getOrganization();
//            String userAtr2 = user2.getOrganization();
//
//            //ascending order
//            return userAtr1.compareTo(userAtr2);
//        }
//
//    };

}




