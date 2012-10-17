package bbmri.model;

import bbmri.entities.Researcher;
import bbmri.persistence.Person;
import com.sun.org.apache.regexp.internal.RE;
import net.sourceforge.stripes.action.*;
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

    private MujActionBeanContext ctx;
    public void setContext(ActionBeanContext ctx) { this.ctx = (MujActionBeanContext) ctx; }
    public MujActionBeanContext getContext() { return ctx; }

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");

    public List<Researcher> getResearchers() {
        return ctx.getResearchers();
    }

    @ValidateNestedProperties(value = {
                @Validate(on = {"pridej", "uloz"}, field = "name", required = true),
                @Validate(on = {"pridej", "uloz"}, field = "surname", required = true)
            }
    )

    private Researcher researcher;
    public Researcher getResearcher() { return researcher;  }
    public void setResearcher(Researcher researcher) { this.researcher = researcher; }

    @DefaultHandler
    public Resolution zobraz() {
        return new ForwardResolution("/researchers.jsp");
    }

    public Resolution pridej() {

          if(getResearchers().isEmpty()){
                getResearchers().addAll(getAllResearchers());
          }
          EntityManager em = emf.createEntityManager();
          em.getTransaction().begin();


          em.persist(researcher);
          em.getTransaction().commit();
          em.close();
              getResearchers().add(researcher);

        return new RedirectResolution(this.getClass(), "zobraz");
    }

       public Resolution delete() {

          Long id = researcher.getId();
          System.out.println("ID DELETE: "+ id);
          EntityManager em = emf.createEntityManager();
          em.getTransaction().begin();
            Researcher del = em.find(Researcher.class, id);
            getResearchers().remove(del);
            em.remove(del);
            em.getTransaction().commit();
            em.close();

        return new RedirectResolution(this.getClass(), "zobraz");
    }

       public List<Researcher> getAllResearchers(){
           EntityManager em = emf.createEntityManager();
            Query query = em.createQuery("SELECT p FROM Researcher p");
            return query.getResultList();
       }

}


