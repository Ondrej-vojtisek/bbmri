package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entities.Notification;
import cz.bbmri.facade.UserFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 8.10.13
 * Time: 22:14
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@HttpCache(allow = false)
@UrlBinding("/dashboard")
public class DashboardActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private UserFacade userFacade;


    private Notification notification;

    @Validate(on = {"markAsRead", "deleteSelected"}, required = true)
    private List<Long> selectedNotifications;

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

    @PermitAll
    @DontValidate
    @DefaultHandler
    public Resolution display() {
        logger.debug("administrators: " + userFacade.getAdministrators());

        return new ForwardResolution(DASHBOARD);


    }

    public List<Notification> getNotifications() {
        return userFacade.getUnreadNotifications(getContext().getMyId());
    }

    @DontValidate
    @HandlesEvent("markAsRead")
    public Resolution markAsRead() {


        return new RedirectResolution(this.getClass());
    }



    @DontValidate
    @HandlesEvent("deleteSelected")
    public Resolution deleteSelected() {
        if (selectedNotifications == null || selectedNotifications.isEmpty()) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.DashboardActionBean.nothingSelected"));
        }

        if (!userFacade.deleteNotifications(selectedNotifications)) {
            return new ForwardResolution(this.getClass());
        }

        successMsg(null);
        return new RedirectResolution(this.getClass());
    }

    @DontValidate
    @HandlesEvent("markSelectedAsRead")
    public Resolution markSelectedAsRead() {
        if (selectedNotifications == null || selectedNotifications.isEmpty()) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.DashboardActionBean.nothingSelected"));
        }

        if (!userFacade.markAsRead(selectedNotifications)) {
            return new ForwardResolution(this.getClass());
        }

        successMsg(null);
        return new RedirectResolution(this.getClass());
    }


}
