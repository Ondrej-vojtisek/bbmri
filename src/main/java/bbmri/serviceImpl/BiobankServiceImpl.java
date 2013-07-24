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

    public Biobank create(Biobank biobank, Long administratorId) {
        try {
            User adminDB = userDAO.get(administratorId);
            if (adminDB != null) {
                biobankDAO.create(biobank);
                adminDB.setBiobank(biobank);
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

    public User removeAdministratorFromBiobank(Long userId, Long biobankId){
        try {
            User userDB = userDAO.get(userId);
            Biobank biobankDB = biobankDAO.get(biobankId);
            if(userDB.getBiobank().equals(biobankDB)){
                userDB.setBiobank(null);
                userDAO.update(userDB);
            }
            return userDB;
           } catch (DataAccessException ex) {
               throw ex;
           }
    }

    public List<User> getAllAdministrators(Long biobankId) {
          Biobank biobankDB = biobankDAO.get(biobankId);
          return  userDAO.getAllAdministratorsOfBiobank(biobankDB);
      }

    public Biobank changeOwnership(Long biobankId, Long newOwnerId) {
          Biobank biobank = biobankDAO.get(biobankId);
          User newOwner = userDAO.get(newOwnerId);
          User oldOwner = userDAO.get(biobank.getOwner().getId());

          if (biobank.getAdministrators().contains(newOwner)) {
              biobank.getAdministrators().remove(oldOwner);
              biobank.getAdministrators().remove(newOwner);
              biobankDAO.update(biobank);
              biobank.getAdministrators().add(0, newOwner);
              biobank.getAdministrators().add(oldOwner);
              biobankDAO.update(biobank);
          }
          return biobank;
      }

    public User assignAdministrator(Long userId, Long biobankId) {
           User userDB = userDAO.get(userId);
           Biobank biobankDB = biobankDAO.get(biobankId);
           if(userDB.getBiobank() == null){
               userDB.setBiobank(biobankDB);
               userDAO.update(userDB);
           }
           return userDB;
       }

    public Biobank getById(Long id) {
          try {
              Biobank biobankDB = biobankDAO.get(id);
              return biobankDB;
          } catch (DataAccessException ex) {
              throw ex;
          }
      }

}
