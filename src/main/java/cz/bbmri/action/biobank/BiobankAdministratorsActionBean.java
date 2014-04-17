package cz.bbmri.action.biobank;

import cz.bbmri.action.DashboardActionBean;
import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.comparator.PermissionComparator;
import cz.bbmri.entities.comparator.PermissionUserComparator;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.BiobankAdministratorService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@UrlBinding("/biobank/administrators/{$event}/{biobankId}")
public class BiobankAdministratorsActionBean extends PermissionActionBean<BiobankAdministrator> {


    @SpringBean
    private BiobankAdministratorService biobankAdministratorService;

    public static Breadcrumb getBreadcrumb(boolean active, Long biobankId) {
        return new Breadcrumb(BiobankAdministratorsActionBean.class.getName(),
                "administratorsResolution", false, "cz.bbmri.action.biobank.BiobankActionBean.administrators",
                active, "biobankId", biobankId);
    }

    public BiobankAdministratorsActionBean() {

        setPagination(new MyPagedListHolder<BiobankAdministrator>(new ArrayList<BiobankAdministrator>()));
        // ribbon
        setComponentManager(new ComponentManager(
                ComponentManager.ROLE_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
        getPagination().setIdentifierParam("biobankId");
    }

    @Validate(required = true, on = {"addAdministrator", "setPermission"})
    private Permission permission;

    @Validate(required = true, on = {"addAdministrator", "removeAdministrator", "setPermission"})
    private Long adminId;

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("administratorsResolution")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution administratorsResolution() {

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(BiobankAdministratorsActionBean.getBreadcrumb(true, biobankId));

        if (biobankId != null) {
            getPagination().setIdentifier(biobankId);
        }

        initiatePagination();

        if (getOrderParam() == null) {
            // default sorter
            getPagination().setOrderParam("user.surname");
            getPagination().setComparator(new PermissionUserComparator());

        } else if (getOrderParam().equals("user.surname")) {
            // pagination orderParam was already set
            getPagination().setComparator(new PermissionUserComparator());

        } else if (getOrderParam().equals("permission")) {
            // pagination orderParam was already set
            getPagination().setComparator(new PermissionComparator());
        }
        getPagination().setEvent("administratorsResolution");
        // Administrators are stored in Set
        getPagination().setSource(new ArrayList<BiobankAdministrator>(getBiobank().getBiobankAdministrators()));

        return new ForwardResolution(BIOBANK_DETAIL_ADMINISTRATORS).addParameter("biobankId", biobankId);
    }

    @HandlesEvent("setPermission")
    @RolesAllowed({"biobank_operator if ${allowedBiobankManager}"})
    public Resolution setPermission() {

        if (!biobankAdministratorService.changeAdministratorPermission(adminId, permission,
                getContext().getValidationErrors(),
                getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "administratorsResolution")
                    .addParameter("biobankId", biobankId);
        }
        successMsg();
        return new RedirectResolution(this.getClass(), "administratorsResolution")
                .addParameter("biobankId", biobankId);


    }

    @HandlesEvent("removeAdministrator")
    @RolesAllowed({"biobank_operator if ${allowedBiobankManager or isMyAccount}"})
    public Resolution removeAdministrator() {

        boolean myAccount = getIsMyAccount();

        if (!biobankAdministratorService.removeAdministrator(adminId,
                getContext().getValidationErrors(),
                getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "administratorsResolution")
                    .addParameter("biobankId", biobankId);
        }

        successMsg();

        // I've just deleted myself - I don't have access to this biobank anymore
        if (getIsMyAccount()) {
            return new RedirectResolution(DashboardActionBean.class);
        }
        return new RedirectResolution(this.getClass());

    }

    public String getRemoveQuestion() {
        if (getIsMyAccount()) {
            return this.getName() + ".questionRemoveMyself";
        }
        return this.getName() + ".questionRemoveAdministrator";
    }

    @HandlesEvent("addAdministrator")
    @RolesAllowed({"biobank_operator if ${allowedBiobankManager}"})
    public Resolution addAdministrator() {

        if (!biobankAdministratorService.assignAdministrator(biobankId, adminId, permission,
                getContext().getValidationErrors(),
                getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "administratorsResolution")
                    .addParameter("biobankId", biobankId);
        }
        successMsg();
        return new RedirectResolution(this.getClass(), "administratorsResolution")
                .addParameter("biobankId", biobankId);

    }
}
