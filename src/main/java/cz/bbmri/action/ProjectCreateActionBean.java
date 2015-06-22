package cz.bbmri.action;

import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.*;
import cz.bbmri.entity.*;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.DateTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@HttpCache(allow = false)
@Wizard(startEvents = {"first"})
@UrlBinding("/project-create/{$event}")
@PermitAll
public class ProjectCreateActionBean extends ComponentActionBean {

    @SpringBean
    private ProjectDAO projectDAO;

    @SpringBean
    private ProjectUserDAO projectUserDAO;

    @SpringBean
    private AttachmentDAO attachmentDAO;

    @SpringBean
    private UserDAO userDAO;

    @SpringBean
    private NotificationDAO notificationDAO;

    @SpringBean
    private RoleDAO roleDAO;

    @ValidateNestedProperties(value = {
            @Validate(on = {"third"},
                    field = "name",
                    required = true),
            @Validate(on = {"third"}, field = "principalInvestigator",
                    required = true),
            @Validate(on = {"third"}, field = "homeInstitution",
                    required = true),
            @Validate(on = {"fourth"},
                    field = "fundingOrganization",
                    required = true),
            @Validate(on = {"fourth"},
                    field = "approvedBy",
                    required = true),
            @Validate(on = {"fourth"}, field = "approvalStorage",
                    required = true),
            @Validate(converter = DateTypeConverter.class, on = {"fourth"}, field = "approvalDate",
                    required = true),
            @Validate(on = {"fifth"}, field = "annotation",
                    required = true)

    })
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    /* First step. Introduction */
    @DontValidate
    @DefaultHandler
    @HandlesEvent("first")
    public Resolution first() {
        return new ForwardResolution(View.Project.CREATE_FIRST);
    }

    /* Second step. */
    @HandlesEvent("second")
    public Resolution second() {
        return new ForwardResolution(View.Project.CREATE_SECOND);
    }

    /* Second step. */
    @HandlesEvent("backToSecond")
    public Resolution backToSecond() {
        return new ForwardResolution(View.Project.CREATE_SECOND);
    }

    /* Confirm second step. */
    @HandlesEvent("third")
    public Resolution third() {
        return new ForwardResolution(View.Project.CREATE_THIRD);
    }

    /* Third step. */
    @HandlesEvent("backToThird")
    public Resolution backToThird() {
        return new ForwardResolution(View.Project.CREATE_THIRD);
    }

    /* Confirm third step. */
    @HandlesEvent("fourth")
    public Resolution fourth() {
        return new ForwardResolution(View.Project.CREATE_FOURTH);
    }

    /* 4th step. */
    @HandlesEvent("backToFourth")
    public Resolution backToFourth() {
        return new ForwardResolution(View.Project.CREATE_FOURTH);
    }

    /* Confirm 4th step. */
    @HandlesEvent("fifth")
    public Resolution fifth() {
        return new ForwardResolution(View.Project.CREATE_FIFTH);
    }


    /* 5th step. Confirm.*/
    @HandlesEvent("confirm")
    public Resolution confirm() {

        if (!project.getClinicalTrial()) {
            project.setEudraCtNumber(null);
        }

        projectDAO.save(project);

        ProjectUser projectUser = new ProjectUser();
        projectUser.setPermission(Permission.MANAGER);
        projectUser.setProject(project);
        projectUser.setUser(getLoggedUser());

        projectUserDAO.save(projectUser);

        User user = getLoggedUser();
        user.nominateProjectTeamMember();
        userDAO.save(user);

        Role administrator = roleDAO.get(Role.ADMIN.getId());

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.action.ProjectCreateActionBean.created",
                project.getName(), getLoggedUser());

        notificationDAO.create(administrator.getUser(),
                NotificationType.PROJECT_DETAIL, locMsg, project.getId());

        return new RedirectResolution(ProjectActionBean.class, "myProjects");
    }

    public String getToday() {
        String datePattern = getDatePattern();
        SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);

        return dateFormat.format(new Date());
    }

}
