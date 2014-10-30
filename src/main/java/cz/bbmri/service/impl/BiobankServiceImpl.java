package cz.bbmri.service.impl;

import cz.bbmri.dao.*;
import cz.bbmri.entities.*;
import cz.bbmri.entities.constant.Constant;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.entities.infrastructure.Infrastructure;
import cz.bbmri.service.BiobankService;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
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

    @Autowired
    private InfrastructureDao infrastructureDao;

    @Autowired
    private WithdrawDao withdrawDao;


    public boolean create(Biobank biobank, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);

        if (isNull(biobank, "biobank", errors)) return false;

        if (biobankDao.getBiobankByAbbreviation(biobank.getAbbreviation()) != null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.duplicitBiobankException"));
            return false;
        }
        biobankDao.create(biobank);
        //base folder, biobanks folder, biobank folder, ...
        int result = ServiceUtils.createFolders(errors,
                storagePath,
                storagePath + Biobank.BIOBANK_FOLDER, // Biobanks folder
                storagePath + biobank.getBiobankFolderPath(), // Folder for the biobank
                storagePath + biobank.getBiobankMonitoringFolder(), // Folder for the biobank/monitoring_data
                storagePath + biobank.getBiobankPatientDataFolder(), // Folder for the biobank/patient_data
                storagePath + biobank.getBiobankTemperatureFolder(), // Folder for the biobank/temperature_data
                storagePath + biobank.getBiobankMonitoringArchiveFolder(), // Folder for the biobank/monitoring_data_archive
                storagePath + biobank.getBiobankPatientArchiveDataFolder(),  // Folder for the biobank/patient_data_archive
                storagePath + biobank.getBiobankTemperatureArchiveFolder(),   // Folder for the biobank/temperature_data_archive
                storagePath + biobank.getBiobankCalibrationDataFolder()   // Folder for the biobank/calibration_data
        );
        if (result != Constant.SUCCESS) {
            biobankDao.remove(biobank);
            return false;
        }

        Infrastructure infrastructure = new Infrastructure();
        infrastructure.setBiobank(biobank);
        infrastructureDao.create(infrastructure);

        // Archive message
        archive("Biobank " + biobank.getAbbreviation() + " was created.", loggedUserId);

        return true;
    }

    public boolean remove(Long biobankId, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);

        if (isNull(biobankId, "biobankId", errors)) return false;
        if (isNull(biobankId, "biobankId", errors)) return false;

        notNull(biobankId);
        notNull(errors);

        boolean result;

        Biobank biobankDB = get(biobankId);
        if (isNull(biobankDB, "biobankDB", errors)) return false;
        List<Patient> patients = biobankDB.getPatients();
        if (patients != null) {
            for (Patient patient : patients) {
                patientDao.remove(patient);
            }
        }
        List<Withdraw> withdraws = biobankDB.getWithdraws();
        if (withdraws != null) {
            for (Withdraw withdraw : withdraws) {
                withdrawDao.remove(withdraw);
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

        // Archive message
        archive("Biobank " + biobankDB.getAbbreviation() + " was deleted.", loggedUserId);

        // delete files
        result = ServiceUtils.recursiveDeleteFolder(storagePath + biobankDB.getBiobankFolderPath(), errors) == Constant.SUCCESS;
        if (result) {

            LocalizableMessage localizableMessage = new LocalizableMessage("cz.bbmri.facade.impl.BiobankFacadeImpl.biobankRemoved", biobankDB.getAbbreviation());

            notificationDao.create(getOtherBiobankAdministrators(biobankDB, loggedUserId),
                    NotificationType.BIOBANK_DELETE, localizableMessage, biobankDB.getId());

            biobankDao.remove(biobankDB);
        }

        return result;
    }

    public boolean update(Biobank biobank, ValidationErrors errors, Long loggedUserId) {

        notNull(errors);

        if (isNull(biobank, "biobank", errors)) return false;
        if (isNull(loggedUserId, "loggedUserId", errors)) return false;

        Biobank biobankDB = biobankDao.get(biobank.getId());
        if (isNull(biobankDB, "biobankDB", errors)) return false;

        // Abbreviation is final
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

        LocalizableMessage localizableMessage = new LocalizableMessage("cz.bbmri.facade.impl.BiobankFacadeImpl.biobankUpdated", biobank.getAbbreviation());

        notificationDao.create(getOtherBiobankAdministrators(biobank, loggedUserId),
                NotificationType.BIOBANK_DETAIL, localizableMessage, biobank.getId());

        // Archive message
        archive("Biobank " + biobankDB.getAbbreviation() + " was updated.", loggedUserId);

        return true;
    }

    @Transactional(readOnly = true)
    public List<Biobank> all() {
        return biobankDao.all();
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return biobankDao.count();
    }

    @Transactional(readOnly = true)
    public Biobank get(Long id) {
        if (isNull(id, "id", null)) return null;
        return biobankDao.get(id);
    }

    @Transactional(readOnly = true)
    public List<Biobank> allOrderedBy(String orderByParam, boolean desc) {
        return biobankDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public Biobank getBiobankByAbbreviation(String abbreviation) {
        if (isNull(abbreviation, "abbreviation", null)) return null;
        return biobankDao.getBiobankByAbbreviation(abbreviation);
    }
}
