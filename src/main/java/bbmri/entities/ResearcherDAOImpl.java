package bbmri.entities;


import com.sun.org.apache.regexp.internal.RE;

import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class ResearcherDAOImpl implements ResearcherDAO{

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");

        public void addResearcher(Researcher researcher){
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(researcher);
            em.getTransaction().commit();
            em.close();
        }

        public void removeResearcher(Researcher researcher){
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(researcher);
            em.remove(researcher);
            em.getTransaction().commit();
            em.close();
        }

        public void removeResearcher(long id){
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(Researcher.class, id));
            em.getTransaction().commit();
            em.close();
        }

        public void updateResearcher(Researcher researcher){
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(researcher);
            Researcher res =  em.find(Researcher.class, researcher.getId());
            if(researcher.getName() != null){
                res.setName(researcher.getName());
            }
             if(researcher.getBiobanks() != null){
                res.setBiobanks(researcher.getBiobanks());
            }
            if(researcher.getSurname() != null){
                res.setSurname(researcher.getSurname());
            }
            if(researcher.getProjects() != null){
                res.setProjects(researcher.getProjects());
            }
            em.getTransaction().commit();
            em.close();
        }

         public List<Researcher> getAllResearchers(){
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT p FROM Researcher p");
            List<Researcher> results = query.getResultList();
            em.close();
            return results;
        }

         public Researcher getResearcher(long id){
            Researcher researcher;
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            researcher = em.find(Researcher.class, id);
            em.close();
            return researcher;
        }

         // temporal
         public boolean verifyPassword(String password, long id){
            if(password == null || id < 0)
                return false;

            boolean result = false;
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            Researcher researcher = em.find(Researcher.class, id);
            if(researcher != null && researcher.getPassword() != null){
                result = (researcher.getPassword()).equals(password);
            }
            em.close();
            return result;
         }
}
