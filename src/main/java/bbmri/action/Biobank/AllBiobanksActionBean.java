package bbmri.action.Biobank;

import bbmri.action.BasicActionBean;
import bbmri.entities.Biobank;
import bbmri.entities.User;
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
    private List<User> users;
    private User administrator;
    private User ethicalCommittee;


    public List<User> getUsers() {
        users = getUserService().getAll();
        return users;
    }

    public User getAdministrator() {
        return administrator;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public User getEthicalCommittee() {
        return ethicalCommittee;
    }

    public void setEthicalCommittee(User ethicalCommittee) {
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
        getUsers();
        biobanks = getBiobankService().getAll();
        return new ForwardResolution("/allBiobanks.jsp");
    }

    public Resolution edit() {
        biobank = getContext().getLoggedUser().getBiobank();
        getContext().setBiobank(biobank);

        return new ForwardResolution("/editBiobank.jsp");
    }
}
