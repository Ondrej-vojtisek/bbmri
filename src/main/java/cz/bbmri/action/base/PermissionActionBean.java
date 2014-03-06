package cz.bbmri.action.base;

import cz.bbmri.entities.Project;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.enumeration.ProjectState;
import cz.bbmri.facade.BiobankFacade;
import cz.bbmri.facade.ProjectFacade;
import cz.bbmri.facade.UserFacade;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.1.14
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public abstract class PermissionActionBean<T> extends ComponentActionBean<T> {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private BiobankFacade biobankFacade;

    @SpringBean
    private ProjectFacade projectFacade;

    @SpringBean
    private UserFacade userFacade;

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

                project = projectFacade.get(projectId);


            }
        }

        return project;
    }



    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean getAllowedBiobankManager() {
        return biobankFacade.hasPermission(Permission.MANAGER, biobankId, getContext().getMyId());
    }

    public boolean getAllowedBiobankEditor() {
        return biobankFacade.hasPermission(Permission.EDITOR, biobankId, getContext().getMyId());
    }

    public boolean getAllowedBiobankExecutor() {
        logger.debug("AllowedBiobankExecutor_ ");
        logger.debug("BiobankId: " + biobankId);

        logger.debug("AllowedBiobankExecutor_ " + biobankFacade.hasPermission(Permission.EXECUTOR, biobankId, getContext().getMyId()));
        return biobankFacade.hasPermission(Permission.EXECUTOR, biobankId, getContext().getMyId());
    }

    public boolean getAllowedBiobankVisitor() {
        return biobankFacade.hasPermission(Permission.VISITOR, biobankId, getContext().getMyId());
    }


    /* When the project is marked as finished than it can't be edited or changes in any way */
    public boolean getAllowedProjectManager() {
        return projectFacade.hasPermission(Permission.MANAGER, projectId, getContext().getMyId()) && !isFinished();
    }

    public boolean getAllowedProjectEditor() {
        return projectFacade.hasPermission(Permission.EDITOR, projectId, getContext().getMyId()) && !isFinished();
    }

    public boolean getAllowedProjectExecutor() {
        logger.debug("GetAllowedProjectExecutor");
        logger.debug("ProjectId: " + projectId);

        return projectFacade.hasPermission(Permission.EXECUTOR, projectId, getContext().getMyId()) && !isFinished();
    }

    public boolean getAllowedProjectVisitor() {
        return projectFacade.hasPermission(Permission.VISITOR, projectId, getContext().getMyId());
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
