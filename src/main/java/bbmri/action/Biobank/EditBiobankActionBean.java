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
 * Time: 22:22
 * To change this template use File | Settings | File Templates.
 */

@UrlBinding("/editbiobank/{$event}/{biobank.id}")
public class EditBiobankActionBean extends BasicActionBean {

    @ValidateNestedProperties(value = {
            @Validate(on = {"update"}, field = "name", required = true),
            @Validate(on = {"update"}, field = "address", required = true),
    })
    private Biobank biobank;
    private List<User> users;
    private User administrator;
    private List<Long> selected;
    private User user;
    private List<Long> selectedApprove;

    @SpringBean
    private UserService userService;

    @SpringBean
    private BiobankService biobankService;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Long> getSelected() {
        return selected;
    }

    public void setSelected(List<Long> selected) {
        this.selected = selected;
    }

    public List<User> getUsers() {
        users = userService.getAll();
        return users;
    }

    public List<User> getAdministrators() {
        return biobankService.getAllAdministrators(getBiobank().getId());
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public Biobank getBiobank() {
        if (biobank == null) {
            biobank = getContext().getLoggedUser().getBiobank();
        }
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public List<User> getFreeUsers() {
          return userService.getNonAdministratorUsers();
      }

    public List<Long> getSelectedApprove() {
        return selectedApprove;
    }

    public void setSelectedApprove(List<Long> selectedApprove) {
        this.selectedApprove = selectedApprove;
    }

    @DefaultHandler
    public Resolution zobraz() {
        return new ForwardResolution("/biobank_edit.jsp");
    }

    public Resolution update() {
        biobankService.update(biobank);
        return new ForwardResolution("/biobank_all.jsp");
    }

    public Resolution removeAll() {
           Integer removed = 0;
           if (selected != null) {
               for (Long id : selected) {
                   if (id.equals(getContext().getLoggedUser().getId())) {
                       /*you can't remove yourself*/
                       return new ForwardResolution("/biobank_all.jsp");
                   }
                   biobankService.removeAdministratorFromBiobank(id, getBiobank().getId());
                   removed++;
               }
           }
           getContext().getMessages().add(
                                    new SimpleMessage("{0} administrators removed", removed)
                            );
           users = biobankService.getAllAdministrators(getBiobank().getId());
           refreshLoggedUser();
           return new ForwardResolution("/biobank_all.jsp");
       }

    public Resolution changeOwnership() {
           biobankService.changeOwnership(getContext().getProject().getId(), user.getId());
           getContext().getMessages().add(
                                 new SimpleMessage("Ownership of biobank was changed")
                         );
           refreshLoggedUser();
           return new ForwardResolution("/biobank_all.jsp");
       }

    public Resolution assignAll() {
           Integer assigned = 0;
           if (selectedApprove != null) {
               for (Long userId : selectedApprove) {
                   biobankService.assignAdministrator(userId, getBiobank().getId());
                   assigned++;
               }
           }
           getContext().getMessages().add(
                          new SimpleMessage("{0} users assigned", assigned)
                  );
           return new ForwardResolution("/biobank_all.jsp");
       }


    public void refreshLoggedUser() {
             getContext().setLoggedUser(userService.getById(getLoggedUser().getId()));
         }
}
