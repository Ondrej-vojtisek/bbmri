package cz.bbmri.service.impl;

import cz.bbmri.dao.*;
import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.service.BiobankService;
import cz.bbmri.service.exceptions.DuplicitBiobankException;
import cz.bbmri.service.exceptions.LastManagerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 20:03
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("biobankService")
public class BiobankServiceImpl extends BasicServiceImpl implements BiobankService {

    /*
    TODO
    Dodat:
    @Override
    @Transactional(readOnly = true)

    @SpringBean zamenit misto @Autowired
    Pokud se nelinkne tak @Repository(biobankDao)
     */

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


    public Biobank create(Biobank biobank, Long newAdministratorId) throws DuplicitBiobankException {
        notNull(biobank);
        notNull(newAdministratorId);

        User adminDB = userDao.get(newAdministratorId);
        if (adminDB == null) {
            logger.debug("Object retrieved from database is null - adminBD");
            return null;
        }

        if (biobankDao.getBiobankByName(biobank.getName()) != null) {
            throw new DuplicitBiobankException("Biobank with name: "
                    + biobank.getName() + " already exists! Name must be unique.");
        }

        biobankDao.create(biobank);
        assignAdministrator(biobank, newAdministratorId, Permission.MANAGER);

        return biobank;
    }

    public boolean remove(Long id) {
        notNull(id);

        logger.debug("Debug " + id);

        Biobank biobankDB = biobankDao.get(id);
        if (biobankDB == null) {
            logger.debug("Object retrieved from database is null - biobankDB");
            return false;
        }

        List<Sample> samples = biobankDB.getSamples();
        if (samples != null) {
            for (Sample sample : samples) {
                sampleDao.remove(sample);
            }
        }
        List<SampleQuestion> sampleQuestions = biobankDB.getSampleQuestions();
        if (sampleQuestions != null) {
            for (SampleQuestion sampleQuestion : sampleQuestions) {
                sampleQuestionDao.remove(sampleQuestion);
            }
        }

        List<RequestGroup> requestGroups = biobankDB.getRequestGroups();
        if (requestGroups != null) {
            for (RequestGroup requestGroup : requestGroups) {
                requestGroupDao.remove(requestGroup);
            }
        }

        Set<BiobankAdministrator> biobankAdministrators = biobankDB.getBiobankAdministrators();
        if (biobankAdministrators != null) {
            for (BiobankAdministrator ba : biobankAdministrators) {

                /* Remove system role biobank operator */
                User userDB = ba.getUser();
                if (userDB.getBiobankAdministrators().size() == 1 &&
                        userDB.getSystemRoles().contains(SystemRole.BIOBANK_OPERATOR)) {

                    userDB.getSystemRoles().remove(SystemRole.BIOBANK_OPERATOR);
                    userDao.update(userDB);
                }

                ba.setUser(null);
                ba.setBiobank(null);
                biobankAdministratorDao.remove(ba);
            }
        }

        biobankDao.remove(biobankDB);
        return true;
    }

    public Biobank update(Biobank biobank) {
        notNull(biobank);

        Biobank biobankDB = biobankDao.get(biobank.getId());

        if (biobankDB == null) {
            logger.debug("Object retrieved from database is null - biobankDB");
            return null;
        }

        if (biobank.getAddress() != null) {
            biobankDB.setAddress(biobank.getAddress());
        }
        if (biobank.getName() != null) {
            biobankDB.setName(biobank.getName());
        }
        biobankDao.update(biobankDB);

        return biobankDB;
    }

    @Transactional(readOnly = true)
    public List<Biobank> all() {
        return biobankDao.all();
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return biobankDao.count();
    }

    public boolean removeAdministrator(BiobankAdministrator objectAdministrator) throws LastManagerException {
        notNull(objectAdministrator);

        User userDB = objectAdministrator.getUser();
        Biobank biobankDB = objectAdministrator.getBiobank();

        if (userDB == null || biobankDB == null) {
            logger.debug("Object retrieved from database is null - userBD or biobankDB");
            return false;
        }

        /* Situation when we want to remove last manager. */
        if (isLastManager(objectAdministrator)) {
            throw new LastManagerException("User: " + userDB.getWholeName()
                    + " is the only administrator with MANAGER permission associated to biobank: "
                    + biobankDB.getName() + ". He can't be removed!");
        }

        if (userDB.getBiobankAdministrators().size() == 1 &&
                userDB.getSystemRoles().contains(SystemRole.BIOBANK_OPERATOR)) {
            userDB.getSystemRoles().remove(SystemRole.BIOBANK_OPERATOR);
            userDao.update(userDB);
        }

        biobankAdministratorDao.remove(objectAdministrator);
        return true;
    }

    public boolean assignAdministrator(Biobank object, Long userId, Permission permission) {
        notNull(object);
        notNull(userId);
        notNull(permission);

        User userDB = userDao.get(userId);
        if (userDB == null) {
            logger.debug("Object retrieved from database is null - userBD");
            return false;
        }

        BiobankAdministrator ba = new BiobankAdministrator();
        ba.setPermission(permission);
        ba.setBiobank(object);
        ba.setUser(userDB);

        biobankAdministratorDao.create(ba);

        if (!userDB.getSystemRoles().contains(SystemRole.BIOBANK_OPERATOR)) {
            userDB.getSystemRoles().add(SystemRole.BIOBANK_OPERATOR);
        }

        userDao.update(userDB);
        return true;
    }

    @Transactional(readOnly = true)
    public boolean isLastManager(BiobankAdministrator objectAdministrator) {
        if (!objectAdministrator.getPermission().equals(Permission.MANAGER)) {
            return false;
        }

        if (biobankAdministratorDao.get(objectAdministrator.getBiobank(), Permission.MANAGER).size() > 1) {
            return false;
        }

        return true;
    }

    public boolean changeAdministratorPermission(BiobankAdministrator ba, Permission permission) throws LastManagerException {
        notNull(ba);
        notNull(permission);

        /* Situation when we want to remove last manager. */

        if (!permission.equals(Permission.MANAGER) && isLastManager(ba)) {
            throw new LastManagerException("User: " + ba.getUser().getWholeName()
                    + " is the only administrator with MANAGER permission associated to biobank: "
                    + ba.getBiobank().getName() + ". He can't be removed!");
        }

        ba.setPermission(permission);
        biobankAdministratorDao.update(ba);
        return true;
    }

    @Transactional(readOnly = true)
    public Biobank get(Long id) {
        notNull(id);
        return biobankDao.get(id);
    }

    @Transactional(readOnly = true)
    public Biobank eagerGet(Long id, boolean samples, boolean requestGroups, boolean sampleQuestions) {
        notNull(id);
        Biobank biobankDB = biobankDao.get(id);

        if (biobankDB == null) {
            logger.debug("Object retrieved from database is null - biobankDB");
            return null;
        }

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

    @Transactional(readOnly = true)
    public List<Biobank> allOrderedBy(String orderByParam, boolean desc) {
        return biobankDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<Biobank> nOrderedBy(String orderByParam, boolean desc, int number) {
        return biobankDao.nOrderedBy(orderByParam, desc, number);
    }

}
