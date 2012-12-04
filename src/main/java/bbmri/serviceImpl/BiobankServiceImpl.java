package bbmri.serviceImpl;

import bbmri.DAO.BiobankDAO;
import bbmri.DAO.UserDAO;
import bbmri.DAOimpl.BiobankDAOImpl;
import bbmri.DAOimpl.UserDAOImpl;
import bbmri.entities.Biobank;
import bbmri.entities.User;
import bbmri.entities.Sample;
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
public class BiobankServiceImpl implements BiobankService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
    BiobankDAO biobankDAO;

    private BiobankDAO getBiobankDAO() {
        if (biobankDAO == null) {
            biobankDAO = new BiobankDAOImpl();
        }
        return biobankDAO;
    }

    UserDAO userDAO;

    private UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAOImpl();
        }
        return userDAO;
    }

    public Biobank create(Biobank biobank, Long administratorId, Long ethicalCommitteeId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User adminDB = getUserDAO().get(administratorId, em);
        if (adminDB != null) {
            getBiobankDAO().create(biobank, em);
            biobank.setAdministrator(adminDB);
        }
        User committeeDB = getUserDAO().get(ethicalCommitteeId, em);
        if (committeeDB != null) {
            biobank.setEthicalCommittee(committeeDB);
        }
        em.getTransaction().commit();
        em.close();
        return biobank;
    }

    public void remove(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Biobank biobank = getBiobankDAO().get(id, em);
        if (biobank != null) {
            getBiobankDAO().remove(biobank, em);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Biobank update(Biobank biobank) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Biobank biobankDB = getBiobankDAO().get(biobank.getId(), em);
        biobankDB.setAddress(biobank.getAddress());
        biobankDB.setName(biobank.getName());

        getBiobankDAO().update(biobankDB, em);
        em.getTransaction().commit();
        em.close();
        return biobankDB;
    }

    public List<Biobank> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Biobank> biobanks = getBiobankDAO().getAll(em);
        em.getTransaction().commit();
        em.close();
        return biobanks;
    }

    public Biobank updateAdministrator(Long biobankId, Long adminId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Biobank biobankDB = getBiobankDAO().get(biobankId, em);
        User user = getUserDAO().get(adminId, em);
        biobankDB.setAdministrator(user);

        getBiobankDAO().update(biobankDB, em);
        em.getTransaction().commit();
        em.close();
        return biobankDB;
    }

    public Biobank updateEthicalCommittee(Long biobankId, Long committeeId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Biobank biobankDB = getBiobankDAO().get(biobankId, em);
        User user = getUserDAO().get(committeeId, em);
        biobankDB.setEthicalCommittee(user);

        getBiobankDAO().update(biobankDB, em);
        em.getTransaction().commit();
        em.close();
        return biobankDB;
    }

    public List<Sample> getAllSamples(Long biobankId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Biobank biobankDB = getBiobankDAO().get(biobankId, em);
        if (biobankDB != null) {
            em.close();
            return null;
        }
        em.close();

        return biobankDB.getSamples();

    }

}
