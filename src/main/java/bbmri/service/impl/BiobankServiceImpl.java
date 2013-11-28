package bbmri.service.impl;

import bbmri.dao.*;
import bbmri.entities.*;
import bbmri.entities.enumeration.Permission;
import bbmri.entities.enumeration.SystemRole;
import bbmri.service.BiobankService;
import bbmri.service.exceptions.LastManagerException;
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
@Service
public class BiobankServiceImpl extends BasicServiceImpl implements BiobankService {

    /*
    Dodat:
    @Override
    @Transactional(readOnly = true)
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


    public Biobank create(Biobank biobank, Long newAdministratorId) {
        notNull(biobank);
        notNull(newAdministratorId);

        User adminDB = userDao.get(newAdministratorId);
        if (adminDB == null) {
            return null;
            // TODO: exception
        }

        biobankDao.create(biobank);
        assignAdministrator(biobank, newAdministratorId, Permission.MANAGER);
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

        Set<BiobankAdministrator> biobankAdministrators = biobank.getBiobankAdministrators();
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

    public void removeAdministrator(BiobankAdministrator objectAdministrator) throws LastManagerException {
        notNull(objectAdministrator);

        User userDB = objectAdministrator.getUser();
        Biobank biobankDB = objectAdministrator.getBiobank();

        if(userDB == null || biobankDB == null){
            return;
            // TODO: EXCEPTION
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
    }

    public void assignAdministrator(Biobank object, Long userId, Permission permission){
        notNull(object);
        notNull(userId);
        notNull(permission);

        User userDB = userDao.get(userId);
        if(userDB == null){
             return;
             // TODO: exception
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
    }


    private boolean isLastManager(BiobankAdministrator ba){
        if(!ba.getPermission().equals(Permission.MANAGER)){
            return false;
        }

        if(biobankAdministratorDao.get(ba.getBiobank(), Permission.MANAGER).size() > 1){
            return false;
        }

        return true;
    }

    public void changeAdministratorPermission(BiobankAdministrator ba, Permission permission) throws LastManagerException {
        notNull(ba);
        notNull(permission);

        /* Situation when we want to remove last manager. */

        if (! permission.equals(Permission.MANAGER) && isLastManager(ba) ) {
            throw new LastManagerException("User: " + ba.getUser().getWholeName()
                    + " is the only administrator with MANAGER permission associated to biobank: "
                    + ba.getBiobank().getName() + ". He can't be removed!");
        }

        ba.setPermission(permission);
        biobankAdministratorDao.update(ba);
    }

//    public void removeAdministrator(Long userId, Long biobankId, Permission permission) {
//        notNull(userId);
//        notNull(biobankId);
//        notNull(permission);
//
//        User userDB = userDao.get(userId);
//        Biobank biobankDB = biobankDao.get(biobankId);
//
//        if (userDB == null || biobankDB == null) {
//            return;
//            // TODO: exception
//        }
//
//        removeAdministratorFromBiobank(userId, biobankId);
//    }

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
