package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import net.sourceforge.stripes.action.*;

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

    private static final String REQUEST_DETAIL = "/requestDetail.jsp";

    @DefaultHandler
    public Resolution display(){
        return new ForwardResolution(REQUEST_DETAIL);

    }
}
