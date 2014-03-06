package cz.bbmri.action.reservation;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.SampleReservation;
import cz.bbmri.facade.ProjectFacade;
import cz.bbmri.facade.RequestFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 26.2.14
 * Time: 16:05
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/reservation/{$event}/{reservationId}")
public class ReservationActionBean extends PermissionActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private ProjectFacade projectFacade;

    @SpringBean
    private RequestFacade requestFacade;


    @DontValidate
    @DefaultHandler
    @HandlesEvent("all")
    @RolesAllowed("user")
    public Resolution all() {

        return new ForwardResolution(RESERVATION_ALL);
    }



}
