package cz.bbmri.service;

import cz.bbmri.entities.*;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 7.11.12
 * Time: 0:22
 * To change this template use File | Settings | File Templates.
 */
public interface SampleService extends BasicService<Sample> {

    boolean create(Sample sample, Long moduleId);

    boolean create(Sample sample, Long moduleId, ValidationErrors errors);

    Sample getByInstitutionalId(String id);

    Sample decreaseCount(Long sampleId, Integer requested);

    Sample withdrawSample(Long sampleId, Integer requested);

    List<Sample> getSortedSamples(Long biobankId, String orderByParam, boolean desc);

     List<Sample> findSamples(Sample sample, Long biobankId, Patient patient, boolean lts);

}
