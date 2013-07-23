package bbmri.action;

import net.sourceforge.stripes.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 17.7.13
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 */

@PermitAll
@UrlBinding("/empty")
public class EmptyActionBean implements ActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final String DEFAULT = "/empty.jsp";

    private TheActionBeanContext ctx;

    @Override
    public void setContext(ActionBeanContext ctx) {
         this.ctx = (TheActionBeanContext) ctx;
    }

    @Override
    public TheActionBeanContext getContext() {
         return ctx;
    }

    @DefaultHandler
          @DontValidate
          public Resolution view(){
              return new ForwardResolution("/index.jsp");
          }

}
