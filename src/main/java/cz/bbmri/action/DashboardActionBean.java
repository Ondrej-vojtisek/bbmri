package cz.bbmri.action;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.NotificationDAO;
import cz.bbmri.entity.Biobank;
import cz.bbmri.entity.Country;
import cz.bbmri.entity.Notification;
import cz.bbmri.entity.webEntities.Breadcrumb;
import cz.bbmri.entity.webEntities.ComponentManager;
import cz.bbmri.entity.webEntities.MyPagedListHolder;
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
public class DashboardActionBean extends PermissionActionBean {

    @SpringBean
    private NotificationDAO notificationDAO;

    private Notification notification;

    @Validate(on = {"markAsRead", "deleteSelected"}, required = true)
    private List<Long> selectedNotifications;

    private MyPagedListHolder<Notification> pagination;

    public DashboardActionBean() {
        getBreadcrumbs().add(new Breadcrumb(DashboardActionBean.class.getName(), "display", false, "home", true));

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


    public MyPagedListHolder<Notification> getPagination() {
        return pagination;
    }


    public void setPagination(MyPagedListHolder<Notification> pagination) {
        this.pagination = pagination;
    }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("display")
    public Resolution display() {

        pagination = new MyPagedListHolder<Notification>(notificationDAO.getUnread(getLoggedUser()));
        pagination.initiate(getPage(), getOrderParam(), isDesc());
        pagination.setEvent("display");

        return new ForwardResolution(View.Notification.DASHBOARD);
    }

    @DontValidate
    @HandlesEvent("deleteSelected")
    public Resolution deleteSelected() {

        if (selectedNotifications == null || selectedNotifications.isEmpty()) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.DashboardActionBean.nothingSelected"));
            return new RedirectResolution(this.getClass());
        }

        for(Long notificationId : selectedNotifications){
            Notification notification = notificationDAO.get(notificationId);
            notification.setRead(true);
            notificationDAO.remove(notification);
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

        for(Long notificationId : selectedNotifications){
            Notification notification = notificationDAO.get(notificationId);
            notification.setRead(true);
            notificationDAO.save(notification);
        }

        return new RedirectResolution(this.getClass());
    }


}
