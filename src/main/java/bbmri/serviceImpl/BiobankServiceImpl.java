package bbmri.serviceImpl;

import bbmri.DAO.BiobankDAO;
import bbmri.DAO.UserDAO;
import bbmri.entities.Biobank;
import bbmri.entities.Sample;
import bbmri.entities.User;
import bbmri.service.BiobankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
@Service
public class BiobankServiceImpl implements BiobankService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");

    @Autowired
    private BiobankDAO biobankDAO;

    @Autowired
    private UserDAO userDAO;

    public Biobank create(Biobank biobank, Long administratorId, Long ethicalCommitteeId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User adminDB = userDAO.get(administratorId, em);
        if (adminDB != null) {
            biobankDAO.create(biobank, em);
            biobank.setAdministrator(adminDB);
        }

        System.out.println("AdminDB: " + adminDB.toString());

        User committeeDB = userDAO.get(ethicalCommitteeId, em);
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
        Biobank biobank = biobankDAO.get(id, em);
        if (biobank != null) {
            biobankDAO.remove(biobank, em);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Biobank update(Biobank biobank) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Biobank biobankDB = biobankDAO.get(biobank.getId(), em);
        biobankDB.setAddress(biobank.getAddress());
        biobankDB.setName(biobank.getName());

        biobankDAO.update(biobankDB, em);
        em.getTransaction().commit();
        em.close();
        return biobankDB;
    }

    public List<Biobank> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Biobank> biobanks = biobankDAO.getAll(em);
        em.getTransaction().commit();
        em.close();
        return biobanks;
    }

    public Biobank updateAdministrator(Long biobankId, Long adminId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Biobank biobankDB = biobankDAO.get(biobankId, em);
        User userDB = userDAO.get(adminId, em);
        biobankDB.setAdministrator(userDB);

        biobankDAO.update(biobankDB, em);
        em.getTransaction().commit();
        em.close();
        return biobankDB;
    }

    public Biobank updateEthicalCommittee(Long biobankId, Long committeeId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Biobank biobankDB = biobankDAO.get(biobankId, em);
        User user = userDAO.get(committeeId, em);
        biobankDB.setEthicalCommittee(user);

        biobankDAO.update(biobankDB, em);
        em.getTransaction().commit();
        em.close();
        return biobankDB;
    }

    public List<Sample> getAllSamples(Long biobankId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Biobank biobankDB = biobankDAO.get(biobankId, em);
        if (biobankDB != null) {
            em.close();
            return null;
        }
        em.close();

        return biobankDB.getSamples();

    }

}
