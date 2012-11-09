package bbmri.testing;

import bbmri.DAOimpl.ProjectDAOImpl;
import bbmri.DAOimpl.ResearcherDAOImpl;
import bbmri.entities.Project;

public class DAO_test {
    public static void main(String[] args) {


        /*
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        EntityManager em = emf.createEntityManager();

        Researcher res = new Researcher();
        Project pr1 = new Project();
        em.getTransaction().begin();

        res.setName("Ondra");
        pr1.setName("SpolecnyProjekt");

        em.persist(res);
        em.persist(pr1);

        res.getProjects().add(pr1);
        pr1.getResearchers().add(res);

        em.getTransaction().commit();
        em.close();

        em = emf.createEntityManager();
        em.getTransaction().begin();
        Researcher res2 = em.find(Researcher.class, res.getId());
        res2.getProjects().add(pr1);
        em.merge(res2);
        em.
        em.getTransaction().commit();
         */

        /*
ProjectDAOImpl projectDao = new ProjectDAOImpl();
ResearcherDAOImpl researcherDao = new ResearcherDAOImpl();

Project pr1 = new Project();
pr1.setName("Fantomasuv projekt hura1");
projectDao.createProject(pr1);
        */
        long id = 161;
        // researcherDao.addProject(res, pr1);

        //  em.close();
        //  emf.close();
    }
}




