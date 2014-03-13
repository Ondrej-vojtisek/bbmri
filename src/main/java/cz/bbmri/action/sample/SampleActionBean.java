package cz.bbmri.action.sample;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.*;
import cz.bbmri.entities.sample.DiagnosisMaterial;
import cz.bbmri.entities.sample.Genome;
import cz.bbmri.entities.sample.Serum;
import cz.bbmri.entities.sample.Tissue;
import cz.bbmri.facade.SampleFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 12.2.14
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/sample/{$event}/{sampleId}")
public class SampleActionBean extends AbstractSampleActionBean {

    @SpringBean
    private SampleFacade sampleFacade;

    public boolean getIsTissue() {
        if (getSample() == null) {
            return false;
        }
        return getSample() instanceof Tissue;
    }

    public boolean getIsSerum() {
        if (getSample() == null) {
            return false;
        }
        return getSample() instanceof Serum;
    }

    public boolean getIsDiagnosisMaterial() {
        if (getSample() == null) {
            return false;
        }
        return getSample() instanceof DiagnosisMaterial;
    }

    public boolean getIsGenome() {
        if (getSample() == null) {
            return false;
        }
        return getSample() instanceof Genome;
    }

    public Tissue getTissue() {
        return (Tissue) getSample();
    }

    public Serum getSerum() {
        return (Serum) getSample();
    }

    public Genome getGenome() {
        return (Genome) getSample();
    }

    public DiagnosisMaterial getDiagnosisMaterial() {
        return (DiagnosisMaterial) getSample();
    }

    public List<Project> getProjectsBySample() {
        if (getSample() == null) {
            return null;
        }

        if (getSample().getRequests() == null) {
            return null;
        }

        List<Project> projects = new ArrayList<Project>();
        for (Request request : getSample().getRequests()) {
            if (request.getSampleQuestion() instanceof SampleRequest) {
                projects.add(((SampleRequest) request.getSampleQuestion()).getProject());
            }
        }

        return projects;
    }

    public List<SampleQuestion> getReservationsBySample() {
        if (getSample() == null) {
            return null;
        }

        if (getSample().getRequests() == null) {
            return null;
        }

        List<SampleQuestion> reservations = new ArrayList<SampleQuestion>();
        for (Request request : getSample().getRequests()) {
            if (request.getSampleQuestion() instanceof SampleReservation) {
                reservations.add(request.getSampleQuestion());
            }
        }

        return reservations;
    }

//    /* Methods */
//    @DontValidate
//    @DefaultHandler
//    @HandlesEvent("all") /* Necessary for stripes security tag*/
//    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
//    public Resolution all() {
//        return new ForwardResolution("/webpages/sample/all.jsp");
//    }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("detail") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution detail() {
        return new ForwardResolution(SAMPLE_DETAIL);
    }
}
