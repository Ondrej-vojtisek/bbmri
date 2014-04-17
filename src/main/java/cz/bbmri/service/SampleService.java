package cz.bbmri.service;

import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Sample;
import cz.bbmri.service.simpleService.*;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface SampleService extends All<Sample>, Count, Get<Sample>, Remove, Update<Sample> {

    boolean create(Sample sample, Long moduleId, ValidationErrors errors);

    Sample getByInstitutionalId(String id);

    List<Sample> getSortedSamples(Long biobankId, String orderByParam, boolean desc);

    List<Sample> findSamples(Sample sample, Long biobankId, Patient patient, boolean lts);

    List<Sample> allOrderedBy(String orderByParam, boolean desc);

}
