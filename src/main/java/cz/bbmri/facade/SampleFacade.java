package cz.bbmri.facade;

import cz.bbmri.entities.Sample;
import cz.bbmri.entities.sample.Tissue;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.9.13
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */
public interface SampleFacade {

      boolean create(Tissue tissue);

      boolean create(Sample sample);

      Sample get (Long sampleId);

//    boolean createSample(Sample sample);
//
//    boolean generateRandomSample(int count);
//
//    boolean update(Sample sample);
//
//    List<Sample> getSamplesByBiobank(Long biobankId);
//
//    List<Sample> getSamplesByPatient(Long patientId);
//
//    List<Sample> getSamplesByQuery(Sample query);

    // void internalWithdraw(Long sampleId, int count);

    // void externalWithdraw(Long sampleId, int count);

}
