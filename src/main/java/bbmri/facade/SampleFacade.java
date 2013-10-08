package bbmri.facade;

import bbmri.entities.Sample;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.9.13
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */
public interface SampleFacade {

    void createSample(Sample sample);
    void generateRandomSample(int count);
    void update(Sample sample);
    void getSamplesByBiobank(Long biobankId);
    List<Sample> getSamplesByQuery(Sample query);
    void internalWithdraw(Long sampleId, int count);
    void externalWithdraw(Long sampleId, int count);

}
