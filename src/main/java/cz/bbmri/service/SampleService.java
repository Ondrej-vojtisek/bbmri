package cz.bbmri.service;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.sample.Tissue;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 7.11.12
 * Time: 0:22
 * To change this template use File | Settings | File Templates.
 */
public interface SampleService extends BasicService<Sample>{

     Sample create(Sample sample, Long moduleId);

     Sample getByInstitutionalId(String id);

     Sample decreaseCount(Long sampleId, Integer requested);

     Sample withdrawSample(Long sampleId, Integer requested);

     List<Sample> getSamplesByQuery(Sample sample, Long biobankId, Patient patient, boolean lts);

    List<Sample> getSortedSamples(Long biobankId, String orderByParam, boolean desc);

}
