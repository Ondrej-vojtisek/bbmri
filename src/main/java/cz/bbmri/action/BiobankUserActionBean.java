package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.*;
import cz.bbmri.entity.*;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;

import javax.annotation.security.RolesAllowed;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/biobankuser/{$event}/{biobankId}/{userId}")
public class BiobankUserActionBean extends AuthorizationActionBean {

    @SpringBean
    private BiobankUserDAO biobankUserDAO;

    @SpringBean
    private BiobankDAO biobankDAO;

    @SpringBean
    private UserDAO userDAO;

    @SpringBean
    private PermissionDAO permissionDAO;

    @SpringBean
    private NotificationDAO notificationDAO;

    @SpringBean
    private NotificationTypeDAO notificationTypeDAO;

    private Long userId;

    private Integer biobankId;

    private Integer permissionId;

    @Validate(on = {"confirmAdd"}, required = true)
    private String find;

    public boolean getIsMyAccount() {
        return getContext().getMyId().equals(userId);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getBiobankId() {
        return biobankId;
    }

    public void setBiobankId(Integer biobankId) {
        this.biobankId = biobankId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getFind() {
        return find;
    }

    public void setFind(String find) {
        this.find = find;
    }

    @HandlesEvent("remove")
    @RolesAllowed({"biobank_operator if ${biobankManager}", "developer"})
    public Resolution remove() {
        Biobank biobank = biobankDAO.get(biobankId);

        if (biobank == null) {
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        User user = userDAO.get(userId);

        if (user == null) {
            return new ForwardResolution(View.User.NOTFOUND);
        }

        BiobankUser biobankUser = biobankUserDAO.get(biobank, user);

        if (biobankUser == null) {
            return new ForwardResolution(View.BiobankUser.NOTFOUND);
        }

        biobankUserDAO.remove(biobankUser);

        user.denominateBiobankOperator();

        userDAO.save(user);

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.action.BiobankUserActionBean.adminDeleted",
                user.getWholeName(), biobank.getAcronym());

        notificationDAO.create(biobank.getOtherBiobankUser(getLoggedUser()),
                NotificationType.BIOBANK_ADMINISTRATOR, locMsg, new Long(biobank.getId()));


        return new RedirectResolution(BiobankActionBean.class, "biobankuser").addParameter("id", biobank.getId());
    }

    @HandlesEvent("setPermission")
    @RolesAllowed({"biobank_operator if ${biobankManager}", "developer"})
    public Resolution setPermission() {
        Biobank biobank = biobankDAO.get(biobankId);

        if (biobank == null) {
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        User user = userDAO.get(userId);

        if (user == null) {
            return new ForwardResolution(View.User.NOTFOUND);
        }

        BiobankUser biobankUser = biobankUserDAO.get(biobank, user);

        if (biobankUser == null) {
            return new ForwardResolution(View.BiobankUser.NOTFOUND);
        }

        Permission permission = permissionDAO.get(permissionId);
        if (permission == null) {
            return new ForwardResolution(View.Permission.NOTFOUND);
        }

        biobankUser.setPermission(permission);

        biobankUserDAO.save(biobankUser);

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.action.BiobankUserActionBean.permissionChanged",
                biobank.getAcronym(), user.getWholeName(), permission);

        notificationDAO.create(biobank.getOtherBiobankUser(getLoggedUser()),
                NotificationType.BIOBANK_ADMINISTRATOR, locMsg, new Long(biobank.getId()));


        return new RedirectResolution(BiobankActionBean.class, "biobankuser").addParameter("id", biobank.getId());
    }

    public String getRemoveQuestion() {
        if (getIsMyAccount()) {
            return this.getName() + ".questionRemoveMyself";
        }
        return this.getName() + ".questionRemoveBiobankUser";
    }

    @DefaultHandler
    @HandlesEvent("add")
    @RolesAllowed({"biobank_operator if ${biobankManager}", "developer"})
    public Resolution add() {

        Biobank biobank = biobankDAO.get(biobankId);

        if (biobank == null) {
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        return new ForwardResolution(View.BiobankUser.ADD);
    }


    @HandlesEvent("confirmAdd")
    @RolesAllowed({"biobank_operator if ${biobankManager}", "developer"})
    public Resolution confirmAdd() {
        Biobank biobank = biobankDAO.get(biobankId);

        if (biobank == null) {
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        User user = userDAO.find(find);
        if (user == null) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.BiobankUserActionBean.userNotFound"));
            return new ForwardResolution(BiobankUserActionBean.class, "add").addParameter("biobankId", biobankId);
        }
        userId = user.getId();

        if (biobankUserDAO.get(biobank, user) != null) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.BiobankUserActionBean.userIsAlreadyAssigned"));
            return new ForwardResolution(View.BiobankUser.ADD);
        }

        Permission permission = permissionDAO.get(permissionId);
        if (permission == null) {
            return new ForwardResolution(View.Permission.NOTFOUND);
        }

        BiobankUser biobankUser = new BiobankUser();
        biobankUser.setUser(user);
        biobankUser.setBiobank(biobank);
        biobankUser.setPermission(permission);

        biobankUserDAO.save(biobankUser);

        user.nominateBiobankOperator();

        userDAO.save(user);



        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.action.BiobankUserActionBean.adminAssigned",
                biobank.getAcronym(), user.getWholeName(), permission);

        notificationDAO.create(biobank.getOtherBiobankUser(getLoggedUser()),
                NotificationType.BIOBANK_ADMINISTRATOR, locMsg, new Long(biobank.getId()));

        return setPermission();
    }

}
