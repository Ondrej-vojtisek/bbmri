package bbmri.entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class ProjectDAOImpl {

      EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");

    public void addProject(Project project) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(project);
        em.getTransaction().commit();
        em.close();
    }

    public void removeProject(Project project) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(project);
        em.remove(project);
        em.getTransaction().commit();
        em.close();
    }

    public void removeProject(long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Project.class, id));
        em.getTransaction().commit();
        em.close();
    }

    public void updateProject(Project project) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Project res = em.find(Project.class, project.getId());

        if (project.getName() != null) {
            res.setName(project.getName());
        }

         if (project.getDescription() != null) {
            res.setDescription(project.getDescription());
        }
        if(project.getState() != null){
            res.setState(project.getState());
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<Project> getAllProjects() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT p FROM Project p");
        List<Project> results = query.getResultList();
        em.close();
        return results;
    }

    public Project getProject(long id) {
        Project project;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        project = em.find(Project.class, id);
        em.close();
        return project;
    }


}
