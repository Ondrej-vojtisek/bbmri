package cz.bbmri.facade.impl;

import cz.bbmri.entities.Sample;
import cz.bbmri.entities.sample.Tissue;
import cz.bbmri.facade.SampleFacade;
import cz.bbmri.service.BiobankService;
import cz.bbmri.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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

    public boolean create(Tissue tissue) {
        Tissue tissueDB = sampleService.create(tissue, null);

        if (tissueDB == null) {
            return false;
        }

        return true;
    }

    public boolean create(Sample sample) {
            Sample sampleDB = sampleService.create(sample, null);

            if (sampleDB == null) {
                return false;
            }

            return true;
        }

    public Sample get (Long sampleId){
        return sampleService.get(sampleId);
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
