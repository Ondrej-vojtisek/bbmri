package cz.bbmri.action.infrastructure;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.action.biobank.BiobankActionBean;
import cz.bbmri.entities.enumeration.MeasurementType;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.monitoring.Monitoring;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.service.ContainerService;
import cz.bbmri.service.MonitoringService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;

import javax.annotation.security.RolesAllowed;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/monitoring/{$event}/{containerId/{monitoringId}}")
public class MonitoringActionBean extends PermissionActionBean {

    @SpringBean
    private ContainerService containerService;

    @SpringBean
    private MonitoringService monitoringService;

    public MonitoringActionBean() {
        //default
        setComponentManager(new ComponentManager(
                ComponentManager.CONTAINER_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
    }

    public static Breadcrumb getBreadcrumb(boolean active, Long containerId) {
        return new Breadcrumb(MonitoringActionBean.class.getName(),
                "monitoring", false, "cz.bbmri.entities.infrastructure.monitoring.Monitoring.monitoring", active,
                "containerId", containerId);
    }

    public static Breadcrumb getCreateMonitoringBreadcrumb(boolean active, Long containerId) {
        return new Breadcrumb(MonitoringActionBean.class.getName(),
                "createMonitoringResolution", false, "cz.bbmri.action.infrastructure.MonitoringActionBean.createMonitoring",
                active, "containerId", containerId);
    }

    private Long containerId;

    private Container container;

    private Monitoring monitoring;

    private Long monitoringId;

    @Validate(field = "name", required = true, on = "createMonitoring")
    private MeasurementType measurementType;

    public Long getContainerId() {
        return containerId;
    }

    public void setContainerId(Long containerId) {
        this.containerId = containerId;
    }

    public void setMonitoring(Monitoring monitoring) {
        this.monitoring = monitoring;
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }

    public Monitoring getMonitoring() {
        if (monitoring == null) {
            if (monitoringId != null) {
                monitoring = monitoringService.get(monitoringId);
            }
        }
        return monitoring;
    }

    public Container getContainer() {
        if (container == null) {
            if (containerId != null) {
                container = containerService.get(containerId);
            }
        }
        return container;
    }

    public Long getMonitoringId() {
        return monitoringId;
    }

    public void setMonitoringId(Long monitoringId) {
        this.monitoringId = monitoringId;
    }

    @DontValidate
    @HandlesEvent("createMonitoringResolution")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createMonitoringResolution() {

        setBiobankId(getContainer().getInfrastructure().getBiobank().getId());

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(InfrastructureActionBean.getBreadcrumb(false, biobankId));
        getBreadcrumbs().add(ContainerActionBean.getBreadcrumb(false, containerId, getContainer().getName()));
        getBreadcrumbs().add(MonitoringActionBean.getCreateMonitoringBreadcrumb(true, containerId));

        return new ForwardResolution(INFRASTRUCTURE_CREATE_MONITORING);
    }

    @HandlesEvent("createMonitoring")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createMonitoring() {

        if (!monitoringService.create(containerId, monitoring, getContext().getValidationErrors())) {
            return new ForwardResolution(ContainerActionBean.class, "detail").addParameter("containerId", containerId);
        }

        successMsg();

        return new RedirectResolution(ContainerActionBean.class, "detail").addParameter("containerId", containerId);
    }

    @DefaultHandler
    @HandlesEvent("monitoring")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}", "project_team_member_confirmed"})
    public Resolution monitoring() {

        // first load of page
        if(monitoringId == null && getContainer().getMonitorings() != null){

            Monitoring firstMonitoring = getContainer().getMonitorings().get(0);
            if(firstMonitoring != null){
                monitoringId = firstMonitoring.getId();
            }
        }

        setBiobankId(getContainer().getInfrastructure().getBiobank().getId());

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(InfrastructureActionBean.getBreadcrumb(false, biobankId));
        getBreadcrumbs().add(ContainerActionBean.getBreadcrumb(false, containerId, getContainer().getName()));
        getBreadcrumbs().add(MonitoringActionBean.getBreadcrumb(true, containerId));

        return new ForwardResolution(INFRASTRUCTURE_DETAIL_MONITORING);

    }

}
