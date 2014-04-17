package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import net.sourceforge.stripes.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@PermitAll
@UrlBinding("/editRequest")
@HttpCache(allow = false)
class EditRequestActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private static final String REQUEST_DETAIL = "/requestDetail.jsp";

    @DefaultHandler
    public Resolution display(){
        return new ForwardResolution(REQUEST_DETAIL);

    }
}
