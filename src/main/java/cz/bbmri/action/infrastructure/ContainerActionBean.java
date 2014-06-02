package cz.bbmri.action.infrastructure;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.action.biobank.BiobankActionBean;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Infrastructure;
import cz.bbmri.entities.infrastructure.Rack;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.ContainerService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.FloatTypeConverter;
import net.sourceforge.stripes.validation.IntegerTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@UrlBinding("/biobank/infrastructure/container/{$event}/{containerId}")
public class ContainerActionBean extends PermissionActionBean<Rack> {

    @SpringBean
    private ContainerService containerService;

    @ValidateNestedProperties(value = {
            @Validate(field = "name",
                    required = true, on = "createContainer"),
            @Validate(field = "location",
                    required = false, on = "createContainer"),
            @Validate(converter = IntegerTypeConverter.class, field = "capacity",
                    required = true, on = "createContainer"),
            @Validate(converter = FloatTypeConverter.class, field = "tempMin",
                    required = false, on = "createContainer"),
            @Validate(converter = FloatTypeConverter.class, field = "tempMax",
                    required = false, on = "createContainer")
    })
    private Container container;

    private Long containerId;

    private Infrastructure infrastructure;

    public static Breadcrumb getBreadcrumb(boolean active, Long containerId, String containerName) {
        return new Breadcrumb(ContainerActionBean.class.getName(),
                "detail", false, "cz.bbmri.entities.infrastructure.Container.container", active,
                "containerId", containerId, containerName);
    }

    public static Breadcrumb getCreateContainerBreadcrumb(boolean active, Long biobankId) {
        return new Breadcrumb(ContainerActionBean.class.getName(),
                "createContainerResolution", false, "cz.bbmri.action.infrastructure.InfrastructureActionBean.createContainer",
                active, "biobankId", biobankId);
    }

    public ContainerActionBean() {
        //default
        setPagination(new MyPagedListHolder<Rack>(new ArrayList<Rack>()));
        setComponentManager(new ComponentManager(
                ComponentManager.RACK_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
    }

    public Long getContainerId() {
        return containerId;
    }

    public void setContainerId(Long containerId) {
        this.containerId = containerId;
    }


    public Container getContainer() {
        if (container == null) {
            if (containerId != null) {
                container = containerService.get(containerId);
            }
        }
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    @DefaultHandler
    @HandlesEvent("detail")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}", "project_team_member_confirmed"})
    public Resolution detail() {

        if (getContainer() != null) {
            setBiobankId(getContainer().getInfrastructure().getBiobank().getId());
        }

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(InfrastructureActionBean.getBreadcrumb(false, biobankId));
        getBreadcrumbs().add(ContainerActionBean.getBreadcrumb(true, containerId, getContainer().getName()));

        initiatePagination();
        getPagination().setEvent("detail");
        getPagination().setSource(new ArrayList<Rack>(getContainer().getRacks()));

        return new ForwardResolution(INFRASTRUCTURE_DETAIL_CONTAINER)
                .addParameter("containerId", containerId);
    }

    @DontValidate
    @HandlesEvent("createContainerResolution")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createContainerResolution() {
        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(InfrastructureActionBean.getBreadcrumb(false, biobankId));
        getBreadcrumbs().add(ContainerActionBean.getCreateContainerBreadcrumb(true, biobankId));

        return new ForwardResolution(INFRASTRUCTURE_CREATE_CONTAINER);
    }

    @HandlesEvent("createContainer")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createContainer() {
        infrastructure = getBiobank().getInfrastructure();
        if (!containerService.create(infrastructure.getId(), container, getContext().getValidationErrors(),
                getContext().getMyId())) {
            return new ForwardResolution(InfrastructureActionBean.class, "all")
                    .addParameter("biobankId", biobankId);
        }

        successMsg();
        return new RedirectResolution(InfrastructureActionBean.class, "all")
                .addParameter("biobankId", biobankId);
    }
}
