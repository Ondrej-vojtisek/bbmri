package cz.bbmri.action;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Notification;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.NotificationService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@PermitAll
@UrlBinding("/dashboard")
public class DashboardActionBean extends PermissionActionBean<Notification> {

    @SpringBean
    private NotificationService notificationService;

    private Notification notification;

    @Validate(on = {"markAsRead", "deleteSelected"}, required = true)
    private List<Long> selectedNotifications;

    public DashboardActionBean() {
        getBreadcrumbs().add(new Breadcrumb(DashboardActionBean.class.getName(), "display", false, "home", true));

        setPagination(new MyPagedListHolder<Notification>(new ArrayList<Notification>()));
        setComponentManager(new ComponentManager(
                ComponentManager.NOTIFICATION_DETAIL,
                ComponentManager.NOTIFICATION_DETAIL));
    }


    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public List<Long> getSelectedNotifications() {
        return selectedNotifications;
    }

    public void setSelectedNotifications(List<Long> selectedNotifications) {
        this.selectedNotifications = selectedNotifications;
    }

    @DontValidate
    @DefaultHandler
    public Resolution display() {
        initiatePagination();
        getPagination().setSource(notificationService.getUnread(getContext().getMyId()));
        return new ForwardResolution(DASHBOARD);
    }

    @DontValidate
    @HandlesEvent("deleteSelected")
    public Resolution deleteSelected() {

        if (selectedNotifications == null || selectedNotifications.isEmpty()) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.DashboardActionBean.nothingSelected"));
            return new RedirectResolution(this.getClass());
        }

        if (notificationService.deleteNotifications(selectedNotifications)) {
            successMsg();
        }

        return new RedirectResolution(this.getClass());
    }

    @DontValidate
    @HandlesEvent("markSelectedAsRead")
    public Resolution markSelectedAsRead() {

        if (selectedNotifications == null || selectedNotifications.isEmpty()) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.DashboardActionBean.nothingSelected"));
            return new RedirectResolution(this.getClass());
        }

        if (notificationService.markAsRead(selectedNotifications, getContext().getValidationErrors())) {
            successMsg();
        }

        return new RedirectResolution(this.getClass());
    }


}
