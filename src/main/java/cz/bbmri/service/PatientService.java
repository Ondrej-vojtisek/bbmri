package cz.bbmri.service;


import cz.bbmri.entities.Patient;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 16.1.14
 * Time: 15:08
 * To change this template use File | Settings | File Templates.
 */
public interface PatientService  extends BasicService<Patient> {

    Patient eagerGet(Long patientId, boolean samples);

    Patient create(Patient patient, Long biobankId);

    List<Patient> find(Patient patient, int requiredResults);
}
