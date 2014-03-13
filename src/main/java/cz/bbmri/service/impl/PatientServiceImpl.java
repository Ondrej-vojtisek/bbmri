package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.ModuleDao;
import cz.bbmri.dao.PatientDao;
import cz.bbmri.dao.SampleDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.ModuleLTS;
import cz.bbmri.entities.ModuleSTS;
import cz.bbmri.entities.Patient;
import cz.bbmri.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 16.1.14
 * Time: 15:08
 * To change this template use File | Settings | File Templates.
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
    private SampleDao sampleDao;

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

    public Patient create(Patient patient, Long biobankId) {
        if (patient == null) {
            logger.debug("Object can't null");
            return null;
        }

        Biobank biobankDB = biobankDao.get(biobankId);

        if (biobankDB == null) {
            logger.debug("Object retrieved from database is null");
            return null;
        }

        patient.setBiobank(biobankDB);
        patientDao.create(patient);

        ModuleSTS modulests = new ModuleSTS();
        modulests.setPatient(patient);
        moduleDao.create(modulests);

        ModuleLTS modulelts = new ModuleLTS();
        modulelts.setPatient(patient);
        moduleDao.create(modulelts);

        return patient;
    }

    public boolean remove(Long id) {
        notNull(id);
        Patient patientDB = patientDao.get(id);
        if (patientDB == null) {
            logger.debug("Object retrieved from database is null");
            return false;
        }

        if (patientDB.getBiobank() != null) {
            Biobank biobank = patientDB.getBiobank();
            biobank.getPatients().remove(patientDB);
            biobankDao.update(biobank);
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
        notNull(patient);

        Patient patientDB = patientDao.get(patient.getId());

        if (patient.getSex() != null) patientDB.setSex(patient.getSex());
        if (patient.getBirthMonth() != null) patientDB.setBirthMonth(patient.getBirthMonth());
        if (patient.getBirthYear() != null) patientDB.setBirthYear(patient.getBirthYear());
        if (patient.getInstitutionId() != null) patientDB.setInstitutionId(patient.getInstitutionId());

        patientDao.update(patientDB);
        return patientDB;
    }


    @Transactional(readOnly = true)
    public Integer count() {
        return patientDao.count();
    }

    @Transactional(readOnly = true)
    public List<Patient> allOrderedBy(String orderByParam, boolean desc) {
        return patientDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<Patient> nOrderedBy(String orderByParam, boolean desc, int number) {
        return patientDao.nOrderedBy(orderByParam, desc, number);
    }

    @Transactional(readOnly = true)
    public List<Patient> find(Patient patient, int requiredResults) {
        notNull(patient);

        List<Patient> patients = patientDao.findPatient(patient);
        if (patients == null) {
            return null;
        }
        if (requiredResults > patients.size()) {
            return patients;
        }
        return patients.subList(0, requiredResults);
    }

    public List<Patient> getSorted(Long biobankId, String orderByParam, boolean desc) {
        if (biobankId == null) {
            logger.debug("biobankId is null");
            return null;
        }

        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            logger.debug("BiobankDB can´t be null");
            return null;
        }

        return patientDao.getSorted(biobankDB, orderByParam, desc);
    }

}
