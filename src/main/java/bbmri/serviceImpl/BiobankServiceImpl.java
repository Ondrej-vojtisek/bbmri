package bbmri.serviceImpl;

import bbmri.dao.BiobankDao;
import bbmri.dao.UserDao;
import bbmri.entities.Biobank;
import bbmri.entities.Sample;
import bbmri.entities.User;
import bbmri.service.BiobankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class BiobankServiceImpl extends BasicServiceImpl implements BiobankService {

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private UserDao userDao;

    public Biobank create(Biobank biobank, Long administratorId) {
        notNull(biobank);
        notNull(administratorId);

        try {
            User adminDB = userDao.get(administratorId);
            if (adminDB != null) {
                biobankDao.create(biobank);
                adminDB.setBiobank(biobank);
                biobank.getAdministrators().add(adminDB);
                biobankDao.update(biobank);
            }
            return biobank;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void remove(Long id) {
        notNull(id);
        try {
            Biobank biobank = biobankDao.get(id);
            if (biobank != null) {
                List<User> administrators = biobank.getAdministrators();
                for(User user : administrators){
                    user.setBiobank(null);
                    userDao.update(user);
                }
                biobank.setAdministrators(null);
                biobankDao.update(biobank);
                biobankDao.remove(biobank);
            }
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    /*
    public void remove(Biobank biobank) {
          try {
            biobankDao.remove(biobank);
          } catch (DataAccessException ex) {
              throw ex;
          }
      }
      */

    public Biobank update(Biobank biobank) {
        notNull(biobank);

        try {
            Biobank biobankDB = biobankDao.get(biobank.getId());
            if(biobank.getAddress() != null){
                biobankDB.setAddress(biobank.getAddress());
            }
            if(biobank.getName() != null){
                            biobankDB.setName(biobank.getName());
            }
            biobankDao.update(biobankDB);
            return biobankDB;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Biobank> all() {
            return biobankDao.all();
    }

    public List<Sample> getAllSamples(Long biobankId) {
        notNull(biobankId);

        try {
            Biobank biobankDB = biobankDao.get(biobankId);
            if (biobankDB == null) {
                return null;
            }
            return biobankDB.getSamples();
        } catch (DataAccessException ex) {
            throw ex;
        }

    }

    public Integer count() {
        return biobankDao.count();
    }

    public User removeAdministratorFromBiobank(Long userId, Long biobankId) {
        notNull(userId);
        notNull(biobankId);
        try {
            User userDB = userDao.get(userId);
            Biobank biobankDB = biobankDao.get(biobankId);
            if (biobankDB.getAdministrators().contains(userDB)) {
                userDB.setBiobank(null);
                userDao.update(userDB);
            }
            return userDB;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<User> getAllAdministrators(Long biobankId) {
        notNull(biobankId);

        try {
            Biobank biobankDB = biobankDao.get(biobankId);
            /*List<User> users = userDao.all();
            List<User> results = new ArrayList<User>();


            for (User user : users) {
                if (user.getBiobank() != null) {
                    if (user.getBiobank().equals(biobankDB)) {
                        results.add(user);
                    }
                }
            }        */
            // results;
            return biobankDB.getAdministrators();
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Biobank changeOwnership(Long biobankId, Long newOwnerId) {
        notNull(biobankId);
        notNull(newOwnerId);

        Biobank biobank = biobankDao.get(biobankId);
        User newOwner = userDao.get(newOwnerId);
        User oldOwner = userDao.get(biobank.getOwner().getId());

        if (biobank.getAdministrators().contains(oldOwner)) {
            biobank.getAdministrators().remove(oldOwner);
            if (biobank.getAdministrators().contains(newOwner)) {
                biobank.getAdministrators().remove(newOwner);
            }
            biobankDao.update(biobank);
            biobank.getAdministrators().add(0, newOwner);
            biobank.getAdministrators().add(oldOwner);
            biobankDao.update(biobank);
        }
        return biobank;
    }

    public User assignAdministrator(Long userId, Long biobankId) {
        notNull(userId);
        notNull(biobankId);

        User userDB = userDao.get(userId);
        Biobank biobankDB = biobankDao.get(biobankId);
        if (userDB.getBiobank() == null) {
            userDB.setBiobank(biobankDB);
            userDao.update(userDB);
        }
        return userDB;
    }

    public Biobank get(Long id) {
        notNull(id);
        return biobankDao.get(id);
    }

}
