package cz.bbmri.action.sample;

import cz.bbmri.entities.SampleReservation;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import net.sourceforge.stripes.action.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.3.14
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/sample/reservations/{$event}/{sampleId}")
public class SampleReservationsActionBean extends AbstractSampleActionBean<SampleReservation> {

    public SampleReservationsActionBean() {
        setPagination(new MyPagedListHolder<SampleReservation>(new ArrayList<SampleReservation>()));
        setComponentManager(new ComponentManager(
                ComponentManager.SAMPLEQUESTION_DETAIL,
                ComponentManager.SAMPLE_DETAIL));
        getPagination().setIdentifierParam("sampleId");
    }

    @DontValidate
    @HandlesEvent("reservations") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution reservations() {

        if (getSampleId() != null) {
            getPagination().setIdentifier(getSampleId());
        }

        initiatePagination();
        if (getOrderParam() == null) {
            getPagination().setOrderParam("created");
        }
        getPagination().setEvent("reservations");
        getPagination().setSource(sampleFacade.getSampleReservationsBySample(
                getSampleId(),
                getPagination().getOrderParam(),
                getPagination().getDesc()));
        return new ForwardResolution(SAMPLE_RESERVATIONS);
    }
}
