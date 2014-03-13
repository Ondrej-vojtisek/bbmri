package cz.bbmri.action.sample;

import cz.bbmri.entities.comparator.position.MatrixComparator;
import cz.bbmri.entities.comparator.position.SampleComparator;
import cz.bbmri.entities.comparator.position.SequentialComparator;
import cz.bbmri.entities.infrastructure.Position;
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
                ComponentManager.SAMPLE_DETAIL));
        getPagination().setIdentifierParam("sampleId");
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
        if (getSampleId() != null) {
            getPagination().setIdentifier(getSampleId());
        }

        initiatePagination();
        if (getOrderParam() == null) {
            getPagination().setOrderParam("institutionId");
        }
        getPagination().setEvent("positions");
        getPagination().setSource(new ArrayList<Position>(getSample().getPositions()));
        return new ForwardResolution(SAMPLE_POSITIONS);
    }
}
