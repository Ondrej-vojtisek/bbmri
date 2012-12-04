package bbmri.action;

import bbmri.DAOimpl.ResearcherDAOImpl;
import bbmri.entities.Researcher;
import bbmri.service.LoginService;
import bbmri.serviceImpl.LoginServiceImpl;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.IntegerTypeConverter;
import net.sourceforge.stripes.validation.LongTypeConverter;
import net.sourceforge.stripes.validation.Validate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 22.10.12
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */

@UrlBinding("/login/{$event}/{researcher.id}")
public class LoginActionBean implements ActionBean {

    private MyActionBeanContext ctx;


    @Validate(converter = LongTypeConverter.class, on = {"login"},
            required = true, minvalue = 1)
    private Long id;
    @Validate(on = {"login"}, required = true)
    private String password;
    private LoginService loginService;

    public LoginService getLoginService() {
        if (loginService == null) {
            loginService = new LoginServiceImpl();
        }
        return loginService;
    }

    public void setContext(ActionBeanContext ctx) {
        this.ctx = (MyActionBeanContext) ctx;
    }

    public MyActionBeanContext getContext() {
        return ctx;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @DefaultHandler
    public Resolution login() {
        ctx.setLoggedResearcher(null);
        Researcher researcher = getLoginService().login(id, password);
        if (researcher != null) {
            ctx.setLoggedResearcher(researcher);
            return new RedirectResolution("/allProjects.jsp");
        }
        return new ForwardResolution("/index.jsp");
    }

    @HandlesEvent("logout")
    public Resolution logoutResearcher() {
        getLoginService().logout(ctx.getLoggedResearcher());
        ctx.setLoggedResearcher(null);
        return new ForwardResolution("/index.jsp");
    }

}
