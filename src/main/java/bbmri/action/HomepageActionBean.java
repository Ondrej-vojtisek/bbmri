package bbmri.action;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.9.13
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 */
public class HomepageActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final String INDEX = "/index.jsp";
    private static final String BBMRI_INDEX = "http://index.bbmri.cz/";
    private static final String HTTPS_BBMRI_INDEX = "https://index.bbmri.cz/";

    @DefaultHandler
    public Resolution display() {
        return new ForwardResolution(INDEX);
    }

    private String getFullURL() {
        HttpServletRequest request = getContext().getRequest();
        StringBuffer requestURL = request.getRequestURL();
        return requestURL.toString();
    }

    /* For distinction between www.bbmri.cz as a homepage and index.bbmri.cz which represents service*/
    public void redirectHTTP() {
        if (getFullURL().equals(BBMRI_INDEX)) {
            HttpServletResponse response = getContext().getResponse();
            try {
                response.sendRedirect(HTTPS_BBMRI_INDEX);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Boolean isHTTPS(){
        return getFullURL().equals(HTTPS_BBMRI_INDEX);
    }


}
