package cz.bbmri.action.sample;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.facade.SampleFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.3.14
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/sample/projects/{$event}/{sampleId}")
public class SampleProjectsActionBean extends AbstractSampleActionBean<Project> {

    public SampleProjectsActionBean() {
        setPagination(new MyPagedListHolder<Project>(new ArrayList<Project>()));
        setComponentManager(new ComponentManager(
                ComponentManager.PROJECT_DETAIL,
                ComponentManager.SAMPLE_DETAIL));
        getPagination().setIdentifierParam("sampleId");
    }


    @DontValidate
    @HandlesEvent("projects") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution projects() {
        if (getSampleId() != null) {
            getPagination().setIdentifier(getSampleId());
        }

        initiatePagination();
        if (getOrderParam() == null) {
            getPagination().setOrderParam("name");
        }
        getPagination().setEvent("projects");
        getPagination().setSource(sampleFacade.getProjectsBySample(getSampleId(),
                getPagination().getOrderParam(),
                getPagination().getDesc()));

        return new ForwardResolution(SAMPLE_PROJECTS);
    }

}
