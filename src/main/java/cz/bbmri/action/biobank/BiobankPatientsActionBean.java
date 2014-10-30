package cz.bbmri.action.biobank;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.PatientService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@UrlBinding("/biobank/patients/{$event}/{biobankId}")
public class BiobankPatientsActionBean extends PermissionActionBean<Patient> {

    @SpringBean
    private PatientService patientService;

    public static Breadcrumb getBreadcrumb(boolean active, Long biobankId) {
        return new Breadcrumb(BiobankPatientsActionBean.class.getName(),
                "display", false, "cz.bbmri.entities.Patient.patients",
                active, "biobankId", biobankId);
    }

    public BiobankPatientsActionBean() {
        //default
        setPagination(new MyPagedListHolder<Patient>(new ArrayList<Patient>()));
        // ribbon
        setComponentManager(new ComponentManager(
                ComponentManager.PATIENT_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
        getPagination().setIdentifierParam("biobankId");
    }

    @DefaultHandler
    @HandlesEvent("display")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution display() {

        if(getPagination().getIdentifier() == null){
            System.err.println("getPagination().getIdentifier() null");
        }

        if(getPagination().getIdentifier() != null){
                    System.err.println("getPagination().getIdentifier() not null");
        }

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(BiobankPatientsActionBean.getBreadcrumb(true, biobankId));

        if (biobankId != null) {
            getPagination().setIdentifier(biobankId);
        }

        initiatePagination();
        if (getOrderParam() == null) {
            getPagination().setOrderParam("institutionId");
        }
        getPagination().setEvent("display");
        getPagination().setSource(patientService.getSorted(biobankId,
                getPagination().getOrderParam(),
                getPagination().getDesc()));
        return new ForwardResolution(BIOBANK_DETAIL_PATIENTS);
    }
}
