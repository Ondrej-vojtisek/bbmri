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

    @Autowired
    private BiobankDAO biobankDAO;

    @Autowired
    private UserDAO userDAO;

    public Biobank create(Biobank biobank, Long administratorId, Long ethicalCommitteeId) {
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
    }

    public void remove(Long id) {
        Biobank biobank = biobankDAO.get(id);
        if (biobank != null) {
            biobankDAO.remove(biobank);
        }
    }

    public Biobank update(Biobank biobank) {
        Biobank biobankDB = biobankDAO.get(biobank.getId());
        biobankDB.setAddress(biobank.getAddress());
        biobankDB.setName(biobank.getName());

        biobankDAO.update(biobankDB);
        return biobankDB;
    }

    public List<Biobank> getAll() {
        List<Biobank> biobanks = biobankDAO.getAll();
        return biobanks;
    }

    public Biobank updateAdministrator(Long biobankId, Long adminId) {

        Biobank biobankDB = biobankDAO.get(biobankId);
        User userDB = userDAO.get(adminId);
        biobankDB.setAdministrator(userDB);

        biobankDAO.update(biobankDB);

        return biobankDB;
    }

    public Biobank updateEthicalCommittee(Long biobankId, Long committeeId) {
        Biobank biobankDB = biobankDAO.get(biobankId);
        User user = userDAO.get(committeeId);
        biobankDB.setEthicalCommittee(user);

        biobankDAO.update(biobankDB);
        return biobankDB;
    }

    public List<Sample> getAllSamples(Long biobankId) {
        Biobank biobankDB = biobankDAO.get(biobankId);
        if (biobankDB != null) {
            return null;
        }

        return biobankDB.getSamples();

    }

}
