package bbmri.service;

import bbmri.entities.Sample;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 7.11.12
 * Time: 0:22
 * To change this template use File | Settings | File Templates.
 */
public interface SampleService {

    public Sample create(Sample sample, Long biobankId);

    public void remove(Long id);

    public Sample update(Sample sample);

    public List<Sample> getAll();

    public Sample decreaseCount(Long sampleId, Integer requested);

}
