package bbmri.action;

import bbmri.entities.User;
import bbmri.service.ProjectService;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
public class BasicActionBean implements ActionBean {

    private MyActionBeanContext ctx;

    public void setContext(ActionBeanContext ctx) {
        this.ctx = (MyActionBeanContext) ctx;
    }

    public MyActionBeanContext getContext() {
        return ctx;
    }

    public User getLoggedUser() {
        return ctx.getLoggedUser();
    }




}
