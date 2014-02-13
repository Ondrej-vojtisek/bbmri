package cz.bbmri.service;

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

     Tissue create(Tissue tissue, Long patientId);

     Sample create(Sample sample, Long patientId);

     Sample decreaseCount(Long sampleId, Integer requested);

     Sample withdrawSample(Long sampleId, Integer requested);

     List<Sample> getSamplesByQuery(Sample sample);

     Sample eagerGet(Long id, boolean patient, boolean request);

}
