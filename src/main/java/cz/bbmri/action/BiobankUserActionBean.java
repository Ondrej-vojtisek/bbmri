package cz.bbmri.action;

import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.BiobankDAO;
import cz.bbmri.dao.BiobankUserDAO;
import cz.bbmri.dao.PermissionDAO;
import cz.bbmri.dao.UserDAO;
import cz.bbmri.entity.Biobank;
import cz.bbmri.entity.BiobankUser;
import cz.bbmri.entity.Permission;
import cz.bbmri.entity.User;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/biobankuser/{$event}/{id}")
public class BiobankUserActionBean extends ComponentActionBean {

    @SpringBean
    private BiobankUserDAO biobankUserDAO;

    @SpringBean
    private BiobankDAO biobankDAO;

    @SpringBean
    private UserDAO userDAO;

    @SpringBean
    private PermissionDAO permissionDAO;

    private Long userId;

    private Integer biobankId;

    private Integer permissionId;

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

        if (user == null) {
            return new ForwardResolution(View.BiobankUser.NOTFOUND);
        }

        biobankUserDAO.remove(biobankUser);

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

        if (user == null) {
            return new ForwardResolution(View.BiobankUser.NOTFOUND);
        }

        Permission permission = permissionDAO.get(permissionId);
        if (user == null) {
            return new ForwardResolution(View.Permission.NOTFOUND);
        }

        biobankUser.setPermission(permission);

        biobankUserDAO.save(biobankUser);

        return new RedirectResolution(BiobankActionBean.class, "biobankuser").addParameter("id", biobank.getId());
    }

    public String getRemoveQuestion() {
        if (getIsMyAccount()) {
            return this.getName() + ".questionRemoveMyself";
        }
        return this.getName() + ".questionRemoveBiobankUser";
    }

    @HandlesEvent("add")
    @RolesAllowed({"biobank_operator if ${biobankManager}", "developer"})
    public Resolution add() {

        return new ForwardResolution(View.BiobankUser.ADD);
    }

}
