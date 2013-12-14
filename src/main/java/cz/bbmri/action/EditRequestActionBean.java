package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.7.13
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@UrlBinding("/editRequest")
public class EditRequestActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private static final String REQUEST_DETAIL = "/requestDetail.jsp";

    @DefaultHandler
    public Resolution display(){
        return new ForwardResolution(REQUEST_DETAIL);

    }
}
