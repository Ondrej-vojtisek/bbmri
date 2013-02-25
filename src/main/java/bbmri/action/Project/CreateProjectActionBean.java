package bbmri.action.Project;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 20:43
 * To change this template use File | Settings | File Templates.
 */

import bbmri.action.BasicActionBean;
import bbmri.entities.Project;
import bbmri.service.ProjectService;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

@UrlBinding("/createproject/{$event}/{project.id}")
public class CreateProjectActionBean extends BasicActionBean {

    @ValidateNestedProperties(value = {
            @Validate(on = {"create"}, field = "name", required = true,
                    minlength = 5, maxlength = 255),
            @Validate(on = {"create"}, field = "description", required = true,
                    minlength = 5, maxlength = 255),
            @Validate(on = {"create"}, field = "fundingOrganization", required = true,
                    minlength = 5, maxlength = 255),
    })

    private Project project;

    @SpringBean
    private UserService userService;

    @SpringBean
    private ProjectService projectService;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Resolution create() {
        projectService.create(project, getLoggedUser());
        getContext().setProject(project);
        refreshLoggedUser();
        return new ForwardResolution("/project_create_upload_agreement.jsp");
    }

    public void refreshLoggedUser() {
        getContext().setLoggedUser(userService.getById(getLoggedUser().getId()));
    }

}
