package cz.bbmri.action.biobank;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.facade.BiobankFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 11.3.14
 * Time: 13:30
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/biobank/patients/{$event}/{biobankId}")
public class BiobankPatientsActionBean extends PermissionActionBean<Patient> {

    @SpringBean
    private BiobankFacade biobankFacade;

    public static Breadcrumb getBreadcrumb(boolean active, Long biobankId) {
        return new Breadcrumb(BiobankPatientsActionBean.class.getName(),
                "display", false, "cz.bbmri.action.biobank.BiobankActionBean.patients",
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
        getPagination().setSource(biobankFacade.getSortedPatients(biobankId,
                getPagination().getOrderParam(),
                getPagination().getDesc()));
        return new ForwardResolution(BIOBANK_DETAIL_PATIENTS);
    }
}
