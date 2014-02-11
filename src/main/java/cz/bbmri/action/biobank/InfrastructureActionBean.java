package cz.bbmri.action.biobank;

import cz.bbmri.action.base.PermissionActionBean;
import net.sourceforge.stripes.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;

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

    /* Methods */
    @DontValidate
    @DefaultHandler
    @HandlesEvent("all")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution all() {
        return new ForwardResolution(BIOBANK_DETAIL_INFRASTRUCTURE_ALL);
    }
}
