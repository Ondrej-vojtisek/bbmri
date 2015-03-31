package cz.bbmri.action.base;

import cz.bbmri.dao.BiobankDAO;
import cz.bbmri.dao.BiobankUserDAO;
import cz.bbmri.dao.ProjectDAO;
import cz.bbmri.dao.ProjectUserDAO;
import cz.bbmri.entity.Permission;
import cz.bbmri.entity.Project;
import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public abstract class ProjectPermissionActionBean extends ComponentActionBean {

    @SpringBean
    private ProjectDAO projectDAO;

    @SpringBean
    private ProjectUserDAO projectUserDAO;

    private Long id;

    protected Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        if (project == null) {
            if (id != null) {
                project = projectDAO.get(id);
            }
        }

        return project;
    }

    public boolean getProjectManager() {
        return projectUserDAO.hasPermission(Permission.MANAGER, getProject(), getLoggedUser());
    }

    public boolean getProjectEditor() {
        return projectUserDAO.hasPermission(Permission.EDITOR, getProject(), getLoggedUser());
    }

    public boolean getProjectExecutor() {
        return projectUserDAO.hasPermission(Permission.EXECUTOR, getProject(), getLoggedUser());
    }

    public boolean getProjectVisitor() {
        return projectUserDAO.hasPermission(Permission.VISITOR, getProject(), getLoggedUser());
    }

}
