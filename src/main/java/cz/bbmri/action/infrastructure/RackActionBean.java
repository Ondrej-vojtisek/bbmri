package cz.bbmri.action.infrastructure;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.action.biobank.BiobankActionBean;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Rack;
import cz.bbmri.entities.infrastructure.RackBox;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.ContainerService;
import cz.bbmri.service.RackService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.IntegerTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.3.14
 * Time: 0:36
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/biobank/infrastructure/container/rack/{$event}/{rackId}")
public class RackActionBean extends PermissionActionBean<RackBox> {

    @SpringBean
    private RackService rackService;

    @SpringBean
    private ContainerService containerService;

    @ValidateNestedProperties(value = {
            @Validate(field = "name",
                    required = true, on = "createRack"),
            @Validate(converter = IntegerTypeConverter.class, field = "capacity",
                    required = true, on = "createRack")
    })
    private Rack rack;

    private Long rackId;

    private Long containerId;


    public static Breadcrumb getBreadcrumb(boolean active, Long rackId, String rackName) {
        return new Breadcrumb(RackActionBean.class.getName(),
                "detail", false, "cz.bbmri.entities.infrastructure.Rack.rack", active,
                "rackId", rackId, rackName);
    }

    public RackActionBean() {
        //default
        setPagination(new MyPagedListHolder<RackBox>(new ArrayList<RackBox>()));
        setComponentManager(new ComponentManager(
                ComponentManager.BOX_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
    }


    Rack getRack() {
        if (rack == null) {
            if (rackId != null) {
                rack = rackService.get(rackId);
            }
        }
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

    public Long getRackId() {
        return rackId;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    public Long getContainerId() {
        return containerId;
    }

    public void setContainerId(Long containerId) {
        this.containerId = containerId;
    }

    @HandlesEvent("detail")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution detail() {

        if (getRack() != null) {
            setBiobankId(getRack().getContainer().getInfrastructure().getBiobank().getId());
        }

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(InfrastructureActionBean.getBreadcrumb(false, biobankId));
        getBreadcrumbs().add(ContainerActionBean.getBreadcrumb(false, getRack().getContainer().getId(), getRack().getContainer().getName()));
        getBreadcrumbs().add(RackActionBean.getBreadcrumb(true, rackId, getRack().getName()));

        initiatePagination();
        getPagination().setEvent("detail");
        getPagination().setSource(new ArrayList<RackBox>(getRack().getRackBoxes()));


        return new ForwardResolution(INFRASTRUCTURE_DETAIL_RACK)
                .addParameter("rackId", rackId);
    }

    @DontValidate
    @HandlesEvent("createRackResolution")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createRackResolution() {
        // biobank ribbon
        Container container = containerService.get(containerId);
        if (container != null) {
            setBiobankId(container.getInfrastructure().getBiobank().getId());
        }
        return new ForwardResolution(INFRASTRUCTURE_CREATE_RACK)
                .addParameter("containerId", containerId);
    }

    @HandlesEvent("createRack")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createRack() {
        if (!rackService.create(containerId, rack, getContext().getValidationErrors())) {
            return new ForwardResolution(ContainerActionBean.class, "detail")
                    .addParameter("containerId", containerId);
        }
        successMsg();
        return new RedirectResolution(ContainerActionBean.class, "detail")
                .addParameter("containerId", containerId);
    }
}
