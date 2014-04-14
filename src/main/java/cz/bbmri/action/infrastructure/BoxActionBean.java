package cz.bbmri.action.infrastructure;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.action.biobank.BiobankActionBean;
import cz.bbmri.entities.infrastructure.*;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.BoxService;
import cz.bbmri.service.RackService;
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
 * Time: 0:37
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/biobank/infrastructure/box/{$event}/{boxId}")
public class BoxActionBean extends PermissionActionBean<Position> {

    @SpringBean
    private BoxService boxService;

    @SpringBean
    private RackService rackService;

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

    private Long boxId;

    private Box box;

    private Long rackId;

    public static Breadcrumb getBoxBreadcrumb(boolean active, Long boxId, String boxName) {
        return new Breadcrumb(BoxActionBean.class.getName(),
                "detail", false, "cz.bbmri.entities.infrastructure.Box.box", active,
                "boxId", boxId, boxName);
    }

    public BoxActionBean() {
        //default
        setPagination(new MyPagedListHolder<Position>(new ArrayList<Position>()));
        setComponentManager(new ComponentManager(
                ComponentManager.POSITION_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
    }

    public void setStandaloneBox(StandaloneBox standaloneBox) {
        this.standaloneBox = standaloneBox;
    }

    public void setRackBox(RackBox rackBox) {
        this.rackBox = rackBox;
    }

    public boolean getIsStandAloneBox() {
        if (getBox() != null) {
            return box instanceof StandaloneBox;
        }
        return false;
    }

    public boolean getIsRackBox() {
        if (getBox() != null) {
            return box instanceof RackBox;
        }
        return false;
    }

    public RackBox getRackBox() {
        if (rackBox == null) {
            if (boxId != null) {
                rackBox = (RackBox) boxService.get(boxId);
            }
        }
        return rackBox;
    }

    public StandaloneBox getStandaloneBox() {
        if (standaloneBox == null) {
            if (boxId != null) {
                standaloneBox = (StandaloneBox) boxService.get(boxId);
            }
        }
        return standaloneBox;
    }

    public Box getBox() {
        if (box == null) {
            if (boxId != null) {
                box = boxService.get(boxId);
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

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public Long getRackId() {
        return rackId;
    }

    public void setRackId(Long rackId) {
        this.rackId = rackId;
    }

    @HandlesEvent("detail")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution detail() {

        if (getBox() != null) {
            if (getIsRackBox()) {
                setBiobankId(getRackBox().getRack().getContainer().
                        getInfrastructure().getBiobank().getId());
            }
            if (getIsStandAloneBox()) {
                setBiobankId(getStandaloneBox().getInfrastructure()
                        .getBiobank().getId());
            }
        }

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(InfrastructureActionBean.getBreadcrumb(false, biobankId));

        if (getIsRackBox()) {
            getBreadcrumbs().add(ContainerActionBean.getBreadcrumb(false,
                    getRackBox().getRack().getContainer().getId(), getRackBox().getRack().getContainer().getName()));
            getBreadcrumbs().add(RackActionBean.getBreadcrumb(false,
                    getRackBox().getRack().getId(), getRackBox().getRack().getName()));
        }

        getBreadcrumbs().add(BoxActionBean.getBoxBreadcrumb(true, boxId, getBox().getName()));

        getPagination().setEvent("detail");
        getPagination().setSource(new ArrayList<Position>(getBox().getPositions()));

        return new ForwardResolution(INFRASTRUCTURE_DETAIL_BOX)
                .addParameter("boxId", boxId);
    }

    @HandlesEvent("createStandaloneBoxResolution")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createStandaloneBoxResolution() {
        return new ForwardResolution(INFRASTRUCTURE_CREATE_STANDALONEBOX);
    }

    @HandlesEvent("createRackBoxResolution")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createRackBoxResolution() {
        Rack rack = rackService.get(rackId);
        if (rack != null) {
            setBiobankId(rack.getContainer().getInfrastructure().getBiobank().getId());
        }
        return new ForwardResolution(INFRASTRUCTURE_CREATE_RACKBOX);
    }


    @HandlesEvent("createStandaloneBox")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createStandaloneBox() {
        Infrastructure infrastructure = getBiobank().getInfrastructure();

        if (!boxService.createStandaloneBox(infrastructure.getId(), standaloneBox, getContext().getValidationErrors())) {
            return new ForwardResolution(InfrastructureActionBean.class, "all")
                    .addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(InfrastructureActionBean.class, "all")
                .addParameter("biobankId", biobankId);
    }

    @HandlesEvent("createRackBox")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createRackBox() {
        if (!boxService.createBox(rackId, rackBox, getContext().getValidationErrors())) {
            return new ForwardResolution(RackActionBean.class, "detail")
                    .addParameter("rackId", rackId);
        }
        successMsg(null);
        return new RedirectResolution(RackActionBean.class, "detail")
                .addParameter("rackId", rackId);
    }
}
