package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.PatientDAO;
import cz.bbmri.entity.Biobank;
import cz.bbmri.entity.Patient;
import cz.bbmri.entity.enumeration.Status;
import cz.bbmri.io.InstanceImportResult;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("patientDAO")
@Transactional
public class PatientDAOImpl extends GenericDAOImpl<Patient> implements PatientDAO {

    public Patient get(Long id) {
        return (Patient) getCurrentSession().get(Patient.class, id);
    }

    public Patient getByInstitutionalId(String id) {
        Criterion criterionInstitutionalId = Restrictions.eq(Patient.PROP_INSTITUTIONAL_ID, id);

        // Retrieve a list of existing users matching the criterion above the list retrieval
        List<Patient> list = getCurrentSession().createCriteria(Patient.class)
                .add(criterionInstitutionalId)
                .setMaxResults(1).list();

        if(list.isEmpty()){
            return null;
        }

        return list.get(0);
    }

    public void remove(Patient patient) {
        getCurrentSession().delete(patient);
    }

    public InstanceImportResult importInstance(Patient patient) {
        notNull(patient);

        InstanceImportResult result = new InstanceImportResult(Patient.class.toString());
        result.setStatus(Status.ERROR);

        if (patient.getInstitutionalId() == null) {
            return null;
        }

        Patient patientDB = getByInstitutionalId(patient.getInstitutionalId());

        // Consent false
        if (!patient.isConsent()) {
            // If patient present - he must be deleted
            if (patientDB != null) {

                result.setStatus(Status.REMOVED);
                result.setIdentifier(patientDB.getId());
                remove(patientDB);

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
            patient = save(patient);

            // new patient was successfully added
            result.setIdentifier(patient.getId());
            result.setStatus(Status.ADDED_NEW);
            return result;

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

    private InstanceImportResult updateWithResult(Patient patient) {
        notNull(patient);

        Patient patientDB = get(patient.getId());

        notNull(patientDB);

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
                instanceImportResult.addChange(Patient.PROP_BIRTH_MONTH, patientDB.getBirthMonth(), patient.getBirthMonth());
                // Change
                patientDB.setBirthMonth(patient.getBirthMonth());
            }
        }

        // Birth year
        if (patientDB.getBirthYear() != patient.getBirthYear()) {
            // Not equals
            // Report
            instanceImportResult.addChange(Patient.PROP_BIRTH_YEAR, patientDB.getBirthYear(), patient.getBirthYear());
            // Change
            patientDB.setBirthYear(patient.getBirthYear());
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

        save(patientDB);
        return instanceImportResult;
    }
}
