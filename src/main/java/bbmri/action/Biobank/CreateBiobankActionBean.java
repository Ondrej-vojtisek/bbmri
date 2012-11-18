package bbmri.action.Biobank;

import bbmri.action.BasicActionBean;
import bbmri.entities.Biobank;
import bbmri.entities.Researcher;
import net.sourceforge.stripes.action.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 23:01
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/createbiobank/{$event}/{biobank.id}")
public class CreateBiobankActionBean extends BasicActionBean {
    private Biobank biobank;
    private List<Researcher> researchers;
    private Researcher administrator;
    private Researcher ethicalCommittee;

     public List<Researcher> getResearchers(){
       researchers =  getResearcherService().getAll();
        return researchers;
    }

    public Researcher getAdministrator() {return administrator;}
    public void setAdministrator(Researcher administrator) {this.administrator = administrator;}

    public Researcher getEthicalCommittee() {return ethicalCommittee;}

    public void setEthicalCommittee(Researcher ethicalCommittee) {this.ethicalCommittee = ethicalCommittee;}


    public Biobank getBiobank() {return biobank;}
    public void setBiobank(Biobank biobank) { this.biobank = biobank;}

    public Resolution create() {
        getBiobankService().create(biobank, administrator.getId(), ethicalCommittee.getId());
        return new ForwardResolution("/allBiobanks.jsp");
    }

}
