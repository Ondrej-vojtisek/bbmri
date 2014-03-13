package cz.bbmri.action.infrastructure;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Infrastructure;
import cz.bbmri.entities.infrastructure.Rack;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.facade.BiobankFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.FloatTypeConverter;
import net.sourceforge.stripes.validation.IntegerTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.3.14
 * Time: 0:33
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/biobank/infrastructure/container/{$event}/{containerId}")
public class ContainerActionBean extends PermissionActionBean<Rack> {

    @SpringBean
    private BiobankFacade biobankFacade;

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

    private Long infrastructureId;

    private Infrastructure infrastructure;

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

    public Long getInfrastructureId() {
        return infrastructureId;
    }

    public void setInfrastructureId(Long infrastructureId) {
        this.infrastructureId = infrastructureId;
    }

    public Container getContainer() {
        if (container == null) {
            if (containerId != null) {
                container = biobankFacade.getContainer(containerId);
            }
        }
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Set<Rack> getRacks() {
        if (getContainer() == null) {
            logger.debug("Container null");
            return null;
        }

        return getContainer().getRacks();
    }

    @HandlesEvent("detail")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution detail() {
        if (getContainer() != null) {
            setBiobankId(getContainer().getInfrastructure().getBiobank().getId());
        }

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
     //   infrastructure = getBiobank().getInfrastructure();
        return new ForwardResolution(INFRASTRUCTURE_CREATE_CONTAINER);
    }

    @HandlesEvent("createContainer")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createContainer() {
        infrastructure = getBiobank().getInfrastructure();
        if (!biobankFacade.createContainer(infrastructure.getId(), container, getContext().getValidationErrors())) {
            return new ForwardResolution(InfrastructureActionBean.class, "all")
                    .addParameter("biobankId", biobankId);
        }

        successMsg(null);
        return new RedirectResolution(InfrastructureActionBean.class, "all")
                .addParameter("biobankId", biobankId);
    }


}
