package cz.bbmri.action.infrastructure;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Infrastructure;
import cz.bbmri.facade.BiobankFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 11.2.14
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */
@HttpCache(allow = false)
@UrlBinding("/biobank/infrastructure/{$event}/{biobankId}")
public class InfrastructureActionBean extends PermissionActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private BiobankFacade biobankFacade;

    Long infrastructureId;

    private Infrastructure infrastructure;

    public Infrastructure getInfrastructure() {
        if (infrastructure == null) {
            if (infrastructureId != null) {
                infrastructure = biobankFacade.getInfrastructure(infrastructureId);
            }
        }
        return infrastructure;
    }

    public List<Container> getContainers() {
        return getInfrastructure().getContainers();
    }

    public List<Box> getBoxes() {
        return getInfrastructure().getBoxes();
    }

    public boolean empty() {
        if (getInfrastructure() == null) {
            return true;
        }
        if (getContainers().isEmpty() && getBoxes().isEmpty()) {
            return true;
        }
        return false;
    }

    /* Methods */
    @DontValidate
    @DefaultHandler
    @HandlesEvent("all")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution all() {
        return new ForwardResolution(BIOBANK_DETAIL_INFRASTRUCTURE_ALL);
    }

    @DontValidate
    @HandlesEvent("initiateInfrastructure")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution initiateInfrastructure() {
        return new RedirectResolution(BIOBANK_DETAIL_INFRASTRUCTURE_ALL);
    }

}
