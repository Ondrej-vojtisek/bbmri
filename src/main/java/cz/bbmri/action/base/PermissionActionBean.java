package cz.bbmri.action.base;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.enumeration.ProjectState;
import cz.bbmri.service.BiobankAdministratorService;
import cz.bbmri.service.BiobankService;
import cz.bbmri.service.ProjectAdministratorService;
import cz.bbmri.service.ProjectService;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

/**
 * All methods with instance based security manager (e.g. permission to biobank or project) must extend this class.
 * They will probably need method like getAllowedBiobankManager etc,...
 * Remember that in EL expressions these methods are defined without "get" -> allowedBiobankManager.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public abstract class PermissionActionBean<T> extends ComponentActionBean<T> {

    @SpringBean
    private BiobankService biobankService;

    @SpringBean
    private BiobankAdministratorService biobankAdministratorService;

    @SpringBean
    private ProjectService projectService;

    @SpringBean
    private ProjectAdministratorService projectAdministratorService;

    public Long biobankId;

    public Long getBiobankId() {
        return biobankId;
    }

    public void setBiobankId(Long biobankId) {
        this.biobankId = biobankId;
    }

    /* Project identifier */
    public Long projectId;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    private Project project;

    // Must be public because of .jsp access
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

    // must be public due to .jsp
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

    public Long userId;

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
         return biobankAdministratorService.hasPermission(Permission.EXECUTOR, biobankId, getContext().getMyId());
    }

    public boolean getAllowedBiobankVisitor() {

        System.err.println("BiobankId: " + biobankId);

        return biobankAdministratorService.hasPermission(Permission.VISITOR, biobankId, getContext().getMyId());
    }


    /* When the project is marked as finished than it can't be edited or changes in any way */
    public boolean getAllowedProjectManager() {
        return projectAdministratorService.hasPermission(Permission.MANAGER, projectId, getContext().getMyId()) && !isFinished();
    }

    public boolean getAllowedProjectEditor() {
        return projectAdministratorService.hasPermission(Permission.EDITOR, projectId, getContext().getMyId()) && !isFinished();
    }

    public boolean getAllowedProjectExecutor() {
          return projectAdministratorService.hasPermission(Permission.EXECUTOR, projectId, getContext().getMyId()) && !isFinished();
    }

    public boolean getAllowedProjectVisitor() {
        return projectAdministratorService.hasPermission(Permission.VISITOR, projectId, getContext().getMyId());
    }


    public boolean getIsNew() {
        return getProject() != null && getProject().getProjectState().equals(ProjectState.NEW);
    }

    public boolean getIsApproved() {
        if (getProject() == null) {
            return false;
        }
        return getProject().getProjectState().equals(ProjectState.CONFIRMED);
    }

    public boolean getIsStarted() {
        return getProject() != null && getProject().getProjectState().equals(ProjectState.STARTED);
    }

    public boolean isFinished() {
        if (getProject() == null) {
            return false;
        }
        return getProject().getProjectState().equals(ProjectState.FINISHED);
    }

    public boolean getIsMyAccount() {
        return getContext().getMyId().equals(userId);
    }

}
