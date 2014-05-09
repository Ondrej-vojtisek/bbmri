package cz.bbmri.action.biobank;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.service.ContainerService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/biobank/monitoring/{$event}/{biobankId}")
public class BiobankMonitoringActionBean extends PermissionActionBean<Biobank> {

    @SpringBean
    private ContainerService containerService;

    public List<Container> getMonitoringList(){
        logger.debug("GetMonitoringList");

        return containerService.getMonitoredContainers(getBiobank());
    }

    public static Breadcrumb getBreadcrumb(boolean active, Long biobankId) {
        return new Breadcrumb(BiobankMonitoringActionBean.class.getName(),
                "display", false, "cz.bbmri.entities.infrastructure.monitoring.Monitoring.monitoring",
                active, "biobankId", biobankId);
    }

    public BiobankMonitoringActionBean() {
        setComponentManager(new ComponentManager(
                ComponentManager.BIOBANK_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
    }

    @DefaultHandler
    @HandlesEvent("display")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution display() {

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(BiobankMonitoringActionBean.getBreadcrumb(true, biobankId));

        return new ForwardResolution(BIOBANK_DETAIL_MONITORING);
    }
}
