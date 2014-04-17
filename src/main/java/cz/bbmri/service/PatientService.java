package cz.bbmri.service;


import cz.bbmri.entities.Patient;
import cz.bbmri.service.simpleService.Get;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface PatientService  extends Get<Patient> {

    boolean create(Patient patient, Long biobankId, ValidationErrors errors);

    Patient create(Patient patient, Long biobankId);

    List<Patient> find(Patient patient, int requiredResults);

    List<Patient> getSorted(Long biobankId, String orderByParam, boolean desc);

    Patient getByInstitutionalId(String id);

}
