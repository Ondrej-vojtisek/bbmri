package cz.bbmri.action.reservation;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.SampleReservation;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.ProjectService;
import cz.bbmri.service.SampleReservationService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@UrlBinding("/reservation/{$event}/{reservationId}")
public class ReservationActionBean extends PermissionActionBean<SampleReservation> {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private ProjectService projectService;

    @SpringBean
    private SampleReservationService sampleReservationService;

    public ReservationActionBean() {
        setPagination(new MyPagedListHolder<SampleReservation>(new ArrayList<SampleReservation>()));
        setComponentManager(new ComponentManager(
                ComponentManager.SAMPLEQUESTION_DETAIL,
                ComponentManager.SAMPLEQUESTION_DETAIL));
    }

    public static Breadcrumb getBreadcrumb(boolean active) {
        return new Breadcrumb(ReservationActionBean.class.getName(),
                "all", false, "cz.bbmri.entities.Reservation.reservations", active);
    }


    @DontValidate
    @DefaultHandler
    @HandlesEvent("all")
    @RolesAllowed("user")
    public Resolution all() {

        getBreadcrumbs().add(ReservationActionBean.getBreadcrumb(true));

        initiatePagination();
        if (getOrderParam() == null) {
            // default
            setOrderParam("created");
            getPagination().setOrderParam("created");
        }
        getPagination().setEvent("all");
        getPagination().setSource(sampleReservationService.getSortedSampleReservations(
                getContext().getMyId(),
                getPagination().getOrderParam(),
                getPagination().getDesc()));
        return new ForwardResolution(RESERVATION_ALL);
    }


}
