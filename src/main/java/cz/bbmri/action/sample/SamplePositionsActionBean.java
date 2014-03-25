package cz.bbmri.action.sample;

import cz.bbmri.action.biobank.BiobankActionBean;
import cz.bbmri.action.biobank.BiobankSamplesActionBean;
import cz.bbmri.entities.comparator.position.MatrixComparator;
import cz.bbmri.entities.comparator.position.SampleComparator;
import cz.bbmri.entities.comparator.position.SequentialComparator;
import cz.bbmri.entities.infrastructure.Position;
import cz.bbmri.entities.webEntities.Breadcrumb;
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
@UrlBinding("/sample/positions/{$event}/{sampleId}")
public class SamplePositionsActionBean extends AbstractSampleActionBean<Position> {

    public SamplePositionsActionBean() {
        setPagination(new MyPagedListHolder<Position>(new ArrayList<Position>()));
        setComponentManager(new ComponentManager(
                ComponentManager.POSITION_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
        getPagination().setIdentifierParam("sampleId");
    }

    public static Breadcrumb getBreadcrumb(boolean active, Long sampleId) {
        return new Breadcrumb(SamplePositionsActionBean.class.getName(),
                "positions", false, "cz.bbmri.entities.infrastructure.Position.positions",
                active, "sampleId", sampleId);
    }


    public void choosePositionComparator() {
        if (getOrderParam() != null) {
            if (getOrderParam().equals("row")) {
                getPagination().setComparator(new MatrixComparator());
                return;
            }
            if (getOrderParam().equals("sequentialPosition")) {
                getPagination().setComparator(new SequentialComparator());
                return;
            }
        }
        getPagination().setOrderParam("sample.sampleIdentification.sampleId");
        getPagination().setComparator(new SampleComparator());
    }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("positions") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution positions() {

        setBiobankId(getSample().getModule().getPatient().getBiobank().getId());

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(BiobankSamplesActionBean.getBreadcrumb(false, biobankId));
        getBreadcrumbs().add(SampleActionBean.getBreadcrumb(false, getSampleId()));
        getBreadcrumbs().add(SamplePositionsActionBean.getBreadcrumb(true, getSampleId()));

        getPagination().setIdentifier(getSampleId());

        initiatePagination();
        if (getOrderParam() == null) {
            getPagination().setOrderParam("institutionId");
        }
        getPagination().setEvent("positions");
        choosePositionComparator();

        getPagination().setSource(new ArrayList<Position>(getSample().getPositions())); //getSample().getPositions()


        return new ForwardResolution(SAMPLE_POSITIONS);
    }
}
