package cz.bbmri.service;


import cz.bbmri.entities.Patient;
import cz.bbmri.service.simpleService.Find;
import cz.bbmri.service.simpleService.Get;
import cz.bbmri.service.simpleService.Remove;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * API for handling Patients
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface PatientService extends Get<Patient>, Find<Patient>, Remove {

    /**
     * Store new instance of patient in DB.
     *
     * @param patient      - new instance of patient
     * @param biobankId    - new patient will be associated with biobank identified by biobankId
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - if of user who initiated event
     * @return true/false
     */
    boolean create(Patient patient, Long biobankId, ValidationErrors errors, Long loggedUserId);

    /**
     * Store new instance of patient in DB. Method without error notifications - it is suitable for automatized
     * creation triggered from xml parsers.
     *
     * @param patient   - new instance of patient
     * @param biobankId - new patient will be associated with biobank identified by biobankId
     * @return new instance or null
     */
    Patient create(Patient patient, Long biobankId);

    /**
     * Return sorted list of patients of given biobank.
     *
     * @param biobankId    - ID of biobank
     * @param orderByParam
     * @param desc
     * @return sorted list of patients
     */
    List<Patient> getSorted(Long biobankId, String orderByParam, boolean desc);

    /**
     * Search DB for patient with given institutional ID (not internal ID of type Long)
     *
     * @param id - original ID from home institution
     * @return patient with given ID or null
     */
    Patient getByInstitutionalId(String id);



}
