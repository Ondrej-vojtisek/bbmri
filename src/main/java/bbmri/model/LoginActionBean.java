package bbmri.model;

import bbmri.entities.Researcher;
import bbmri.entities.ResearcherDAOImpl;
import net.sourceforge.stripes.action.*;

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

    public void setContext(ActionBeanContext ctx) {
        this.ctx = (MyActionBeanContext) ctx;
    }

    public MyActionBeanContext getContext() {
        return ctx;
    }

    private ResearcherDAOImpl researcherDAO;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
    private long id;
    private String password;

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
        if (researcherDAO == null) {
            researcherDAO = new ResearcherDAOImpl();
        }

        if (researcherDAO.verifyPassword(password, id)) {
            Researcher res = researcherDAO.getResearcher(id);
            researcherDAO.loginResearcher(id, true);
            ctx.setLoggedResearcher(res);
            return new RedirectResolution("/researchers.jsp");
        }

        return new ForwardResolution("/login.jsp");
    }

    @HandlesEvent("logout")
    public Resolution logoutResearcher() {
        if (researcherDAO == null) {
            researcherDAO = new ResearcherDAOImpl();
        }
        Researcher res = ctx.getLoggedResearcher();
        researcherDAO.loginResearcher(res.getId(), false);
        ctx.setLoggedResearcher(null);
        return new ForwardResolution("/login.jsp");

    }

}
