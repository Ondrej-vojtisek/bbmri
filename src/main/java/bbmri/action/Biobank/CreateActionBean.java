package bbmri.action.biobank;

import bbmri.action.BasicActionBean;
import bbmri.entities.Biobank;
import bbmri.facade.BiobankFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 15.10.13
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
@Wizard
//@UrlBinding("/Biobank/Create/{$event}/{biobank.id}")
public class CreateActionBean extends BasicActionBean {


    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

   /* Variables */

    @SpringBean
    private BiobankFacade biobankFacade;

    @ValidateNestedProperties(value = {
            @Validate(on = {"create"}, field = "name", required = true),
            @Validate(on = {"create"}, field = "address", required = true),
    })
    private Biobank newBiobank;

    @DontValidate
    @DefaultHandler
    @HandlesEvent("create") /* Necessary for stripes security tag*/
    @RolesAllowed({"administrator", "developer"})
    public Resolution display() {
        return new ForwardResolution(BIOBANK_CREATE);
    }
}
