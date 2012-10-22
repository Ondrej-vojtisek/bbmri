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

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");

    public List<Project> getProjects() {
     /*   List<?> projects = ctx.getProjects();
        if(projects.isEmpty()){
            return (List<Project>) projects;
        }
        if(projects.get(0).getClass() != Project.class){
            return null;
        }   */

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

    private int owner;
    public int getOwner(){return owner;}
    public void setOwner(int owner) {this.owner = owner;}

    @DefaultHandler
    public Resolution zobraz() {
          if(getProjects().isEmpty()){
                getProjects().addAll(getAllProjects());
          }

        return new ForwardResolution("/projects.jsp");
    }

    public Resolution add() {

          if(getProjects().isEmpty()){
                getProjects().addAll(getAllProjects());
          }

     /*   Researcher res = ctx.getResearchers().get(owner);
          System.out.println("Project owner: " + res.toString() + "\n");
     */
          EntityManager em = emf.createEntityManager();
          em.getTransaction().begin();
          em.persist(project);
          em.getTransaction().commit();
          em.close();
          getProjects().add(project);

        return new RedirectResolution(this.getClass(), "zobraz");
    }

         public List<Project> getAllProjects(){
           EntityManager em = emf.createEntityManager();
            Query query = em.createQuery("SELECT p FROM Project p");
            return query.getResultList();
       }
}


