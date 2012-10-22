package bbmri.model;

import bbmri.entities.Researcher;
import bbmri.entities.ResearcherDAO;
import bbmri.entities.ResearcherDAOImpl;
import bbmri.persistence.Person;
import com.sun.org.apache.regexp.internal.RE;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@UrlBinding("/researcher/{$event}/{researcher.id}")
public class ResearcherActionBean implements ActionBean {

    private ActionBeanContext ctx;
    public void setContext(ActionBeanContext ctx) { this.ctx = ctx; }
    public ActionBeanContext getContext() { return ctx; }
    private ResearcherDAOImpl researcherDAO;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");

    private Researcher researcher;
    private long id;

    private List<Researcher> researchers;

    public List<Researcher> getResearchers() {
        if(researcherDAO == null){
            researcherDAO = new ResearcherDAOImpl();
        }
        return researcherDAO.getAllResearchers();
    }

    public void setResearchers(List<Researcher> researchers) {
        this.researchers = researchers;
    }

    public Researcher getResearcher() { return researcher;  }
    public void setResearcher(Researcher researcher) { this.researcher = researcher; }

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
          if(researcherDAO == null){
             researcherDAO = new ResearcherDAOImpl();
         }
      //   researchers = researcherDAO.getAllResearchers();
        return new ForwardResolution("/researchers.jsp");
        //return new ForwardResolution("/project.jsp");
    }

    public Resolution pridej() {
         if(researcherDAO == null){
             researcherDAO = new ResearcherDAOImpl();
         }

          researcherDAO.addResearcher(researcher);
          return new RedirectResolution(this.getClass(), "zobraz");
    }

       public Resolution delete() {
         if(researcherDAO == null){
             researcherDAO = new ResearcherDAOImpl();
         }
        researcherDAO.removeResearcher(id);
        return new RedirectResolution(this.getClass(), "zobraz");
    }

        public Resolution delete2() {
         if(researcherDAO == null){
             researcherDAO = new ResearcherDAOImpl();
         }
        //researcherDAO.removeResearcher(researcher);
        return new RedirectResolution(this.getClass(), "zobraz");
    }

}


