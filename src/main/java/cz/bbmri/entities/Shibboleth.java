package cz.bbmri.entities;

import cz.bbmri.extension.context.TheActionBeanContext;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Entity
@org.hibernate.annotations.Proxy(lazy = false)
@Table(name = "shibboleth")
public class Shibboleth implements Serializable {

    private static final String AFFILIATION_EMPLOYEE = "employee@";


    @PrimaryKeyJoinColumn
    @OneToOne(targetEntity = cz.bbmri.entities.User.class, fetch = FetchType.LAZY)
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.LOCK})
    @JoinColumns({@JoinColumn(name = "user_id", nullable = false)})
    private User user;

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    @Id
    @GeneratedValue(generator = "CZ_BBMRI_ENTITY_SHIBBOLETH_USERID_GENERATOR")
    @org.hibernate.annotations.GenericGenerator(name = "CZ_BBMRI_ENTITY_SHIBBOLETH_USERID_GENERATOR", strategy = "foreign", parameters = @org.hibernate.annotations.Parameter(name = "property", value = "Id"))
    private long userId;

    public void setUserId(long value) {
        this.userId = value;
    }

    public long getUserId() {
        return userId;
    }

    @Column(name = "eppn", nullable = true)
    private String eppn;

    @Column(name = "affiliation", nullable = true)
    private String affiliation;

    @Column(name = "targetedId", nullable = true)
    private String targetedId;

    @Column(name = "persistentId", nullable = true)
    private String persistentId;

    @Column(name = "name", nullable = true)
    private String name;

    @Column(name = "surname", nullable = true)
    private String surname;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "organization", nullable = true)
    private String organization;

    @Column(name = "displayName", nullable = true)
    private String displayName;

    public void setEppn(String value) {
        this.eppn = value;
    }

    public String getEppn() {
        return eppn;
    }

    public void setAffiliation(String value) {
        this.affiliation = value;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setTargetedId(String value) {
        this.targetedId = value;
    }

    public String getTargetedId() {
        return targetedId;
    }

    public void setPersistentId(String value) {
        this.persistentId = value;
    }

    public String getPersistentId() {
        return persistentId;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String value) {
        this.surname = value;
    }

    public String getSurname() {
        return surname;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getEmail() {
        return email;
    }

    public void setOrganization(String value) {
        this.organization = value;
    }

    public String getOrganization() {
        return organization;
    }

    public void setDisplayName(String value) {
        this.displayName = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setUser(User value) {
        this.user = value;
    }

    public User getUser() {
        return user;
    }

    public boolean isAuthorized() {

        return affiliation != null && affiliation.contains(AFFILIATION_EMPLOYEE);
    }

    public static Shibboleth initiate(TheActionBeanContext context) {

        if(context == null){
            System.err.println("Given context was null");
            return null;
        }

        Shibboleth shibboleth = new Shibboleth();

        shibboleth.setDisplayName(context.getShibbolethDisplayName());
        shibboleth.setEmail(context.getShibbolethMail());
        shibboleth.setEppn(context.getShibbolethEppn());
        shibboleth.setTargetedId(context.getShibbolethTargetedId());
        shibboleth.setPersistentId(context.getShibbolethPersistentId());
        shibboleth.setOrganization(context.getShibbolethOrganization());
        shibboleth.setAffiliation(context.getShibbolethAffiliation());
        shibboleth.setName(context.getShibbolethGivenName());
        shibboleth.setSurname(context.getShibbolethSn());

        return shibboleth;
    }


    public static final String PROP_USER = "user";
    public static final String PROP_EPPN = "eppn";
    public static final String PROP_AFFILIATION = "affiliation";
    public static final String PROP_TARGETED_ID = "targetedId";
    public static final String PROP_PERSISTENT_ID = "persistentId";
    public static final String PROP_NAME = "name";
    public static final String PROP_SURNAME = "surname";
    public static final String PROP_EMAIL = "email";
    public static final String PROP_ORGANIZATION = "organization";
    public static final String PROP_DISPLAY_NAME = "displayName";
}
