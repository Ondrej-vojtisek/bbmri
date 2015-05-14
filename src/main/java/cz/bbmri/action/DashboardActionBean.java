package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.BiobankDAO;
import cz.bbmri.dao.NotificationDAO;
import cz.bbmri.dao.SampleDAO;
import cz.bbmri.entity.Biobank;
import cz.bbmri.entity.Notification;
import cz.bbmri.entity.Sample;
import cz.bbmri.entity.temp.Car;
import cz.bbmri.entity.webEntities.Breadcrumb;
import cz.bbmri.entity.webEntities.MyPagedListHolder;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@PermitAll
@UrlBinding("/dashboard")
public class DashboardActionBean extends AuthorizationActionBean {

    @SpringBean
    private NotificationDAO notificationDAO;

//    @SpringBean
//    private BiobankDAO biobankDAO;
//
//    @SpringBean
//    private SampleDAO sampleDAO;


    private Notification notification;

    @Validate(on = {"markAsRead", "deleteSelected"}, required = true)
    private List<Long> selectedNotifications;

    private MyPagedListHolder<Notification> pagination = new MyPagedListHolder<Notification>(new ArrayList<Notification>());

    public DashboardActionBean() {
        getBreadcrumbs().add(new Breadcrumb(DashboardActionBean.class.getName(), "display", false, "home", true));
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

        pagination.initiate(getPage(), getOrderParam(), isDesc());
        pagination.setSource(notificationDAO.getUnread(getLoggedUser()));
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

        for (Long notificationId : selectedNotifications) {
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

        for (Long notificationId : selectedNotifications) {
            Notification notification = notificationDAO.get(notificationId);
            notification.setRead(true);
            notificationDAO.save(notification);
        }

        return new RedirectResolution(this.getClass());
    }

//    private Car car = new Car();
//    public String make;
//    private List<String> models;
//
//    private List<Sample> samples;
//
//    public Integer biobankId;
//
//    public Resolution view() {
//        return new ForwardResolution(View.Notification.DASHBOARD);
//    }
//
//    public Car getCar() {
//        return car;
//    }
//
//    public List<String> getModels() {
//        return models;
//    }
//
//    public List<Sample> getSamples() {
//        return samples;
//    }
//
//    public Resolution updateModels() {
//        models = car.get(make);
//
//        Biobank biobank = biobankDAO.get(biobankId);
//
//        if (biobankId != null) {
//            samples = sampleDAO.getAllByBiobank(biobank);
//        }
//
//        return new ForwardResolution(View.Ajax.PARTIAL);
//    }
//
//    public List<Biobank> getBiobanks() {
//        return biobankDAO.all();
//    }

}
