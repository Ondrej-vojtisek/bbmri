package bbmri.serviceImpl;

import bbmri.DAO.ResearcherDAO;
import bbmri.DAOimpl.ResearcherDAOImpl;
import bbmri.entities.Researcher;
import bbmri.service.LoginService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
public class LoginServiceImpl implements LoginService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
    ResearcherDAO researcherDAO;

    private ResearcherDAO getResearcherDAO() {
        if (researcherDAO == null) {
            researcherDAO = new ResearcherDAOImpl();
        }
        return researcherDAO;
    }

    // temporal prosthesis
    public Researcher login(Long id, String password) {
        if (password == null || id < 0) {
            return null;
        }
        boolean result = false;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Researcher researcher = getResearcherDAO().get(id, em);
        if (researcher != null && researcher.getPassword() != null) {
            if ((researcher.getPassword()).equals(password)) {
                result = true;
                researcher.setOnline(true);
                getResearcherDAO().update(researcher, em);
                em.getTransaction().commit();
            }

        }
        em.close();
        if (result) {
            return researcher;
        }
        return null;
    }

    public void logout(Researcher researcher) {
        if (researcher == null) {
            return;
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        researcher.setOnline(false);
        getResearcherDAO().update(researcher, em);
        em.getTransaction().commit();
        em.close();
    }
}
