package bbmri.action;

import bbmri.entities.Role;
import bbmri.entities.RoleType;
import bbmri.entities.User;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
public class BasicActionBean implements ActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private UserService userService;

    private static final String MY_PROJECTS = "/project_my_projects.jsp";

    private TheActionBeanContext ctx;

    @Override
    public void setContext(ActionBeanContext ctx) {
        this.ctx = (TheActionBeanContext) ctx;
    }

    @Override
    public TheActionBeanContext getContext() {
        return ctx;
    }

    public User getLoggedUser() {
        return ctx.getLoggedUser();
    }

    public Resolution primary_menu_project(){
        return new ForwardResolution(MY_PROJECTS);
    }

    public User getUser(){

        Long id = ctx.getIdentifier();
        logger.debug("getUserbyId: " + id + " user: " + userService.getById(id));
        return userService.getById(id);
    }

    public Set<Role> getRoles(){
        Long id = ctx.getIdentifier();
        logger.debug("getUserbyId: " + id + " user: " + userService.getById(id));
        return userService.getById(id).getRoles();

    }

}
