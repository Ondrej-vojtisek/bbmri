package bbmri.action.Biobank;

import bbmri.action.BasicActionBean;
import bbmri.action.MyActionBeanContext;
import bbmri.entities.Biobank;
import bbmri.entities.User;
import bbmri.service.BiobankService;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
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
            @Validate(on = {"create"}, field = "name", required = true),
            @Validate(on = {"create"}, field = "address", required = true),
    })
    private Biobank biobank;
    private List<User> users;
    private User administrator;

    @SpringBean
    private UserService userService;

    @SpringBean
    private BiobankService biobankService;

    public List<User> getUsers() {
            users = userService.getAll();
            return users;
        }

    public User getAdministrator() {
        return administrator;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public Resolution create() {
        User resDB = userService.getById(administrator.getId());
        if (resDB.getBiobank() != null) {
            refreshLoggedUser();
            return new ForwardResolution("/biobank_all.jsp");
        }
        biobankService.create(biobank, administrator.getId());
        return new ForwardResolution("/biobank_all.jsp");
    }

    public void refreshLoggedUser() {
          getContext().setLoggedUser(userService.getById(getLoggedUser().getId()));
      }

}
