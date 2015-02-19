package cz.bbmri.service;

import cz.bbmri.entities.Patient;
import cz.bbmri.entities.sample.Sample;
import cz.bbmri.io.InstanceImportResult;
import cz.bbmri.service.simpleService.*;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * API for handling Samples
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface SampleService extends All<Sample>, AllOrderedBy<Sample>, Count, Get<Sample>, Remove, Update<Sample> {

    /**
     * Store instance of sample in DB. Method for auto triggered events.
     *
     * @param sample       - new instance of sample
     * @param moduleId     - sample will is associated with Module identified by moduleId
     * @return instance of stored Sample
     */
    Sample create(Sample sample, Long moduleId);

    /**
     * Store instance of sample in DB
     *
     * @param sample       - new instance of sample
     * @param moduleId     - sample will is associated with Module identified by moduleId
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - id of user who initiated event
     * @return true/false
     */
    boolean create(Sample sample, Long moduleId, ValidationErrors errors, Long loggedUserId);

    /**
     * Find sample by institutional ID
     *
     * @param id - institutional ID of sample
     * @return sample with given ID or null
     */
    Sample getByInstitutionalId(String id);


    /**
     * List of sorted samples. All returned samples are associated with given biobank
     *
     * @param biobankId    - ID of biobank
     * @param orderByParam
     * @param desc
     * @return list of sorted samples
     */
    List<Sample> getSortedSamples(Long biobankId, String orderByParam, boolean desc);

    /**
     * Find all similar samples to given params. Samples are search among all samples of given biobank. Param lts defines
     * whether it is LTS or STS. Sample defines required characteristics of biological material. Patient defines age,
     * sex, ... Only not null fields are taken as search criterion.
     *
     * @param sample    - search criteria based on sample characteristics
     * @param biobankId - ID of biobank where samples will be find
     * @param patient   - search criteria based on patient characteristics
     * @param lts       - true = LTS, false = STS
     * @return list of similar samples
     */
    List<Sample> findSamples(Sample sample, Long biobankId, Patient patient, boolean lts);

    //TODO
    InstanceImportResult importInstance(Sample sample, Long moduleId);

}
