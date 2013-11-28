package bbmri.action;

import net.sourceforge.stripes.action.*;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 28.11.13
 * Time: 16:45
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/")
public class LomitkoActionBean extends BasicActionBean  {

    @DontValidate
    @DefaultHandler
    public Resolution display() {
        logger.debug("LOMITKO SCREEN");

        return new RedirectResolution(WELCOME);
    }
}
