package bbmri.serviceImpl;

import bbmri.dao.*;
import bbmri.entities.*;
import bbmri.service.BiobankService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BiobankServiceImpl extends BasicServiceImpl implements BiobankService {

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SampleDao sampleDao;

    @Autowired
    private SampleQuestionDao sampleQuestionDao;

    @Autowired
    private RequestGroupDao requestGroupDao;


    public Biobank create(Biobank biobank, Long administratorId) {
        notNull(biobank);
        notNull(administratorId);

        User adminDB = userDao.get(administratorId);
        if (adminDB != null) {
            biobankDao.create(biobank);
            assignAdministrator(adminDB.getId(), biobank.getId());
        }
        return biobank;
    }

    public void remove(Long id) {
        notNull(id);

        Biobank biobank = biobankDao.get(id);
        if (biobank != null) {
            List<User> administrators = biobank.getAdministrators();
            if (administrators != null) {
                for (User user : administrators) {
                    user.setBiobank(null);
                    userDao.update(user);
                }
            }
            List<Sample> samples = biobank.getSamples();
            if (samples != null) {
                for (Sample sample : samples) {
                    sampleDao.remove(sample);
                }
            }
            List<SampleQuestion> sampleQuestions = biobank.getSampleQuestions();
            if (sampleQuestions != null) {
                for (SampleQuestion sampleQuestion : sampleQuestions) {
                    sampleQuestionDao.remove(sampleQuestion);
                }
            }

            List<RequestGroup> requestGroups = biobank.getRequestGroups();
            if (requestGroups != null) {
                for (RequestGroup requestGroup : requestGroups) {
                    requestGroupDao.remove(requestGroup);
                }
            }

            biobank.setAdministrators(null);
            biobankDao.update(biobank);
            biobankDao.remove(biobank);
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

        Biobank biobankDB = biobankDao.get(biobank.getId());
        if (biobankDB != null) {
            if (biobank.getAddress() != null) {
                biobankDB.setAddress(biobank.getAddress());
            }
            if (biobank.getName() != null) {
                biobankDB.setName(biobank.getName());
            }
            biobankDao.update(biobankDB);
        }

        return biobankDB;
    }

    public List<Biobank> all() {
        return biobankDao.all();
    }

    /*
    public List<Sample> getAllSamples(Long biobankId) {
        notNull(biobankId);
        try {
            Biobank biobankDB = biobankDao.get(biobankId);
            if (biobankDB != null) {
                return biobankDB.getSamples();
            }
            return null;
        } catch (DataAccessException ex) {
            throw ex;
        }

    }
    */

    public Integer count() {
        return biobankDao.count();
    }

    public User removeAdministratorFromBiobank(Long userId, Long biobankId) {
        notNull(userId);
        notNull(biobankId);

        User userDB = userDao.get(userId);
        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB.getAdministrators().contains(userDB)) {
            userDB.setBiobank(null);
            userDao.update(userDB);
            biobankDB.getAdministrators().remove(userDB);
            biobankDao.update(biobankDB);
        }
        return userDB;
    }

    public User assignAdministrator(Long userId, Long biobankId) {
        notNull(userId);
        notNull(biobankId);
        User userDB = userDao.get(userId);
        Biobank biobankDB = biobankDao.get(biobankId);

        if (userDB != null && biobankDB != null) {

            if (userDB.getBiobank() == null) {
                userDB.setBiobank(biobankDB);
                biobankDB.getAdministrators().add(userDB);
                biobankDao.update(biobankDB);
            }
        }
        return userDB;
    }

    /*
    public List<User> getAllAdministrators(Long biobankId) {
        notNull(biobankId);

        try {
            Biobank biobankDB = biobankDao.get(biobankId);
            return biobankDB.getAdministrators();
        } catch (DataAccessException ex) {
            throw ex;
        }
    } */

    public Biobank changeOwnership(Long biobankId, Long newOwnerId) {
        notNull(biobankId);
        notNull(newOwnerId);

        Biobank biobank = biobankDao.get(biobankId);
        User newOwner = userDao.get(newOwnerId);
        User oldOwner = userDao.get(biobank.getOwner().getId());

        if (!biobank.getAdministrators().contains(oldOwner) || !biobank.getAdministrators().contains(newOwner)) {
            return null;
        }

        biobank.getAdministrators().remove(oldOwner);
        biobank.getAdministrators().remove(newOwner);
        biobankDao.update(biobank);
        biobank.getAdministrators().add(0, newOwner);
        biobank.getAdministrators().add(oldOwner);
        biobankDao.update(biobank);
        return biobank;
    }


    public Biobank get(Long id) {
        notNull(id);
        return biobankDao.get(id);
    }

}
