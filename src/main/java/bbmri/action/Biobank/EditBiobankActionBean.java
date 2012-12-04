package bbmri.action.Biobank;

import bbmri.action.BasicActionBean;
import bbmri.entities.Biobank;
import bbmri.entities.Researcher;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 22:22
 * To change this template use File | Settings | File Templates.
 */

@UrlBinding("/editbiobank/{$event}/{biobank.id}")
public class EditBiobankActionBean extends BasicActionBean {

        @ValidateNestedProperties(value = {
            @Validate(on = {"update"}, field = "name", required = true,
                    minlength = 5, maxlength = 50),
            @Validate(on = {"update"}, field = "address", required = true,
                    minlength = 5, maxlength = 100),
    })
    private Biobank biobank;
    private List<Researcher> researchers;
    private Researcher administrator;
    private Researcher ethicalCommittee;


    public List<Researcher> getResearchers() {
        researchers = getResearcherService().getAll();
        return researchers;
    }

    public Researcher getAdministrator() {
        administrator = getBiobank().getAdministrator();
        return administrator;

    }

    public void setAdministrator(Researcher administrator) {
        this.administrator = administrator;
    }

    public Researcher getEthicalCommittee() {
        ethicalCommittee = getBiobank().getEthicalCommittee();
        return ethicalCommittee;
    }

    public void setEthicalCommittee(Researcher ethicalCommittee) {
        this.ethicalCommittee = ethicalCommittee;
    }


    public Biobank getBiobank() {
        if (biobank == null) {
            biobank = getContext().getLoggedResearcher().getBiobank();
        }
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    @DefaultHandler
    public Resolution zobraz() {
        return new ForwardResolution("/editBiobank.jsp");
    }

    public Resolution update() {
        getBiobankService().update(biobank);
        return new ForwardResolution("/allBiobanks.jsp");
    }

    public Resolution changeAdministrator() {

        System.out.println("LoggedResearcher: " + getLoggedResearcher());
        System.out.println("Administrator: " + administrator);

        getBiobankService().updateAdministrator(biobank.getId(), administrator.getId());
        return new ForwardResolution("/allBiobanks.jsp");
    }

    public Resolution changeEthicalCommittee() {

        System.out.println("LoggedResearcher: " + getLoggedResearcher());
        System.out.println("Committee: " + ethicalCommittee);

        getBiobankService().updateEthicalCommittee(biobank.getId(), ethicalCommittee.getId());
        return new ForwardResolution("/allBiobanks.jsp");
    }
}
