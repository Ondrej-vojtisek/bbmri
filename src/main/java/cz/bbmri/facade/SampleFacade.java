package cz.bbmri.facade;

import cz.bbmri.entities.Module;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.sample.DiagnosisMaterial;
import cz.bbmri.entities.sample.Genome;
import cz.bbmri.entities.sample.Serum;
import cz.bbmri.entities.sample.Tissue;
import net.sourceforge.stripes.action.Message;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.9.13
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */
public interface SampleFacade {

    boolean create(Sample sample, ValidationErrors errors);

    Sample get(Long sampleId);

    Module getModule(Long moduleId);

    List<Sample> findSamples(Sample sample, Long biobankId, Patient patient, boolean lts);

    List<Project> getProjectsBySample(Long sampleId);

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
