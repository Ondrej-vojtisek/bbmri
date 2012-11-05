package bbmri.serviceImpl;

import bbmri.DAO.ProjectDAO;
import bbmri.DAO.ResearcherDAO;
import bbmri.DAOimpl.ProjectDAOImpl;
import bbmri.DAOimpl.ResearcherDAOImpl;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.Researcher;
import bbmri.service.ProjectService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
public class ProjectServiceImpl implements ProjectService {

     EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
     ResearcherDAO researcherDAO;
     ProjectDAO projectDAO;

     private ResearcherDAO getResearcherDAO(){
          if(researcherDAO == null){
            researcherDAO = new ResearcherDAOImpl();
         }
        return researcherDAO;
     }

     private ProjectDAO getProjectDAO(){
          if(projectDAO == null){
            projectDAO = new ProjectDAOImpl();
         }
        return projectDAO;
     }

     public Project create(Project project, Researcher researcher){
         EntityManager em = emf.createEntityManager();
         em.getTransaction().begin();
         project.setProjectState(ProjectState.NEW);
         getProjectDAO().create(project, em);
         Researcher resDB = getResearcherDAO().get(researcher.getId(), em);
         getProjectDAO().assignResearcherToProject(resDB, project);
         em.getTransaction().commit();
         em.close();
         return project;
     }

    public void remove(Long id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Project project = getProjectDAO().get(id, em);
        if(project != null){
            getProjectDAO().remove(project, em);
        }
        em.getTransaction().commit();
        em.close();
     }

    public Project update(Project project){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        getProjectDAO().update(project, em);
        em.getTransaction().commit();
        em.close();
        return project;
    }

    public List<Project> getAll(){
       EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Project> projects = getProjectDAO().getAll(em);
        em.getTransaction().commit();
        em.close();
        return projects;
    }

     public List<Project> getAllByResearcher(Researcher researcher){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Project> projects = getProjectDAO().getAllByResearcher(researcher);
        em.getTransaction().commit();
        em.close();
        return projects;
     }

     public List<Project> getAllByProjectState(ProjectState projectState){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Project> projects = getProjectDAO().getAllByProjectState(projectState, em);
        em.getTransaction().commit();
        em.close();
        return projects;
     }

     public void assignResearcher(Long researcherId, Long projectId){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Researcher researcherDB = getResearcherDAO().get(researcherId, em);
        Project projectDB = getProjectDAO().get(projectId, em);
        if( getProjectDAO().projectContainsResearcher(researcherDB, projectDB) == true){
            return;
        }
        getProjectDAO().assignResearcherToProject(researcherDB, projectDB);
        em.getTransaction().commit();
        em.close();
     }

    public void removeResearcherFromProject(Long researcherId, Long projectId){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Researcher researcherDB = getResearcherDAO().get(researcherId, em);
        Project projectDB = getProjectDAO().get(projectId, em);
        if( getProjectDAO().projectContainsResearcher(researcherDB, projectDB) == false){
            return;
        }
        getProjectDAO().removeResearcherFromProject(researcherDB, projectDB);
        em.getTransaction().commit();
        em.close();
    }

    public List<Researcher> getAllAssignedResearchers(Long projectId){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Project projectDB = getProjectDAO().get(projectId, em);
        List<Researcher> researchers =  getProjectDAO().getAllResearchersByProject(projectDB);
        em.getTransaction().commit();
        em.close();
        return researchers;
    }

    public void approve(Long id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Project projectDB = getProjectDAO().get(id, em);
        if(projectDB.getProjectState() == ProjectState.NEW){
            projectDB.setProjectState(ProjectState.APPROVED);
        }
        em.getTransaction().commit();
        em.close();
    }

}
