package cz.bbmri.dao;

import cz.bbmri.entities.Patient;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */
public interface PatientDao extends BasicBiobankDao<Patient> {

    List<Patient> findPatient(Patient patient);

    Patient getByInstitutionalId(String id);

}
