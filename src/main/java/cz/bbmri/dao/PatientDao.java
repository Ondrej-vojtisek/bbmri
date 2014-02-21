package cz.bbmri.dao;

import cz.bbmri.entities.Patient;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 8.1.14
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 */
public interface PatientDao extends BasicDao<Patient> {

    List<Patient> findPatient(Patient patient);
}
