package bbmri.serviceImpl;

import bbmri.DAO.ResearcherDAO;
import bbmri.DAOimpl.ResearcherDAOImpl;
import bbmri.entities.Researcher;
import bbmri.service.ResearcherService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 13:05
 * To change this template use File | Settings | File Templates.
 */
public class ResearcherServiceImpl implements ResearcherService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
    ResearcherDAO researcherDAO;

    private ResearcherDAO getResearcherDAO() {
        if (researcherDAO == null) {
            researcherDAO = new ResearcherDAOImpl();
        }
        return researcherDAO;
    }

    public Researcher create(Researcher researcher) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        getResearcherDAO().create(researcher, em);
        em.getTransaction().commit();
        em.close();
        return researcher;
    }

    public void remove(Researcher researcher) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        getResearcherDAO().remove(researcher, em);
        em.getTransaction().commit();
        em.close();
    }

    public void remove(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Researcher researcher = getResearcherDAO().get(id, em);
        if (researcher != null) {
            getResearcherDAO().remove(researcher, em);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Researcher update(Researcher researcher) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Researcher res = getResearcherDAO().get(researcher.getId(),em);
        if(res == null){
            em.close();
            return null;
        }
        if(researcher.getName() != null) res.setName(researcher.getName());
        if(researcher.getSurname() != null) res.setSurname(researcher.getSurname());
        if(researcher.getPassword() != null) res.setPassword(researcher.getPassword());

        getResearcherDAO().update(researcher, em);
        em.getTransaction().commit();
        em.close();
        return researcher;
    }

    public List<Researcher> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Researcher> researchers = getResearcherDAO().getAll(em);
        em.getTransaction().commit();
        em.close();
        return researchers;
    }

    public Researcher getById(Long id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Researcher researcher = getResearcherDAO().get(id, em);
        em.getTransaction().commit();
        em.close();
        return researcher;
    }
}
