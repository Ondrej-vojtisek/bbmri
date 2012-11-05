package bbmri.serviceImpl;

import bbmri.DAO.BiobankDAO;
import bbmri.DAO.ResearcherDAO;
import bbmri.DAOimpl.BiobankDAOImpl;
import bbmri.DAOimpl.ResearcherDAOImpl;
import bbmri.entities.Biobank;
import bbmri.entities.Researcher;
import bbmri.service.BiobankService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 20:03
 * To change this template use File | Settings | File Templates.
 */
public class BiobankServiceImpl implements BiobankService{

     EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
     BiobankDAO biobankDAO;

     private BiobankDAO getBiobankDAO(){
          if(biobankDAO == null){
            biobankDAO = new BiobankDAOImpl();
         }
        return biobankDAO;
     }
     ResearcherDAO researcherDAO;
     private ResearcherDAO getResearcherDAO(){
          if(researcherDAO == null){
            researcherDAO = new ResearcherDAOImpl();
         }
        return researcherDAO;
     }

    public Biobank create(Biobank biobank, Researcher researcher){
         EntityManager em = emf.createEntityManager();
         em.getTransaction().begin();
         Researcher resDB = getResearcherDAO().get(researcher.getId(), em);
         if(resDB != null){
            getBiobankDAO().create(biobank, em);
            biobank.setAdmin(resDB);
         }
         em.getTransaction().commit();
         em.close();
         return biobank;
    }

    public void remove(Long id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Biobank biobank = getBiobankDAO().get(id, em);
        if(biobank != null){
            getBiobankDAO().remove(biobank, em);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Biobank update(Biobank biobank){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        getBiobankDAO().update(biobank, em);
        em.getTransaction().commit();
        em.close();
        return biobank;
    }

    public List<Biobank> getAll(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Biobank> researchers = getBiobankDAO().getAll(em);
        em.getTransaction().commit();
        em.close();
        return researchers;
    }

}
