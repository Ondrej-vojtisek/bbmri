package cz.bbmri.service;


import cz.bbmri.entities.Patient;
import cz.bbmri.service.simpleService.Get;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 16.1.14
 * Time: 15:08
 * To change this template use File | Settings | File Templates.
 */
public interface PatientService  extends Get<Patient> {

    boolean create(Patient patient, Long biobankId, ValidationErrors errors);

    Patient create(Patient patient, Long biobankId);

    List<Patient> find(Patient patient, int requiredResults);

    List<Patient> getSorted(Long biobankId, String orderByParam, boolean desc);

    Patient getByInstitutionalId(String id);

}
