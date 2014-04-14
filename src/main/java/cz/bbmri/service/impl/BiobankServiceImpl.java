package cz.bbmri.service.impl;

import cz.bbmri.dao.*;
import cz.bbmri.entities.*;
import cz.bbmri.entities.constant.Constant;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.service.BiobankService;
import cz.bbmri.service.exceptions.DuplicitBiobankException;
import cz.bbmri.service.exceptions.LastManagerException;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
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

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SampleDao sampleDao;

    @Autowired
    private SampleQuestionDao sampleQuestionDao;

    @Autowired
    private BiobankAdministratorDao biobankAdministratorDao;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private NotificationDao notificationDao;


    public Biobank create(Biobank biobank, Long newAdministratorId) throws DuplicitBiobankException {
        notNull(biobank);
        notNull(newAdministratorId);

        User adminDB = userDao.get(newAdministratorId);
        if (adminDB == null) {
            logger.debug("Object retrieved from database is null - adminBD");
            return null;
        }

        if (biobankDao.getBiobankByAbbreviation(biobank.getAbbreviation()) != null) {
            throw new DuplicitBiobankException("Biobank with abbreviation: "
                    + biobank.getAbbreviation() + " already exists! Abbreviation must be unique.");
        }

        biobankDao.create(biobank);
        assignAdministrator(biobank, newAdministratorId, Permission.MANAGER);

        logger.debug("CreateBiobank Service - Biobank: " + biobank);

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

        List<Patient> patients = biobankDB.getPatients();
        if (patients != null) {
            for (Patient patient : patients) {
                patientDao.remove(patient);
            }
        }
        List<SampleQuestion> sampleQuestions = biobankDB.getSampleQuestions();
        if (sampleQuestions != null) {
            for (SampleQuestion sampleQuestion : sampleQuestions) {
                sampleQuestionDao.remove(sampleQuestion);
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

        // Abbreativion is final

        if (biobank.getStreet() != null) {
            biobankDB.setStreet(biobank.getStreet());
        }
        if (biobank.getCity() != null) {
            biobankDB.setCity(biobank.getCity());
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
    public List<Biobank> allOrderedBy(String orderByParam, boolean desc) {
        return biobankDao.allOrderedBy(orderByParam, desc);
    }

    public Biobank getBiobankByAbbreviation(String abbreviation) {
        return biobankDao.getBiobankByAbbreviation(abbreviation);
    }

    public boolean assignAdministrator(Long objectId,
                                       Long newAdministratorId,
                                       Permission permission,
                                       ValidationErrors errors,
                                       Long loggedUserId) {
        notNull(objectId);
        notNull(newAdministratorId);
        notNull(permission);
        notNull(errors);
        notNull(loggedUserId);

        Biobank biobankDB = biobankDao.get(objectId);
        User newAdmin = userDao.get(newAdministratorId);

        if (biobankDB == null || newAdmin == null) {
            fatalError(errors);
            return false;
        }

        if (biobankAdministratorDao.get(biobankDao.get(objectId), userDao.get(newAdministratorId)) != null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.adminAlreadyExists"));
            return false;
        }

        boolean result = false;

        try {
            result = assignAdministrator(biobankDB, newAdministratorId, permission);
        } catch (Exception ex) {
            fatalError(errors);
            return false;
        }

        if (result) {

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.BiobankFacadeImpl.adminAssigned",
                    biobankDB.getAbbreviation(), newAdmin.getWholeName(), permission);

            notificationDao.create(getOtherBiobankAdministrators(biobankDB, loggedUserId),
                    NotificationType.BIOBANK_ADMINISTRATOR, locMsg, biobankDB.getId());

        }

        return result;

    }

    public boolean changeAdministratorPermission(Long objectAdministratorId,
                                                 Permission permission,
                                                 ValidationErrors errors, Long loggedUserId) {
        notNull(objectAdministratorId);
        notNull(permission);
        notNull(errors);
        notNull(errors);

        BiobankAdministrator ba = biobankAdministratorDao.get(objectAdministratorId);

        if (ba == null) {
            fatalError(errors);
            return false;
        }

        boolean result = false;

        try {
            result = changeAdministratorPermission(ba, permission);
        } catch (LastManagerException ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.service.exceptions.LastBiobankManagerException"));
            return false;
        } catch (Exception ex) {
            fatalError(errors);
            return false;
        }

        if (result) {
            Biobank biobank = ba.getBiobank();

            User user = ba.getUser();

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.BiobankFacadeImpl.permissionChanged",
                    biobank.getAbbreviation(), user.getWholeName(), permission);

            notificationDao.create(getOtherBiobankAdministrators(biobank, loggedUserId),
                    NotificationType.BIOBANK_ADMINISTRATOR, locMsg, biobank.getId());
        }
        return result;
    }

    public boolean removeAdministrator(Long objectAdministratorId, ValidationErrors errors, Long loggedUserId) {
        notNull(objectAdministratorId);
        notNull(errors);

        BiobankAdministrator ba = biobankAdministratorDao.get(objectAdministratorId);
        if (ba == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.dbg.null"));
            return false;
        }

        boolean result = false;

        try {
            result = removeAdministrator(ba);
        } catch (LastManagerException ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.service.exceptions.LastBiobankManagerException"));
            return false;
        }

        if (result) {
            Biobank biobank = ba.getBiobank();

            User user = ba.getUser();

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.BiobankFacadeImpl.adminDeleted",
                    user.getWholeName(), biobank.getAbbreviation());

            notificationDao.create(getOtherBiobankAdministrators(biobank, loggedUserId),
                    NotificationType.BIOBANK_ADMINISTRATOR, locMsg, biobank.getId());
        }

        return result;
    }

    public boolean hasPermission(Permission permission, Long objectId, Long userId) {
        notNull(permission);
        notNull(objectId);
        notNull(userId);

        BiobankAdministrator ba = biobankAdministratorDao.get(biobankDao.get(objectId), userDao.get(userId));

        if (ba == null) {
            return false;
        }

        return ba.getPermission().include(permission);
    }

    public boolean removeBiobank(Long biobankId, ValidationErrors errors, Long loggedUserId) {
          notNull(biobankId);
          notNull(errors);

          boolean result = false;

          Biobank biobankDB = get(biobankId);

          try {

              if (remove(biobankId)) {
                  result = ServiceUtils.recursiveDeleteFolder(storagePath + biobankDB.getBiobankFolderPath(), errors) == Constant.SUCCESS;
              }

          } catch (Exception ex) {

              // Return DBG info that something went wrong. In final version there should be logging instead.
              fatalError(errors);
              return false;

          }

          if (result) {
              LocalizableMessage localizableMessage = new LocalizableMessage("cz.bbmri.facade.impl.BiobankFacadeImpl.biobankRemoved", biobankDB.getAbbreviation());

              notificationDao.create(getOtherBiobankAdministrators(biobankDB, loggedUserId),
                      NotificationType.BIOBANK_DELETE, localizableMessage, biobankDB.getId());
          }

          return result;


      }

        public boolean createBiobank(Biobank biobank, Long newAdministratorId, ValidationErrors errors) {

            notNull(biobank);
            notNull(newAdministratorId);
            notNull(errors);

            logger.debug("CreateBiobank - controle");

            try {
                biobank = create(biobank, newAdministratorId);

                logger.debug("CreateBiobank - created");

            } catch (DuplicitBiobankException ex) {
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.duplicitBiobankException"));
                return false;

            } catch (Exception ex) {
                // Return DBG info that something went wrong. In final version there should be logging instead.
                fatalError(errors);
                return false;
            }

            logger.debug("CreateBiobank - create folders");

            // Base folder
            if (!ServiceUtils.folderExists(storagePath)) {

                if (ServiceUtils.createFolder(storagePath, errors) != Constant.SUCCESS) {

                    logger.debug("CreateBiobank - Create Base folder failed");

                    remove(biobank.getId());
                    return false;
                }
            }

            // Biobanks folder
            if (!ServiceUtils.folderExists(storagePath + Biobank.BIOBANK_FOLDER)) {
                if (ServiceUtils.createFolder(storagePath + Biobank.BIOBANK_FOLDER, errors) != Constant.SUCCESS) {

                    logger.debug("CreateBiobank - Create Biobank folder failed");

                    remove(biobank.getId());
                    return false;
                }
            }

            // Folder for the biobank
            if (ServiceUtils.createFolder(storagePath + biobank.getBiobankFolderPath(), errors)
                    != Constant.SUCCESS) {

                logger.debug("CreateBiobank - Create folder for specific biobank failed");

                remove(biobank.getId());
                return false;
            }

            // Folder for the biobank/monitoring_data
            if (ServiceUtils.createFolder(storagePath + biobank.getBiobankMonitoringFolder(), errors) != Constant.SUCCESS) {

                logger.debug("CreateBiobank - Create folder for monitoring data failed");

                remove(biobank.getId());
                return false;
            }

            // Folder for the biobank/patient_data
            if (ServiceUtils.createFolder(storagePath + biobank.getBiobankPatientDataFolder(), errors)
                    != Constant.SUCCESS) {

                logger.debug("CreateBiobank - Create folder for patient data failed");

                remove(biobank.getId());
                return false;
            }

            // Folder for the biobank/temperature_data
            if (ServiceUtils.createFolder(storagePath + biobank.getBiobankTemperatureFolder(), errors)
                    != Constant.SUCCESS) {

                logger.debug("CreateBiobank - Create folder for temperature monitoring data failed");

                remove(biobank.getId());
                return false;
            }

            // Folder for the biobank/monitoring_data_archive
            if (ServiceUtils.createFolder(storagePath + biobank.getBiobankMonitoringArchiveFolder(), errors)
                    != Constant.SUCCESS) {

                logger.debug("CreateBiobank - Create folder for archive of patient data failed");

                remove(biobank.getId());
                return false;
            }

            // Folder for the biobank/patient_data_archive
            if (ServiceUtils.createFolder(storagePath + biobank.getBiobankPatientArchiveDataFolder(), errors)
                    != Constant.SUCCESS) {

                logger.debug("CreateBiobank - Create folder for archive of monitoring data failed");

                remove(biobank.getId());
                return false;
            }

            // Folder for the biobank/temperature_data_archive
            if (ServiceUtils.createFolder(storagePath + biobank.getBiobankTemperatureArchiveFolder(), errors)
                    != Constant.SUCCESS) {

                logger.debug("CreateBiobank - Create folder for archive of temperature monitoring data failed");

                remove(biobank.getId());
                return false;
            }

            return true;
        }

    public boolean updateBiobank(Biobank biobank, ValidationErrors errors, Long loggedUserId) {
         notNull(biobank);
         notNull(errors);
         notNull(loggedUserId);

         try {

             update(biobank);

             LocalizableMessage localizableMessage = new LocalizableMessage("cz.bbmri.facade.impl.BiobankFacadeImpl.biobankUpdated", biobank.getAbbreviation());

             notificationDao.create(getOtherBiobankAdministrators(biobank, loggedUserId),
                     NotificationType.BIOBANK_DETAIL, localizableMessage, biobank.getId());

             return true;

         } catch (Exception ex) {

             // Return DBG info that something went wrong. In final version there should be logging instead.

             fatalError(errors);

             return false;
         }
     }



}
