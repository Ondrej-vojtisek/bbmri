package bbmri.action;

import bbmri.DAOimpl.ResearcherDAOImpl;
import bbmri.entities.Researcher;
import bbmri.service.ResearcherService;
import bbmri.serviceImpl.ResearcherServiceImpl;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import javax.imageio.spi.RegisterableService;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


@UrlBinding("/researcher/{$event}/{researcher.id}")
public class ResearcherActionBean implements ActionBean {

    private MyActionBeanContext ctx;
    private Researcher researcher;
    private long id;
    private ResearcherService researcherService;
    private List<Researcher> researchers;



    public void setContext(ActionBeanContext ctx) {
        this.ctx = (MyActionBeanContext) ctx;
    }

    public MyActionBeanContext getContext() {
        return ctx;
    }

    public ResearcherService getResearcherService() {
        if (researcherService == null) {
            researcherService = new ResearcherServiceImpl();
        }
        return researcherService;
    }

    public Researcher getLoggedResearcher() {
        return ctx.getLoggedResearcher();
    }

    public List<Researcher> getResearchers() {
        return getResearcherService().getAll();
    }

    public Researcher getResearcher() {
        return researcher;
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ValidateNestedProperties(value = {
            @Validate(on = {"pridej", "uloz"}, field = "name", required = true),
            @Validate(on = {"pridej", "uloz"}, field = "surname", required = true)
    }
    )

    @DefaultHandler
    public Resolution zobraz() {

        researchers = getResearcherService().getAll();
        return new ForwardResolution("/researchers.jsp");
    }

    public Resolution create() {
        getResearcherService().create(researcher);
        return new RedirectResolution(this.getClass(), "zobraz");
    }

    public Resolution delete() {
        getResearcherService().remove((Long) id);
        return new RedirectResolution(this.getClass(), "zobraz");
    }

}


