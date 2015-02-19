package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.ModuleDao;
import cz.bbmri.dao.PatientDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.ModuleLTS;
import cz.bbmri.entities.ModuleSTS;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.enumeration.Status;
import cz.bbmri.io.InstanceImportResult;
import cz.bbmri.service.PatientService;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Transactional
@Service("patientService")
public class PatientServiceImpl extends BasicServiceImpl implements PatientService {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private ModuleDao moduleDao;

    @Transactional(readOnly = true)
    public List<Patient> all() {
        return patientDao.all();
    }

    @Transactional(readOnly = true)
    public Patient get(Long id) {
        notNull(id);
        return patientDao.get(id);
    }

    public boolean create(Patient patient, Long biobankId, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);
        if (create(patient, biobankId) == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.patientCreateFailed"));
            return false;
        }

        Biobank biobank = biobankDao.get(biobankId);
        archive("New Patient with id: " + patient.getInstitutionId() + "was created in biobank: " +
                        biobank.getAbbreviation(),
                loggedUserId);

        return true;
    }

    public Patient create(Patient patient, Long biobankId) {
        if (isNull(patient, "patient", null)) return null;
        if (isNull(biobankId, "biobankId", null)) return null;

        Biobank biobankDB = biobankDao.get(biobankId);
        if (isNull(biobankDB, "biobankDB", null)) return null;

        logger.debug("New patient: " + patient);

        // Biobank relationship
        patient.setBiobank(biobankDB);
        patientDao.create(patient);

        logger.debug("New patient2: " + patient);

        // STS
        ModuleSTS modulests = new ModuleSTS();
        modulests.setPatient(patient);
        moduleDao.create(modulests);

        // LTS
        ModuleLTS modulelts = new ModuleLTS();
        modulelts.setPatient(patient);
        moduleDao.create(modulelts);

        return patient;
    }

    public boolean remove(Long id) {
        if (isNull(id, "id", null)) return false;
        Patient patientDB = patientDao.get(id);
        if (isNull(patientDB, "patientDB", null)) return false;

        if (patientDB.getBiobank() != null) {
            patientDB.setBiobank(null);
        }

        if (patientDB.getModuleSTS() != null) {
            moduleDao.remove(patientDB.getModuleSTS());
        }

        if (patientDB.getModuleLTS() != null) {
            moduleDao.remove(patientDB.getModuleLTS());
        }

        patientDao.remove(patientDB);
        return true;
    }

    public Patient update(Patient patient) {
        if (isNull(patient, "patient", null)) return null;

        Patient patientDB = patientDao.get(patient.getId());

        if (isNull(patientDB, "patientDB", null)) return null;

        if (patient.getSex() != null) patientDB.setSex(patient.getSex());
        if (patient.getBirthMonth() != null) patientDB.setBirthMonth(patient.getBirthMonth());
        if (patient.getBirthYear() != null) patientDB.setBirthYear(patient.getBirthYear());

        // institutional ID may not be changed easily
        // if (patient.getInstitutionId() != null) patientDB.setInstitutionId(patient.getInstitutionId());

        patientDao.update(patientDB);
        return patientDB;
    }

    private InstanceImportResult updateWithResult(Patient patient) {
        if (isNull(patient, "patient", null)) return null;

        Patient patientDB = patientDao.get(patient.getId());

        if (isNull(patientDB, "patientDB", null)) return null;

        InstanceImportResult instanceImportResult = new InstanceImportResult(Patient.class.toString());
        // initialize context id
        instanceImportResult.setIdentifier(patient.getId());


        // Sex
        if (patient.getSex() != null) {
            if (!patientDB.getSex().equals(patient.getSex())) {
                // Not equals
                // Report
                instanceImportResult.addChange(Patient.PROP_SEX, patientDB.getSex(), patient.getSex());
                // Change
                patientDB.setSex(patient.getSex());
            }
        }

        // Birth month
        if (patient.getBirthMonth() != null) {
            if (!patientDB.getBirthMonth().equals(patient.getBirthMonth())) {
                // Not equals
                // Report
                instanceImportResult.addChange(Patient.PROP_BIRTHMONTH, patientDB.getBirthMonth(), patient.getBirthMonth());
                // Change
                patientDB.setBirthMonth(patient.getBirthMonth());
            }
        }

        // Birth year
        if (patient.getBirthYear() != null) {
            if (!patientDB.getBirthYear().equals(patient.getBirthYear())) {
                // Not equals
                // Report
                instanceImportResult.addChange(Patient.PROP_BIRTHYEAR, patientDB.getBirthYear(), patient.getBirthYear());
                // Change
                patientDB.setBirthYear(patient.getBirthYear());
            }
        }

        // Consent
        if (patientDB.isConsent() != patient.isConsent()) {
            // Not equals
            // Report
            instanceImportResult.addChange(Patient.PROP_CONSENT, patientDB.isConsent(), patient.isConsent());
            // Change
            patientDB.setConsent(patient.isConsent());
        }

        if (instanceImportResult.getAttributeChanges() != null) {
            if (!instanceImportResult.getAttributeChanges().isEmpty()) {
                instanceImportResult.setStatus(Status.CHANGED_CURRENT);
            } else {
                instanceImportResult.setStatus(Status.UNCHANGED_CURRENT);
            }
        }

        patientDao.update(patientDB);
        return instanceImportResult;
    }

    @Transactional(readOnly = true)
    public List<Patient> find(Patient patient, int requiredResults) {
        if (isNull(patient, "patient", null)) return null;

        List<Patient> patients = patientDao.findPatient(patient);
        if (patients == null) {
            return null;
        }
        if (requiredResults > patients.size()) {
            return patients;
        }
        return patients.subList(0, requiredResults);
    }

    @Transactional(readOnly = true)
    public List<Patient> getSorted(Long biobankId, String orderByParam, boolean desc) {
        if (isNull(biobankId, "biobankId", null)) return null;

        Biobank biobankDB = biobankDao.get(biobankId);
        if (isNull(biobankDB, "biobankDB", null)) return null;

        return patientDao.getSorted(biobankDB, orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public Patient getByInstitutionalId(String id) {
        if (isNull(id, "id", null)) return null;
        return patientDao.getByInstitutionalId(id);
    }


    public InstanceImportResult importInstance(Patient patient, Long biobankId) {
        notNull(patient);
        notNull(biobankId);

        InstanceImportResult result = new InstanceImportResult(Patient.class.toString());
        result.setStatus(Status.ERROR);

        if (patient.getInstitutionId() == null) {
            return null;
        }

        Patient patientDB = getByInstitutionalId(patient.getInstitutionId());

        // Consent false
        if (!patient.isConsent()) {
            // If patient present - he must be deleted
            if (patientDB != null) {

                result.setStatus(Status.REMOVED);
                result.setIdentifier(patientDB.getId());
                remove(patientDB.getId());

             // if he is not present, don't add him to system
            } else {
                // consent it false, can't be added
                result.setStatus(Status.NOT_ADDED);
            }

             return result;
        }

        // is patient in DB ?
        if (patientDB == null) {
            // No .. lets create it
            Patient newPatient = create(patient, biobankId);

            // Unable to create new patient
            if (newPatient == null) {
                result.addChange(Patient.PROP_INSTITUTIONID, null, patient.getInstitutionId());
                result.setStatus(Status.ERROR);
                return result;
            } else {
                // new patient was successfully added
                result.setIdentifier(patient.getId());
                result.setStatus(Status.ADDED_NEW);
                return result;
            }

        } else {
            // Patient is already in DB

            // Assign internal id which is not known in import
            patient.setId(patientDB.getId());

            result = updateWithResult(patient);

            // If something went wrong
            if (result == null) {
                result = new InstanceImportResult(Patient.class.toString());
                result.setIdentifier(patient.getId());
                result.setStatus(Status.ERROR);
            }

        }
        return result;
    }


}
