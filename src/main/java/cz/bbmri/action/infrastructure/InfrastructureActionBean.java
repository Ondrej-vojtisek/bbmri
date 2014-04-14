package cz.bbmri.action.infrastructure;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.action.biobank.BiobankActionBean;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Infrastructure;
import cz.bbmri.entities.infrastructure.StandaloneBox;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.BoxService;
import cz.bbmri.service.ContainerService;
import cz.bbmri.service.InfrastructureService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 11.2.14
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/biobank/infrastructure/{$event}/{biobankId}")
public class InfrastructureActionBean extends PermissionActionBean<Container> {

    @SpringBean
    private InfrastructureService infrastructureService;

    @SpringBean
    private BoxService boxService;

    @SpringBean
    private ContainerService containerService;


    private Infrastructure infrastructure;

    private MyPagedListHolder<StandaloneBox> boxesPagination;

    private ComponentManager boxComponentManager;

    public static Breadcrumb getBreadcrumb(boolean active, Long biobankId) {
        return new Breadcrumb(InfrastructureActionBean.class.getName(),
                "all", false, "cz.bbmri.action.infrastructure.InfrastructureActionBean.occupancy", active,
                "biobankId", biobankId);
    }

    public ComponentManager getBoxComponentManager() {
        return boxComponentManager;
    }

    public MyPagedListHolder<StandaloneBox> getBoxesPagination() {
        return boxesPagination;
    }

    private Integer page2;

    private String orderParam2;

    private boolean desc2;

    public Integer getPage2() {
        return page2;
    }

    public void setPage2(Integer page2) {
        this.page2 = page2;
    }

    public String getOrderParam2() {
        return orderParam2;
    }

    public void setOrderParam2(String orderParam2) {
        this.orderParam2 = orderParam2;
    }

    public boolean isDesc2() {
        return desc2;
    }

    public void setDesc2(boolean desc2) {
        this.desc2 = desc2;
    }

    public InfrastructureActionBean() {
        boxesPagination = new MyPagedListHolder<StandaloneBox>(new ArrayList<StandaloneBox>());
        boxComponentManager = new ComponentManager(
                ComponentManager.BOX_DETAIL,
                ComponentManager.BIOBANK_DETAIL);
        boxesPagination.setIdentifierParam("biobankId");
        // distinguish between pagination params
        boxesPagination.setWebParamDiscriminator("2");

        setPagination(new MyPagedListHolder<Container>(new ArrayList<Container>()));
        setComponentManager(new ComponentManager(
                ComponentManager.CONTAINER_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
        getPagination().setIdentifierParam("biobankId");

    }

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
            infrastructure = infrastructureService.get(getInfrastructureId());
        }
        return infrastructure;
    }

    public void initiatePaginationBoxes() {
        if (page2 != null) {
            boxesPagination.setCurrentPage(page2);
        }
        if (orderParam2 != null) {
            boxesPagination.setOrderParam(orderParam2);
        }
        boxesPagination.setDesc(desc2);
    }

    /* Methods */
    @DontValidate
    @DefaultHandler
    @HandlesEvent("all")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution all() {

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId.longValue(), getBiobank()));
        getBreadcrumbs().add(InfrastructureActionBean.getBreadcrumb(true, biobankId));

        if (getBiobank().getInfrastructure() == null) {
            infrastructureService.initialize(getBiobank());
            return new RedirectResolution(INFRASTRUCTURE_DETAIL)
                    .addParameter("biobankId", biobankId);
        }

        if (biobankId != null) {
            boxesPagination.setIdentifier(biobankId);
            getPagination().setIdentifier(biobankId);
        }

        initiatePagination();
        initiatePaginationBoxes();
        // default
        if (getOrderParam() == null) {
            setOrderParam("name");
            getPagination().setOrderParam("name");
        }

        // default
        if (orderParam2 == null) {
            orderParam2 = "name";
            boxesPagination.setOrderParam("name");
        }

        getPagination().setEvent("all");
        getPagination().setSource(containerService.getSortedContainers(biobankId,
                getPagination().getOrderParam(),
                getPagination().getDesc()));

        boxesPagination.setEvent("all");
        boxesPagination.setSource(boxService.getSortedStandAloneBoxes(biobankId,
                boxesPagination.getOrderParam(),
                boxesPagination.getDesc()));

        return new ForwardResolution(INFRASTRUCTURE_DETAIL).addParameter("biobankId", biobankId);
    }

}
