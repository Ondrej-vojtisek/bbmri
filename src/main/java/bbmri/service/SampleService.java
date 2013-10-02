package bbmri.service;

import bbmri.entities.Biobank;
import bbmri.entities.Sample;
import bbmri.entities.User;

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

     Sample eagerGet(Long id, boolean biobank, boolean request);

}
