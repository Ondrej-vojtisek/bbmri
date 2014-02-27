package cz.bbmri.facade.impl;

import cz.bbmri.entities.*;
import cz.bbmri.facade.SampleFacade;
import cz.bbmri.service.BiobankService;
import cz.bbmri.service.ModuleService;
import cz.bbmri.service.SampleService;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
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

    public List<Sample> findSamples(Sample sample, Long biobankId, Patient patient, boolean lts) {
        logger.debug("Facade findSamples sample: " + sample + " biobankId: " + biobankId + " Patient: " + patient
                + " LTS: " + lts);
        return sampleService.getSamplesByQuery(sample, biobankId, patient, lts);
    }

    public List<Project> getProjectsBySample(Long sampleId) {
        Sample sampleDB = sampleService.eagerGet(sampleId, false, true);
        if (sampleDB == null) {
            logger.debug("sampleDB can't be null");
            // Empty List
            return new ArrayList<Project>();
        }
        List<Project> projects = new ArrayList<Project>();
        for (Request request : sampleDB.getRequests()) {
            if (request.getSampleQuestion() instanceof SampleRequest) {
                Project project = ((SampleRequest)request.getSampleQuestion()).getProject();
                projects.add(project);
            }
        }

        return projects;
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
