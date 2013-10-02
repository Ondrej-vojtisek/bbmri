package bbmri.serviceImpl;

import bbmri.dao.*;
import bbmri.entities.*;
import bbmri.entities.enumeration.Permission;
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

    @Autowired
    private BiobankAdministratorDao biobankAdministratorDao;


    public Biobank create(Biobank biobank, Long administratorId) {
        notNull(biobank);
        notNull(administratorId);

        User adminDB = userDao.get(administratorId);
        if (adminDB == null) {
            return null;
            // TODO: exception
        }

        biobankDao.create(biobank);
        assignAdministrator(adminDB.getId(), biobank.getId(), Permission.MANAGER);
        return biobank;
    }

    public void remove(Long id) {
        notNull(id);

        logger.debug("Debug " + id);

        Biobank biobank = biobankDao.get(id);
        if (biobank == null) {
            return;
            //TODO: exception
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

        List<BiobankAdministrator> biobankAdministrators = biobank.getBiobankAdministrators();
        if (biobankAdministrators != null) {
            for (BiobankAdministrator ba : biobankAdministrators) {
                ba.setUser(null);
                ba.setBiobank(null);
                biobankAdministratorDao.remove(ba);
            }
        }

        biobankDao.remove(biobank);

    }

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

    public Integer count() {
        return biobankDao.count();
    }

    public void removeAdministratorFromBiobank(Long biobankId, Long userId) {
        notNull(biobankId);
        notNull(userId);

        Biobank biobankDB = biobankDao.get(biobankId);
        User userDB = userDao.get(userId);

        if(biobankDB == null || userDB == null){
            return;
            // TODO: Exception
        }
        BiobankAdministrator ba = biobankAdministratorDao.get(biobankDB, userDB);

        if (ba == null) {
            return;
            // TODO: exception
        }
        if (biobankDB.getBiobankAdministrators().size() == 1) {
            return;
            // TODO: exception
        }
        biobankAdministratorDao.remove(ba);
    }

    public User assignAdministrator(Long userId, Long biobankId, Permission permission) {
        notNull(userId);
        notNull(biobankId);
        notNull(permission);
        User userDB = userDao.get(userId);
        Biobank biobankDB = biobankDao.get(biobankId);

        if (userDB == null || biobankDB == null) {
            return null;
            // TODO: exception
        }

        BiobankAdministrator ba = new BiobankAdministrator();
        ba.setPermission(permission);
        ba.setBiobank(biobankDB);
        ba.setUser(userDB);

        biobankAdministratorDao.create(ba);

        /* TODO: check if this is necessary */
        biobankDB.getBiobankAdministrators().add(ba);
        biobankDao.update(biobankDB);

        userDB.getBiobankAdministrators().add(ba);
        userDao.update(userDB);
        return userDB;
    }

    private int biobankManagerCount(Biobank biobank) {
        notNull(biobank);
        int count = 0;
        for (BiobankAdministrator ba : biobank.getBiobankAdministrators()) {
            if (ba.getPermission().equals(Permission.MANAGER)) {
                count++;
            }
        }
        return count;
    }


    public void changeAdministratorPermission(Long loggedUserId, Long userId, Long biobankId, Permission permission) {
        notNull(userId);
        notNull(biobankId);
        notNull(loggedUserId);
        notNull(permission);

        User userDB = userDao.get(userId);
        User loggedUser = userDao.get(loggedUserId);
        Biobank biobankDB = biobankDao.get(biobankId);

        if (userDB == null || loggedUser == null || biobankDB == null) {
            return;
            // TODO: exception
        }

        if(!biobankAdministratorDao.contains(biobankDB, loggedUser)){
            return;
            // TODO: exception - You are not administrator of this biobank
        }
        if(!biobankAdministratorDao.get(biobankDB, loggedUser).getPermission().equals(Permission.MANAGER)){
            return;
            // TODO: exception - You don't have sufficient rights
        }
        if (userDB.equals(loggedUser) && biobankManagerCount(biobankDB) == 1) {
            return;
            // TODO: exception - You are last admin so you can't lower your permissions
        }
        assignAdministrator(userId, biobankId, permission);

    }

    public void removeAdministrator(Long loggedUserId, Long userId, Long biobankId, Permission permission) {
        notNull(userId);
        notNull(biobankId);
        notNull(loggedUserId);
        notNull(permission);

        User userDB = userDao.get(userId);
        User loggedUser = userDao.get(loggedUserId);
        Biobank biobankDB = biobankDao.get(biobankId);

        if (userDB == null || loggedUser == null || biobankDB == null) {
            return;
            // TODO: exception
        }

        if(!biobankAdministratorDao.contains(biobankDB, loggedUser)){
            return;
            // TODO: exception - You are not administrator of this biobank
        }

        if(!biobankAdministratorDao.get(biobankDB, loggedUser).getPermission().equals(Permission.MANAGER)){
            return;
            // TODO: exception - You don't have sufficient rights
        }
        if (userDB.equals(loggedUser) && biobankManagerCount(biobankDB) == 1) {
            return;
            // TODO: exception - You are last admin so you can't lower your permissions
        }
        removeAdministratorFromBiobank(userId, biobankId);
    }

    public Biobank get(Long id) {
        notNull(id);
        return biobankDao.get(id);
    }

    public Biobank eagerGet(Long id, boolean samples, boolean requestGroups, boolean sampleQuestions) {
        notNull(id);
        Biobank biobankDB = biobankDao.get(id);

        /* Not only comments - this force hibernate to load mentioned relationship from db. Otherwise it wont be accessible from presentational layer of application.*/

        if (samples) {
            logger.debug("" + biobankDB.getSamples());
        }

        if (requestGroups) {
            logger.debug("" + biobankDB.getRequestGroups());
        }

        if (sampleQuestions) {
            logger.debug("" + biobankDB.getSampleQuestions());
        }

        return biobankDB;

    }

}
