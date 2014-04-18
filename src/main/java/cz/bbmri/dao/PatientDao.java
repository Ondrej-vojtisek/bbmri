package cz.bbmri.dao;

import cz.bbmri.entities.Patient;

import java.util.List;

/**
 * Interface to handle instances of Patient stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface PatientDao extends BasicBiobankDao<Patient> {

    /**
     * Find patient by selected attributes. Most priority is set to institutional id, then year of birth, sex and then
     * month of birth. Results are order by probable relevance.
     * Method is trying to find all similar patients to given one.
     *
     * @param patient - find similarities to this patient
     * @return list of patients similar to given patients (by its attributes)
     */
    List<Patient> findPatient(Patient patient);

    /**
     * Find patient by its institutionalId. It is a unique identifier given by the originator of patient data.
     *
     * @param id - String identifier
     * @return Patient with given institutionalId or null
     */
    Patient getByInstitutionalId(String id);

}
