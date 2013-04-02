package bbmri.action.Biobank;

import bbmri.action.BasicActionBean;
import bbmri.action.MyActionBeanContext;
import bbmri.entities.Biobank;
import bbmri.entities.User;
import bbmri.service.BiobankService;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 20:33
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/allbiobanks/{$event}/{biobank.id}")
public class AllBiobanksActionBean extends BasicActionBean {

    private Biobank biobank;
    private List<Biobank> biobanks;

    @SpringBean
    private UserService userService;

    @SpringBean
    private BiobankService biobankService;


    public List<Biobank> getBiobanks() {
        return biobankService.getAll();
    }

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    @DefaultHandler
    public Resolution display() {
        refreshLoggedUser();
        biobanks = biobankService.getAll();
        return new ForwardResolution("/biobank_all.jsp");
    }

    public Resolution edit() {
        biobank = getContext().getLoggedUser().getBiobank();
        getContext().setBiobank(biobank);

        return new ForwardResolution("/biobank_edit.jsp");
    }

    public void refreshLoggedUser() {
             getContext().setLoggedUser(userService.getById(getLoggedUser().getId()));
         }
}
