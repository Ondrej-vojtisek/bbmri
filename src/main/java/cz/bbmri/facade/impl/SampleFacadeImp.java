package cz.bbmri.facade.impl;

import cz.bbmri.entities.Module;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.sample.DiagnosisMaterial;
import cz.bbmri.entities.sample.Genome;
import cz.bbmri.entities.sample.Serum;
import cz.bbmri.entities.sample.Tissue;
import cz.bbmri.facade.SampleFacade;
import cz.bbmri.service.BiobankService;
import cz.bbmri.service.ModuleService;
import cz.bbmri.service.SampleService;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 16.1.14
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */
@Controller("sampleFacade")
public class SampleFacadeImp extends BasicFacade implements SampleFacade {


//    @Autowired
//    private PatientService patientService;

    @Autowired
    private BiobankService biobankService;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private ModuleService moduleService;


    public boolean create(Sample sample, ValidationErrors errors) {
        Sample sampleDB = sampleService.create(sample, null);

        if (sampleDB == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.SampleFacadeImpl.sampleCreateFailed"));
            return false;
        }

        return true;
    }

    public Sample get(Long sampleId) {
        return sampleService.get(sampleId);
    }

    public Module getModule(Long moduleId) {
        return moduleService.get(moduleId);
    }

    public List<Sample> findSamples(Sample sample, Long biobankId, Patient patient, boolean lts){
        logger.debug("Facade findSamples sample: " + sample + " biobankId: " + biobankId + " Patient: " + patient
        + " LTS: " + lts);
        return sampleService.getSamplesByQuery(sample, biobankId,  patient, lts);
    }

    public boolean createRequestGroup(List<Long> sampleIds, Long sampleRequestId, ValidationErrors errors){

    }

//    public boolean generateRandomSample(int count) {
//
//    }
//
//    public boolean update(Sample sample) {
//
//    }
//
//    public List<Sample> getSamplesByBiobank(Long biobankId) {
//
//    }
//
//    public List<Sample> getSamplesByPatient(Long patientId) {
//
//    }
//
//    public List<Sample> getSamplesByQuery(Sample query) {
//
//    }

}
