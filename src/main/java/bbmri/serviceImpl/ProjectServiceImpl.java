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
import java.util.ArrayList;
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

    private ResearcherDAO getResearcherDAO() {
        if (researcherDAO == null) {
            researcherDAO = new ResearcherDAOImpl();
        }
        return researcherDAO;
    }

    private ProjectDAO getProjectDAO() {
        if (projectDAO == null) {
            projectDAO = new ProjectDAOImpl();
        }
        return projectDAO;
    }

    public Project create(Project project, Researcher researcher) {
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

    public void remove(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Project project = getProjectDAO().get(id, em);
        if (project != null) {
            getProjectDAO().remove(project, em);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Project update(Project project) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Project projectDB = getProjectDAO().get(project.getId(), em);

        if (projectDB.getProjectState() != ProjectState.NEW &&
                project.getProjectState() != ProjectState.NEW &&
                project.getProjectState() != ProjectState.APPROVED) {
            projectDB.setProjectState(project.getProjectState());
        }
        projectDB.setDescription(project.getDescription());
        projectDB.setFundingOrganization(project.getFundingOrganization());
        projectDB.setName(project.getName());

        getProjectDAO().update(projectDB, em);
        em.getTransaction().commit();
        em.close();
        return project;
    }

    public List<Project> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Project> projects = getProjectDAO().getAll(em);
        em.getTransaction().commit();
        em.close();
        return projects;
    }

    public List<Project> getAllByResearcher(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Researcher researcher = getResearcherDAO().get(id, em);
        List<Project> projects = getProjectDAO().getAllByResearcher(researcher);
        em.getTransaction().commit();
        em.close();
        return projects;
    }

    public List<Project> getAllWhichResearcherAdministrate(Long id) {
        Researcher researcher;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        researcher = getResearcherDAO().get(id, em);
        List<Project> projects = getProjectDAO().getAllByResearcher(researcher);
        em.getTransaction().commit();
        em.close();

        List<Project> result = new ArrayList<Project>();
        if (projects != null) {
            for (Project project : projects) {
                if (project.getResearchers().get(0).equals(researcher)) {
                    result.add(project);
                }
            }
        }
        return result;
    }


    public List<Project> getAllByProjectState(ProjectState projectState) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Project> projects = getProjectDAO().getAllByProjectState(projectState, em);
        em.getTransaction().commit();
        em.close();
        return projects;
    }

    public List<Project> getAllApprovedByResearcher(Researcher researcher) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Project> projects = getProjectDAO().getAllByResearcher(researcher);
        em.close();
        List<Project> result = new ArrayList<Project>();
        if (projects != null) {
            for (Project res : projects) {
                if (res.getProjectState() == ProjectState.APPROVED ||
                        res.getProjectState() == ProjectState.STARTED) {
                    result.add(res);
                }
            }
        }
        return result;
    }


    public Researcher assignResearcher(Long researcherId, Long projectId) {
        Researcher researcherDB;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        researcherDB = getResearcherDAO().get(researcherId, em);
        Project projectDB = getProjectDAO().get(projectId, em);
        if (getProjectDAO().projectContainsResearcher(researcherDB, projectDB) == true) {
            em.close();
            return null;
        }
        getProjectDAO().assignResearcherToProject(researcherDB, projectDB);
        em.getTransaction().commit();
        em.close();
        return researcherDB;
    }

    public Researcher removeResearcherFromProject(Long researcherId, Long projectId) {
        Researcher researcherDB;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        researcherDB = getResearcherDAO().get(researcherId, em);
        Project projectDB = getProjectDAO().get(projectId, em);
        if (getProjectDAO().projectContainsResearcher(researcherDB, projectDB) == false) {
            em.close();
            return null;
        }
        getProjectDAO().removeResearcherFromProject(researcherDB, projectDB);
        em.getTransaction().commit();
        em.close();
        return researcherDB;
    }

    public List<Researcher> getAllAssignedResearchers(Long projectId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Project projectDB = getProjectDAO().get(projectId, em);
        List<Researcher> researchers = getProjectDAO().getAllResearchersByProject(projectDB);
        em.getTransaction().commit();
        em.close();
        return researchers;
    }

    public void approve(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Project projectDB = getProjectDAO().get(id, em);
        if (projectDB.getProjectState() == ProjectState.NEW) {
            projectDB.setProjectState(ProjectState.APPROVED);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Project getById(Long id) {
        Project project;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        project = getProjectDAO().get(id, em);
        em.getTransaction().commit();
        em.close();
        return project;
    }

    public List<Researcher> getAllNotAssignedResearchers(Long id) {
        List<Researcher> result = new ArrayList<Researcher>();

        Project project;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        project = getProjectDAO().get(id, em);
        List<Researcher> allResearchers = getResearcherDAO().getAll(em);
        em.getTransaction().commit();
        em.close();
        if (allResearchers != null) {
            for (Researcher res : allResearchers) {
                if (!res.getProjects().contains(project)) {
                    result.add(res);
                }
            }
        }
        return result;
    }

    public Project changeOwnership(Long projectId, Long newOwnerId) {

        Project project;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        project = getProjectDAO().get(projectId, em);
        Researcher researcher = getResearcherDAO().get(newOwnerId, em);

        Researcher old = getResearcherDAO().get(project.getOwner().getId(), em);

        if (project.getResearchers().contains(researcher)) {
            project.getResearchers().remove(old);
            getProjectDAO().update(project, em);
            project.getResearchers().remove(researcher);
            project.getResearchers().add(0, researcher);
            project.getResearchers().add(old);
            getProjectDAO().update(project, em);
        }
        em.getTransaction().commit();
        em.close();
        return project;
    }
}
