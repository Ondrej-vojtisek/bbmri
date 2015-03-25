package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.PatientDAO;
import cz.bbmri.entity.Patient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
