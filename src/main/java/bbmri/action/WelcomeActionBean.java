package bbmri.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import javax.annotation.security.PermitAll;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 12.11.13
 * Time: 16:06
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/welcome")
public class WelcomeActionBean extends BasicActionBean {

    @PermitAll
    @DefaultHandler
    public Resolution display() {
        return new ForwardResolution(WELCOME);
    }
}
