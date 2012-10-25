package bbmri.entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        EntityManager em = emf.createEntityManager();

        Researcher res = new Researcher();
        Researcher res2 = new Researcher();
        Project pr1 = new Project();
        Project pr2 = new Project();
        Project pr3 = new Project();
        Biobank bio1 = new Biobank();
        Biobank bio2 = new Biobank();

        em.getTransaction().begin();

        res.setName("Ondra");
        res2.setName("Pepa");

        pr1.setName("SpolecnyProjekt");
        pr2.setName("OndruvProjekt");
        pr3.setName("PepuvProjekt");

        bio1.getAdmins().add(res);
        bio1.getAdmins().add(res2);

        res.getBiobanks().add(bio1);
        res2.getBiobanks().add(bio1);

        bio1.setAddress("MOU");

        em.persist(res);
        em.persist(res2);
        em.persist(pr1);
        em.persist(pr2);
        em.persist(pr3);
        em.persist(bio1);
        em.persist(bio2);

        res.getProjects().add(pr1);
        pr1.getResearchers().add(res);

        res2.getProjects().add(pr1);
        pr1.getResearchers().add(res2);

        res.getProjects().add(pr2);
        pr2.getResearchers().add(res);

        res2.getProjects().add(pr3);
        pr3.getResearchers().add(res2);

        em.getTransaction().commit();

        em.close();
        emf.close();
    }
}




