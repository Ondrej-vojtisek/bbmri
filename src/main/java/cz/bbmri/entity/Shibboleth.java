package cz.bbmri.entity;

import cz.bbmri.extension.context.TheActionBeanContext;

import java.io.Serializable;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class Shibboleth implements Serializable {

    private static final String AFFILIATION_EMPLOYEE = "employee@";

    public static final String PROP_USER = "user";
    public static final String PROP_EPPN = "eppn";
    public static final String PROP_AFFILIATION = "affiliation";
    public static final String PROP_TARGETED = "targeted";
    public static final String PROP_PERSISTENT = "persistent";
    public static final String PROP_NAME = "name";
    public static final String PROP_SURNAME = "surname";
    public static final String PROP_EMAIL = "email";
    public static final String PROP_ORGANIZATION = "organization";
    public static final String PROP_DISPLAY_NAME = "displayName";

    private User user;
    private long userId;
    private String eppn;
    private String affiliation;
    private String targeted;
    private String persistent;
    private String name;
    private String surname;
    private String email;
    private String organization;
    private String displayName;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEppn() {
        return eppn;
    }

    public void setEppn(String eppn) {
        this.eppn = eppn;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getTargeted() {
        return targeted;
    }

    public void setTargeted(String targeted) {
        this.targeted = targeted;
    }

    public String getPersistent() {
        return persistent;
    }

    public void setPersistent(String persistent) {
        this.persistent = persistent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        shibboleth.setTargeted(context.getShibbolethTargetedId());
        shibboleth.setPersistent(context.getShibbolethPersistentId());
        shibboleth.setOrganization(context.getShibbolethOrganization());
        shibboleth.setAffiliation(context.getShibbolethAffiliation());
        shibboleth.setName(context.getShibbolethGivenName());
        shibboleth.setSurname(context.getShibbolethSn());

        return shibboleth;
    }


}
