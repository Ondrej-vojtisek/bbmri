package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.ModuleDao;
import cz.bbmri.dao.PatientDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.ModuleLTS;
import cz.bbmri.entities.ModuleSTS;
import cz.bbmri.entities.Patient;
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
 *
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

    public boolean create(Patient patient, Long biobankId, ValidationErrors errors) {
        notNull(errors);
        if (create(patient, biobankId) == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.patientCreateFailed"));
            return false;
        }
        return true;
    }

    public Patient create(Patient patient, Long biobankId) {
        if (isNull(patient, "patient", null)) return null;
        if (isNull(biobankId, "biobankId", null)) return null;

        Biobank biobankDB = biobankDao.get(biobankId);
        if (isNull(biobankDB, "biobankDB", null)) return null;

        // Biobank relationship
        patient.setBiobank(biobankDB);
        patientDao.create(patient);

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
        if (patient.getInstitutionId() != null) patientDB.setInstitutionId(patient.getInstitutionId());

        patientDao.update(patientDB);
        return patientDB;
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


}
