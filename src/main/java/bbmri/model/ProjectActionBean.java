package bbmri.model;

import bbmri.entities.Project;
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
import java.util.Collections;
import java.util.List;


@UrlBinding("/project/{$event}/{project.id}")
public class ProjectActionBean implements ActionBean {

    private MujActionBeanContext ctx;
    public void setContext(ActionBeanContext ctx) { this.ctx = (MujActionBeanContext) ctx; }
    public MujActionBeanContext getContext() { return ctx; }

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");

    public List<Project> getProjects() {
        return ctx.getProjects();
    }

    @ValidateNestedProperties(value = {
                @Validate(on = {"pridej", "uloz"}, field = "name", required = true)
            }
    )
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @DefaultHandler
    public Resolution zobraz() {
        return new ForwardResolution("/projects.jsp");
    }

    public Resolution add() {

        // ctx.getOwner();
         /*
          if(getProjects().isEmpty()){
                getProjects().addAll(getAllProjects());
          }
          EntityManager em = emf.createEntityManager();
          em.getTransaction().begin();


          em.persist(project);
          project.getResearchers().add(ctx.getResearchers().);
          em.getTransaction().commit();
          em.close();
          getProjects().add(project);       */
        return new RedirectResolution(this.getClass(), "zobraz");
    }

         public List<Researcher> getAllProjects(){
           EntityManager em = emf.createEntityManager();
            Query query = em.createQuery("SELECT p FROM Project p");
            return query.getResultList();
       }
        /*
           public List<Researcher> getResearcher(){
           EntityManager em = emf.createEntityManager();
            Query query = em.createQuery("SELECT p FROM Researcher where id=Project.researchers");
            return query.getResultList();
       }   */


}


