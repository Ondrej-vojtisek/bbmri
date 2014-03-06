package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.facade.UserFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 10.2.14
 * Time: 15:25
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@HttpCache(allow = false)
@UrlBinding("/timeline")
public class TimelineActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

      @SpringBean
      private UserFacade userFacade;

//    @PermitAll
//    @DontValidate
//    @DefaultHandler
//    public Resolution timeline() {
//         return new StreamingResolution("application/json", userFacade.getJSON(getContext().getMyId()));
//    }
}
