package cz.bbmri.action.sample;

import cz.bbmri.action.biobank.BiobankActionBean;
import cz.bbmri.action.biobank.BiobankSamplesActionBean;
import cz.bbmri.entities.SampleReservation;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.SampleReservationService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@UrlBinding("/sample/reservations/{$event}/{sampleId}")
public class SampleReservationsActionBean extends AbstractSampleActionBean<SampleReservation> {

    @SpringBean
    private SampleReservationService sampleReservationService;

    public SampleReservationsActionBean() {
        setPagination(new MyPagedListHolder<SampleReservation>(new ArrayList<SampleReservation>()));
        setComponentManager(new ComponentManager(
                ComponentManager.SAMPLEQUESTION_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
        getPagination().setIdentifierParam("sampleId");
    }

    private static Breadcrumb getBreadcrumb(boolean active, Long sampleId) {
        return new Breadcrumb(SampleReservationsActionBean.class.getName(),
                "reservations", false, "cz.bbmri.entities.SampleReservation.sampleReservations",
                active, "sampleId", sampleId);
    }

    @DontValidate
    @HandlesEvent("reservations") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution reservations() {

        setBiobankId(getSample().getModule().getPatient().getBiobank().getId());

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(BiobankSamplesActionBean.getBreadcrumb(false, biobankId));
        getBreadcrumbs().add(SampleActionBean.getBreadcrumb(false, getSampleId()));
        getBreadcrumbs().add(SampleReservationsActionBean.getBreadcrumb(true, getSampleId()));

        getPagination().setIdentifier(getSampleId());

        initiatePagination();
        if (getOrderParam() == null) {
            getPagination().setOrderParam("created");
        }
        getPagination().setEvent("reservations");
        getPagination().setSource(sampleReservationService.getSampleReservationsBySample(
                getSampleId(),
                getPagination().getOrderParam(),
                getPagination().getDesc()));
        return new ForwardResolution(SAMPLE_RESERVATIONS);
    }
}
