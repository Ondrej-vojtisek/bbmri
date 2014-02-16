package cz.bbmri.action.infrastructure;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.*;
import cz.bbmri.facade.BiobankFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.FloatTypeConverter;
import net.sourceforge.stripes.validation.IntegerTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 11.2.14
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */
@HttpCache(allow = false)
@UrlBinding("/infrastructure/{$event}/{biobankId}/{containerId}/{rackId}/{boxId}")
public class InfrastructureActionBean extends PermissionActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private Biobank biobank;

    @SpringBean
    private BiobankFacade biobankFacade;

    private Infrastructure infrastructure;

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

    @ValidateNestedProperties(value = {
            @Validate(field = "name",
                    required = true, on = "createRackBox"),
            @Validate(converter = IntegerTypeConverter.class, field = "capacity",
                    required = true, on = "createRackBox"),
            @Validate(converter = FloatTypeConverter.class, field = "tempMin",
                    required = false, on = "createRackBox"),
            @Validate(converter = FloatTypeConverter.class, field = "tempMax",
                    required = false, on = "createRackBox")
    })
    private RackBox rackBox;

    @ValidateNestedProperties(value = {
            @Validate(field = "name",
                    required = true, on = "createStandaloneBox"),
            @Validate(converter = IntegerTypeConverter.class, field = "capacity",
                    required = true, on = "createStandaloneBox"),
            @Validate(converter = FloatTypeConverter.class, field = "tempMin",
                    required = false, on = "createStandaloneBox"),
            @Validate(converter = FloatTypeConverter.class, field = "tempMax",
                    required = false, on = "createStandaloneBox")
    })
    private StandaloneBox standaloneBox;

    @ValidateNestedProperties(value = {
            @Validate(field = "name",
                    required = true, on = "createRack"),
            @Validate(converter = IntegerTypeConverter.class, field = "capacity",
                    required = true, on = "createRack")
    })
    private Rack rack;

    private Box box;

    private Long containerId;

    private Long rackId;

    private Long boxId;

    public Long getInfrastructureId() {
        if (getBiobank() == null) {
            return null;
        }
        if (getBiobank().getInfrastructure() == null) {
            return null;
        }
        return getBiobank().getInfrastructure().getId();
    }

    public Infrastructure getInfrastructure() {
        if (infrastructure == null) {
            infrastructure = biobankFacade.getInfrastructure(getInfrastructureId());
        }
        return infrastructure;
    }

    public Biobank getBiobank() {
        if (biobank == null) {
            if (biobankId != null) {
                biobank = biobankFacade.get(biobankId);
            }
        }
        return biobank;
    }

    public Set<Container> getContainers() {
        if (getInfrastructure() == null) {
            return null;
        }

        return getInfrastructure().getContainers();
    }

    public Set<StandaloneBox> getStandaloneBoxes() {
        if (getInfrastructure() == null) {
            return null;
        }

        return getInfrastructure().getStandaloneBoxes();
    }

    public Set<Rack> getRacks() {
        if (getContainer() == null) {
            logger.debug("Container null");
            return null;
        }

        logger.debug("Racks: " + getContainer().getRacks());

        return getContainer().getRacks();
    }


    public void setStandaloneBox(StandaloneBox standaloneBox) {
        this.standaloneBox = standaloneBox;
    }


    public Set<RackBox> getRackBoxes() {
        if (getRack() == null) {
            return null;
        }

        return getRack().getRackBoxes();
    }

    public Container getContainer() {
        if (container == null) {
            if (containerId != null) {
                container = biobankFacade.getContainer(containerId);
            }
        }
        return container;
    }

    public Rack getRack() {
        if (rack == null) {
            if (rackId != null) {
                rack = biobankFacade.getRack(rackId);
            }
        }
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

    public void setRackBox(RackBox rackBox) {
        this.rackBox = rackBox;
    }

    public RackBox getRackBox() {
        if (rackBox == null) {
            if (boxId != null) {
                rackBox = (RackBox) biobankFacade.getBox(boxId);
            }
        }
        return rackBox;
    }

    public StandaloneBox getStandaloneBox() {
        if (standaloneBox == null) {
            if (boxId != null) {
                standaloneBox = (StandaloneBox) biobankFacade.getBox(boxId);
            }
        }
        return standaloneBox;
    }

    public Box getBox() {
        if (box == null) {
            if (boxId != null) {
                box = biobankFacade.getBox(boxId);
            }
        }
        return box;
    }

    public Set<Position> getPositions() {
        if (getBox() == null) {
            return null;
        }
        return getBox().getPositions();
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Long getContainerId() {
        return containerId;
    }

    public void setContainerId(Long containerId) {
        this.containerId = containerId;
    }

    public Long getRackId() {
        return rackId;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    /* Methods */
    @DontValidate
    @DefaultHandler
    @HandlesEvent("all")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution all() {
        if (getBiobank().getInfrastructure() == null) {
            biobankFacade.createInfrastructure(biobankId);
            return new RedirectResolution(INFRASTRUCTURE_DETAIL)
                    .addParameter("biobankId", biobankId);
        }
        return new ForwardResolution(INFRASTRUCTURE_DETAIL).addParameter("biobankId", biobankId);
    }


    @DontValidate
    @HandlesEvent("createContainerResolution")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createContainerResolution() {
        return new ForwardResolution(INFRASTRUCTURE_CREATE_CONTAINER);
    }

    @DontValidate
    @HandlesEvent("createRackResolution")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createRackResolution() {
        return new ForwardResolution(INFRASTRUCTURE_CREATE_RACK);
    }

    @HandlesEvent("createStandaloneBoxResolution")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createStandaloneBoxResolution() {
        return new ForwardResolution(INFRASTRUCTURE_CREATE_STANDALONEBOX);
    }

    @HandlesEvent("createRackBoxResolution")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createRackBoxResolution() {
        return new ForwardResolution(INFRASTRUCTURE_CREATE_RACKBOX)
                .addParameter("biobankId", biobankId)
                .addParameter("containerId", containerId)
                .addParameter("rackId", rackId);
    }

    @HandlesEvent("createContainer")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createContainer() {
        if (!biobankFacade.createContainer(getInfrastructureId(), container, getContext().getValidationErrors())) {
            return new ForwardResolution(this.getClass(), "all").addParameter("biobankId", biobankId);
        }

        biobank = null;
        successMsg(null);
        return new RedirectResolution(this.getClass(), "all").addParameter("biobankId", biobankId);
    }

    @HandlesEvent("createRack")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createRack() {
        if (!biobankFacade.createRack(containerId, rack, getContext().getValidationErrors())) {
            return new ForwardResolution(this.getClass(), "containerDetail")
                    .addParameter("biobankId", biobankId)
                    .addParameter("containerId", containerId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "containerDetail")
                .addParameter("biobankId", biobankId)
                .addParameter("biobankId", biobankId)
                .addParameter("containerId", containerId);
    }

    @HandlesEvent("createStandaloneBox")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createStandaloneBox() {
        if (!biobankFacade.createStandaloneBox(getInfrastructureId(), standaloneBox, getContext().getValidationErrors())) {
            return new ForwardResolution(this.getClass(), "all").addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "all").addParameter("biobankId", biobankId);
    }

    @HandlesEvent("createRackBox")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createRackBox() {
        logger.debug("RackBox: " + rackBox);
        logger.debug("rackId " + rackId);

        if (!biobankFacade.createBox(rackId, rackBox, getContext().getValidationErrors())) {
            return new ForwardResolution(this.getClass(), "rackDetail")
                    .addParameter("biobankId", biobankId)
                    .addParameter("containerId", containerId)
                    .addParameter("rackId", rackId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "rackDetail")
                .addParameter("biobankId", biobankId)
                .addParameter("containerId", containerId)
                .addParameter("rackId", rackId);
    }

    @HandlesEvent("containerDetail")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution containerDetail() {
        return new ForwardResolution(INFRASTRUCTURE_DETAIL_CONTAINER)
                .addParameter("biobankId", biobankId)
                .addParameter("containerId", containerId);
    }

    @HandlesEvent("boxDetail")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution boxDetail() {
        return new ForwardResolution(INFRASTRUCTURE_DETAIL_BOX)
                .addParameter("biobankId", biobankId)
                .addParameter("containerId", containerId)
                .addParameter("rackId", rackId)
                .addParameter("boxId", boxId);
    }

    @HandlesEvent("rackDetail")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution rackDetail() {

        return new ForwardResolution(INFRASTRUCTURE_DETAIL_RACK)
                .addParameter("biobankId", biobankId)
                .addParameter("containerId", containerId)
                .addParameter("rackId", rackId);
    }


}
