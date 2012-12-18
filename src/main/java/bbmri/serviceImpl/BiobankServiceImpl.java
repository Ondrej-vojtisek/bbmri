package bbmri.serviceImpl;

import bbmri.DAO.BiobankDAO;
import bbmri.DAO.UserDAO;
import bbmri.entities.Biobank;
import bbmri.entities.Sample;
import bbmri.entities.User;
import bbmri.service.BiobankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    @Autowired
    private BiobankDAO biobankDAO;

    @Autowired
    private UserDAO userDAO;

    public Biobank create(Biobank biobank, Long administratorId, Long ethicalCommitteeId) {
        try {
            User adminDB = userDAO.get(administratorId);
            if (adminDB != null) {
                biobankDAO.create(biobank);
                biobank.setAdministrator(adminDB);
            }

            User committeeDB = userDAO.get(ethicalCommitteeId);
            if (committeeDB != null) {
                biobank.setEthicalCommittee(committeeDB);
            }
            return biobank;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void remove(Long id) {
        try {
            Biobank biobank = biobankDAO.get(id);
            if (biobank != null) {
                biobankDAO.remove(biobank);
            }
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Biobank update(Biobank biobank) {
        try {
            Biobank biobankDB = biobankDAO.get(biobank.getId());
            biobankDB.setAddress(biobank.getAddress());
            biobankDB.setName(biobank.getName());

            biobankDAO.update(biobankDB);
            return biobankDB;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Biobank> getAll() {
        try {
            List<Biobank> biobanks = biobankDAO.getAll();
            return biobanks;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Biobank updateAdministrator(Long biobankId, Long adminId) {
        try {
            Biobank biobankDB = biobankDAO.get(biobankId);
            User userDB = userDAO.get(adminId);
            biobankDB.setAdministrator(userDB);

            biobankDAO.update(biobankDB);

            return biobankDB;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Biobank updateEthicalCommittee(Long biobankId, Long committeeId) {
        try {
            Biobank biobankDB = biobankDAO.get(biobankId);
            User user = userDAO.get(committeeId);
            biobankDB.setEthicalCommittee(user);

            biobankDAO.update(biobankDB);
            return biobankDB;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Sample> getAllSamples(Long biobankId) {
        try {
            Biobank biobankDB = biobankDAO.get(biobankId);
            if (biobankDB != null) {
                return null;
            }

            return biobankDB.getSamples();
        } catch (DataAccessException ex) {
            throw ex;
        }

    }

    public Integer getCount() {
        try {
            return biobankDAO.getCount();
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

}
