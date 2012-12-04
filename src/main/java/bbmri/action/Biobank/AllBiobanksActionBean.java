package bbmri.action.Biobank;

import bbmri.action.BasicActionBean;
import bbmri.entities.Biobank;
import bbmri.entities.Project;
import bbmri.entities.Researcher;
import bbmri.service.BiobankService;
import bbmri.service.ResearcherService;
import bbmri.serviceImpl.BiobankServiceImpl;
import bbmri.serviceImpl.ResearcherServiceImpl;
import net.sourceforge.stripes.action.*;

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
    private List<Researcher> researchers;
    private Researcher administrator;
    private Researcher ethicalCommittee;


    public List<Researcher> getResearchers() {
        researchers = getResearcherService().getAll();
        return researchers;
    }

    public Researcher getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Researcher administrator) {
        this.administrator = administrator;
    }

    public Researcher getEthicalCommittee() {
        return ethicalCommittee;
    }

    public void setEthicalCommittee(Researcher ethicalCommittee) {
        this.ethicalCommittee = ethicalCommittee;
    }

    public List<Biobank> getBiobanks() {
        return getBiobankService().getAll();
    }

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    @DefaultHandler
    public Resolution display() {
        getResearchers();
        biobanks = getBiobankService().getAll();
        return new ForwardResolution("/allBiobanks.jsp");
    }

    public Resolution edit() {
        biobank = getContext().getLoggedResearcher().getBiobank();
        getContext().setBiobank(biobank);

        return new ForwardResolution("/editBiobank.jsp");
    }
}
