package bbmri.action.Biobank;

import bbmri.action.BasicActionBean;
import bbmri.entities.Biobank;
import bbmri.entities.User;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

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
      @ValidateNestedProperties(value = {
            @Validate(on = {"create"}, field = "name", required = true,
                    minlength = 5, maxlength = 50),
            @Validate(on = {"create"}, field = "address", required = true,
                    minlength = 5, maxlength = 100),
    })
    private Biobank biobank;
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


    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public Resolution create() {
        User resDB = getUserService().getById(administrator.getId());
        if (resDB.getBiobank() != null) {
            return new ForwardResolution("/allBiobanks.jsp");
        }
        resDB = getUserService().getById(ethicalCommittee.getId());
        if (resDB.getEthicalCommitteeOfBiobank() != null) {
            return new ForwardResolution("/allBiobanks.jsp");
        }
        getBiobankService().create(biobank, administrator.getId(), ethicalCommittee.getId());
        return new ForwardResolution("/allBiobanks.jsp");
    }

}
