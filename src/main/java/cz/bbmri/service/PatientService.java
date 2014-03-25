package cz.bbmri.service;


import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.infrastructure.Position;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 16.1.14
 * Time: 15:08
 * To change this template use File | Settings | File Templates.
 */
public interface PatientService  extends BasicService<Patient> {

    Patient create(Patient patient, Long biobankId);

    List<Patient> find(Patient patient, int requiredResults);

    List<Patient> getSorted(Long biobankId, String orderByParam, boolean desc);

    Patient getByInstitutionalId(String id);

}
