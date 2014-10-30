package cz.bbmri.action.sample;

import cz.bbmri.action.biobank.BiobankActionBean;
import cz.bbmri.action.biobank.BiobankSamplesActionBean;
import cz.bbmri.entities.*;
import cz.bbmri.entities.sample.*;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import net.sourceforge.stripes.action.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@UrlBinding("/sample/{$event}/{sampleId}")
public class SampleActionBean extends AbstractSampleActionBean<Sample> {


    public SampleActionBean() {
        setComponentManager(new ComponentManager(
                ComponentManager.SAMPLE_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
    }



    public static Breadcrumb getBreadcrumb(boolean active, Long sampleId) {
        return new Breadcrumb(SampleActionBean.class.getName(),
                "detail", false, "cz.bbmri.entities.sample.Sample.sample",
                active, "sampleId", sampleId);
    }

    public boolean getIsTissue() {
        return getSample() != null && getSample() instanceof Tissue;
    }

    public boolean getIsSerum() {
        return getSample() != null && getSample() instanceof Serum;
    }

    public boolean getIsDiagnosisMaterial() {
        return getSample() != null && getSample() instanceof DiagnosisMaterial;
    }

    public boolean getIsGenome() {
        return getSample() != null && getSample() instanceof Genome;
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

        // TODO smazat?

        if (getSample() == null) {
            return null;
        }

        if (getSample().getRequests() == null) {
            return null;
        }

        List<Project> projects = new ArrayList<Project>();
        for (Request request : getSample().getRequests()) {
            if (request.getWithdraw() instanceof SampleRequest) {
                projects.add(((SampleRequest) request.getWithdraw()).getProject());
            }
        }

        return projects;
    }

    public List<SampleReservation> getReservationsBySample() {

        // TODO smazat?

        if (getSample() == null) {
            return null;
        }

        if (getSample().getRequests() == null) {
            return null;
        }

        List<SampleReservation> reservations = new ArrayList<SampleReservation>();
        for (Request request : getSample().getRequests()) {
            if (request.getWithdraw() instanceof SampleReservation) {
                reservations.add((SampleReservation)request.getWithdraw());
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

        setBiobankId(getSample().getModule().getPatient().getBiobank().getId());

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(BiobankSamplesActionBean.getBreadcrumb(false, biobankId));
        getBreadcrumbs().add(SampleActionBean.getBreadcrumb(true, getSampleId()));


        return new ForwardResolution(SAMPLE_DETAIL);
    }
}
