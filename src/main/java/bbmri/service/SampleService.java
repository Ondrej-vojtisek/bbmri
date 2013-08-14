package bbmri.service;

import bbmri.entities.Biobank;
import bbmri.entities.Sample;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 7.11.12
 * Time: 0:22
 * To change this template use File | Settings | File Templates.
 */
public interface SampleService extends BasicService<Sample>{

     Sample create(Sample sample, Long biobankId);

     Sample decreaseCount(Long sampleId, Integer requested);

     Sample withdrawSample(Long sampleId, Integer requested);

     List<Sample> getSamplesByQuery(Sample sample);

     List<Sample> getSamplesByQueryAndBiobank(Sample sample, Biobank biobank);

     List<Sample> getAllByBiobank(Long biobankId);

}
