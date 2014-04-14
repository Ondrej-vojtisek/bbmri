package cz.bbmri.action.base;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.enumeration.ProjectState;
import cz.bbmri.service.BiobankAdministratorService;
import cz.bbmri.service.BiobankService;
import cz.bbmri.service.ProjectService;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.1.14
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public abstract class PermissionActionBean<T> extends ComponentActionBean<T> {

    @SpringBean
    private BiobankService biobankService;

    @SpringBean
    private BiobankAdministratorService biobankAdministratorService;

    @SpringBean
    private ProjectService projectService;


    protected Long biobankId;

    public Long getBiobankId() {
        return biobankId;
    }

    public void setBiobankId(Long biobankId) {
        this.biobankId = biobankId;
    }

    /* Project identifier */
    protected Long projectId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    private Project project;

    public Project getProject() {

        if (project == null) {
            if (projectId != null) {
                project = projectService.get(projectId);
            }
        }

        return project;
    }

    @ValidateNestedProperties(value = {
            @Validate(field = "name",
                    required = true, on = "update"),
            @Validate(field = "address",
                    required = true, on = "update")
    })
    private Biobank biobank;

    public Biobank getBiobank() {
        if (biobank == null) {
            if (biobankId != null) {
                biobank = biobankService.get(biobankId);
            }
        }
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean getAllowedBiobankManager() {
        return biobankAdministratorService.hasPermission(Permission.MANAGER, biobankId, getContext().getMyId());
    }

    public boolean getAllowedBiobankEditor() {
        return biobankAdministratorService.hasPermission(Permission.EDITOR, biobankId, getContext().getMyId());
    }

    public boolean getAllowedBiobankExecutor() {
        logger.debug("AllowedBiobankExecutor_ ");
        logger.debug("BiobankId: " + biobankId);

        logger.debug("AllowedBiobankExecutor_ " + biobankAdministratorService.hasPermission(Permission.EXECUTOR, biobankId, getContext().getMyId()));
        return biobankAdministratorService.hasPermission(Permission.EXECUTOR, biobankId, getContext().getMyId());
    }

    public boolean getAllowedBiobankVisitor() {
        return biobankAdministratorService.hasPermission(Permission.VISITOR, biobankId, getContext().getMyId());
    }


    /* When the project is marked as finished than it can't be edited or changes in any way */
    public boolean getAllowedProjectManager() {
        return projectService.hasPermission(Permission.MANAGER, projectId, getContext().getMyId()) && !isFinished();
    }

    public boolean getAllowedProjectEditor() {
        return projectService.hasPermission(Permission.EDITOR, projectId, getContext().getMyId()) && !isFinished();
    }

    public boolean getAllowedProjectExecutor() {
        logger.debug("GetAllowedProjectExecutor");
        logger.debug("ProjectId: " + projectId);

        return projectService.hasPermission(Permission.EXECUTOR, projectId, getContext().getMyId()) && !isFinished();
    }

    public boolean getAllowedProjectVisitor() {
        return projectService.hasPermission(Permission.VISITOR, projectId, getContext().getMyId());
    }


    public boolean getIsNew() {
        if (getProject() == null) {
            return false;
        }
        return getProject().getProjectState().equals(ProjectState.NEW);
    }

    public boolean getIsApproved() {
        if (getProject() == null) {
            return false;
        }
        return getProject().getProjectState().equals(ProjectState.APPROVED);
    }

    public boolean getIsStarted() {
        if (getProject() == null) {
            return false;
        }
        return getProject().getProjectState().equals(ProjectState.STARTED);
    }

    private boolean isFinished() {
        if (getProject() == null) {
            return false;
        }
        return getProject().getProjectState().equals(ProjectState.FINISHED);
    }

    public boolean getIsMyAccount() {
        return getContext().getMyId().equals(userId);
    }

}
