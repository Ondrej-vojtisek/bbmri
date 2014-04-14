package cz.bbmri.action.sample;

import cz.bbmri.action.biobank.BiobankActionBean;
import cz.bbmri.action.biobank.BiobankSamplesActionBean;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.ProjectService;
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

    @SpringBean
    private ProjectService projectService;

    public SampleProjectsActionBean() {
        setPagination(new MyPagedListHolder<Project>(new ArrayList<Project>()));
        setComponentManager(new ComponentManager(
                ComponentManager.PROJECT_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
        getPagination().setIdentifierParam("sampleId");
    }

    public static Breadcrumb getBreadcrumb(boolean active, Long sampleId) {
        return new Breadcrumb(SampleProjectsActionBean.class.getName(),
                "projects", false, "cz.bbmri.entities.Project.projects",
                active, "sampleId", sampleId);
    }


    @DontValidate
    @HandlesEvent("projects") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution projects() {

        setBiobankId(getSample().getModule().getPatient().getBiobank().getId());

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(BiobankSamplesActionBean.getBreadcrumb(false, biobankId));
        getBreadcrumbs().add(SampleActionBean.getBreadcrumb(false, getSampleId()));
        getBreadcrumbs().add(SampleProjectsActionBean.getBreadcrumb(true, getSampleId()));

        getPagination().setIdentifier(getSampleId());

        initiatePagination();
        if (getOrderParam() == null) {
            getPagination().setOrderParam("name");
        }
        getPagination().setEvent("projects");
        getPagination().setSource(projectService.getProjectsBySample(getSampleId(),
                getPagination().getOrderParam(),
                getPagination().getDesc()));

        return new ForwardResolution(SAMPLE_PROJECTS);
    }

}
