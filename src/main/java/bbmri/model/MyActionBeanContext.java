package bbmri.model;

import bbmri.entities.Researcher;
import net.sourceforge.stripes.action.ActionBeanContext;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 24.10.12
 * Time: 0:45
 * To change this template use File | Settings | File Templates.
 */
public class MyActionBeanContext extends ActionBeanContext {
    public void setLoggedResearcher(Researcher loggedResearcher) {
        getRequest().getSession().setAttribute("loggedResearcher", loggedResearcher);
    }

    public Researcher getLoggedResearcher() {
        return (Researcher) getRequest().getSession().getAttribute("loggedResearcher");
    }

}
