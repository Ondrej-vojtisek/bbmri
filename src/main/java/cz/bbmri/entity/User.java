package cz.bbmri.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Anyone with access to system
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class User implements Serializable {

    public static final String PROP_ID = "id";
    public static final String PROP_PASSWORD = "password";
    public static final String PROP_CREATED = "created";
    public static final String PROP_LAST_LOGIN = "lastLogin";
    public static final String PROP_ROLE = "role";
    public static final String PROP_CONTACT = "contact";
    public static final String PROP_SHIBBOLETH = "shibboleth";
    public static final String PROP_SETTINGS = "settings";
    public static final String PROP_RESERVATION = "reservation";
    public static final String PROP_BIOBANK_USER = "biobankUser";
    public static final String PROP_PROJECT_USER = "projectUser";
    public static final String PROP_NOTIFICATION = "notification";

    private long id;
    private String password;
    private Date created = new Date();
    private Date lastLogin;
    private Set<Role> role = new HashSet<Role>();
    private Contact contact;
    private Shibboleth shibboleth;
    private Settings settings;
    private Set<Reservation> reservation = new HashSet<Reservation>();
    private Set<BiobankUser> biobankUser = new HashSet<BiobankUser>();
    private Set<ProjectUser> projectUser = new HashSet<ProjectUser>();
    private Set<Notification> notification = new HashSet<Notification>();

    public boolean isAdministrator() {
        return role.contains(Role.ADMIN);
    }

    public boolean isDeveloper() {
        return role.contains(Role.DEVELOPER);
    }

    public boolean isProjectTeamMember() {
        return role.contains(Role.PROJECT_TEAM_MEMBER);
    }

    public boolean isBiobankOperator() {
        return role.contains(Role.BIOBANK_OPERATOR);
    }

    public boolean isProjectTeamMemberConfirmed() {
        return role.contains(Role.PROJECT_TEAM_MEMBER_CONFIRMED);
    }

    public void nominateProjectTeamMember() {
        if (isProjectTeamMember()) {
            // nothing to be done here
            return;
        }

        role.add(Role.PROJECT_TEAM_MEMBER);
    }

    public void nominateProjectTeamMemberConfirmed() {
        if (isProjectTeamMemberConfirmed()) {
            // nothing to be done here
            return;
        }

        role.add(Role.PROJECT_TEAM_MEMBER_CONFIRMED);
    }

    public void nominateBiobankOperator() {
        if (isBiobankOperator()) {
            // nothing to be done here
            return;
        }

        role.add(Role.BIOBANK_OPERATOR);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Shibboleth getShibboleth() {
        return shibboleth;
    }

    public void setShibboleth(Shibboleth shibboleth) {
        this.shibboleth = shibboleth;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Set<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(Set<Reservation> reservation) {
        this.reservation = reservation;
    }

    public Set<ProjectUser> getProjectUser() {
        return projectUser;
    }

    public void setProjectUser(Set<ProjectUser> projectUser) {
        this.projectUser = projectUser;
    }

    public Set<BiobankUser> getBiobankUser() {
        return biobankUser;
    }

    public void setBiobankUser(Set<BiobankUser> biobankUser) {
        this.biobankUser = biobankUser;
    }

    public Set<Notification> getNotification() {
        return notification;
    }

    public void setNotification(Set<Notification> notification) {
        this.notification = notification;
    }

    public String getWholeName() {
        if (shibboleth != null) {
            if (shibboleth.getDisplayName() != null) {
                return shibboleth.getDisplayName();
            } else {
                // Fallback
                return shibboleth.getName() + " " + shibboleth.getSurname();
            }
        }

        return "empty name";
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", created=" + created +
                ", lastLogin=" + lastLogin +
                ", contact=" + contact +
                ", shibboleth=" + shibboleth +
                '}';
    }
}




