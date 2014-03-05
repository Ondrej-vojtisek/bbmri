package cz.bbmri.action.sample;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.infrastructure.Position;
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
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 12.2.14
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */
@HttpCache(allow = false)
@UrlBinding("/sample/{$event}/{biobankId}/{sampleId}")
public class SampleActionBean extends PermissionActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private SampleFacade sampleFacade;

    private Long sampleId;

    private Sample sample;

    public Sample getSample() {
        if (sample == null) {
            if (sampleId != null) {
                sample = sampleFacade.get(sampleId);
            }
        }
        return sample;
    }

    public boolean getIsTissue() {
        if (getSample() == null) {
            return false;
        }
        return sample instanceof Tissue;
    }

    public boolean getIsSerum() {
        if (getSample() == null) {
            return false;
        }
        return sample instanceof Serum;
    }

    public boolean getIsDiagnosisMaterial() {
        if (getSample() == null) {
            return false;
        }
        return sample instanceof DiagnosisMaterial;
    }

    public boolean getIsGenome() {
        if (getSample() == null) {
            return false;
        }
        return sample instanceof Genome;
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

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public List<Project> getProjectsBySample() {
        if (getSample() == null) {
            return null;
        }

        return sampleFacade.getProjectsBySample(sampleId);
    }

    public List<SampleQuestion> getReservationsBySample() {
        if (getSample() == null) {
            return null;
        }

        return sampleFacade.getReservationsBySample(sampleId);
    }

    public Set<Position> getPositions() {
        if (getSample() == null) {
            return null;
        }

        return sampleFacade.getPositionsBySample(sampleId);
    }


    /* Methods */
    @DontValidate
    @DefaultHandler
    @HandlesEvent("all") /* Necessary for stripes security tag*/
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution all() {
        return new ForwardResolution("/webpages/sample/all.jsp");
    }

    @DontValidate
    @HandlesEvent("detail") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution detail() {
        return new ForwardResolution(SAMPLE_DETAIL);
    }

    @DontValidate
    @HandlesEvent("projects") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution projects() {
        return new ForwardResolution(SAMPLE_PROJECTS);
    }

    @DontValidate
    @HandlesEvent("reservations") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution reservations() {
        return new ForwardResolution(SAMPLE_RESERVATIONS);
    }


    @DontValidate
    @HandlesEvent("positions") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution positions() {
        return new ForwardResolution(SAMPLE_POSITIONS);
    }
}
