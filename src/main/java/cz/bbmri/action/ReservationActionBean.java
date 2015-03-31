package cz.bbmri.action;

import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.ReservationDAO;
import cz.bbmri.entity.Reservation;
import cz.bbmri.entity.webEntities.Breadcrumb;
import cz.bbmri.entity.webEntities.MyPagedListHolder;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/reservation/{$event}/{id}")
public class ReservationActionBean extends ComponentActionBean {

    @SpringBean
    private ReservationDAO reservationDAO;

    private Long id;

    private Reservation reservation;

    private MyPagedListHolder<Reservation> pagination;

    public static Breadcrumb getAllBreadcrumb(boolean active) {
        return new Breadcrumb(ReservationActionBean.class.getName(), "all", false, "" +
                "cz.bbmri.entity.Reservation.reservations", active);
    }

    public static Breadcrumb getMyBreadcrumb(boolean active) {
           return new Breadcrumb(ReservationActionBean.class.getName(), "myReservations", false, "cz.bbmri.entity.Reservation.myReservations",
                   active);
       }

    public static Breadcrumb getDetailBreadcrumb(boolean active, Reservation reservation) {
        return new Breadcrumb(ReservationActionBean.class.getName(), "detail", false, "cz.bbmri.entity.Reservation.allReservation" ,
                active, "id", reservation.getId());
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Reservation getReservation() {
        if (reservation == null) {
            if (id != null) {
                reservation = reservationDAO.get(id);
            }
        }

        return reservation;
    }

    public MyPagedListHolder<Reservation> getPagination() {
        return pagination;
    }

    public void setPagination(MyPagedListHolder<Reservation> pagination) {
        this.pagination = pagination;
    }

    @DefaultHandler
    @HandlesEvent("all")
    @RolesAllowed({"developer"})
    public Resolution all() {

        getBreadcrumbs().add(ReservationActionBean.getAllBreadcrumb(true));

        pagination = new MyPagedListHolder<Reservation>(new ArrayList<Reservation>(reservationDAO.all()));
        pagination.initiate(getPage(), getOrderParam(), isDesc());
        pagination.setEvent("all");

        return new ForwardResolution(View.Reservation.ALL);

    }


    @HandlesEvent("myReservations")
    @RolesAllowed({"developer"})
    public Resolution myReservations() {

        getBreadcrumbs().add(ReservationActionBean.getMyBreadcrumb(true));

        pagination = new MyPagedListHolder<Reservation>(new ArrayList<Reservation>(getLoggedUser().getReservation()));
        pagination.initiate(getPage(), getOrderParam(), isDesc());
        pagination.setEvent("myReservations");

        return new ForwardResolution(View.Reservation.ALL);

    }

    @HandlesEvent("detail")
    @RolesAllowed({"developer", "admin"})
    public Resolution detail() {

        getReservation();

        if (reservation == null) {
            return new ForwardResolution(View.Reservation.NOTFOUND);
        }

        getBreadcrumbs().add(ReservationActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(ReservationActionBean.getDetailBreadcrumb(true, reservation));

        return new ForwardResolution(View.Reservation.DETAIL);
    }

}

