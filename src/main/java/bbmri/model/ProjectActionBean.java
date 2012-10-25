package bbmri.model;

import bbmri.entities.Project;
import bbmri.entities.ProjectDAOImpl;
import bbmri.entities.Researcher;
import bbmri.entities.ResearcherDAOImpl;
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

    private MyActionBeanContext ctx;

    public void setContext(ActionBeanContext ctx) {
        this.ctx = (MyActionBeanContext) ctx;
    }

    public MyActionBeanContext getContext() {
        return ctx;
    }

    private ProjectDAOImpl projectDAO;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
    private Researcher loggedResearcher;
    private Project project;

    public List<Project> getProjects() {
         if (projectDAO == null) {
            projectDAO = new ProjectDAOImpl();
        }
      //  return projectDAO.();
        return null;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    private List<Project> projects;


     public Researcher getLoggedResearcher() {
        return ctx.getLoggedResearcher();
    }

    public void setLoggedResearcher(Researcher loggedResearcher) {
        this.loggedResearcher = loggedResearcher;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @DefaultHandler
    public Resolution zobraz() {
        if (projectDAO == null) {
            projectDAO = new ProjectDAOImpl();
        }
         projects = projectDAO.getAllProjects();
        return new ForwardResolution("/projects.jsp");
    }

    public Resolution add() {
         if (projectDAO == null) {
            projectDAO = new ProjectDAOImpl();
        }
        projectDAO.addProject(project);



        return new RedirectResolution(this.getClass(), "zobraz");
    }
}


