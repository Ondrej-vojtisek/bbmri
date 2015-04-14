package cz.bbmri.action.base;

import cz.bbmri.dao.BiobankDAO;
import cz.bbmri.dao.BiobankUserDAO;
import cz.bbmri.dao.ProjectDAO;
import cz.bbmri.dao.ProjectUserDAO;
import cz.bbmri.entity.Biobank;
import cz.bbmri.entity.Permission;
import cz.bbmri.entity.Project;
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

public abstract class AuthorizationActionBean extends ComponentActionBean {

    @SpringBean
    private BiobankDAO biobankDAO;

    @SpringBean
    private ProjectDAO projectDAO;

    @SpringBean
    private BiobankUserDAO biobankUserDAO;

    @SpringBean
    private ProjectUserDAO projectUserDAO;

    private Integer authBiobankId;

    private Long authProjectId;

    private Long userId;

    public void setAuthBiobankId(Integer biobankId) {
        this.authBiobankId = biobankId;
    }

    public void setAuthProjectId(Long projectId) {
        this.authProjectId = projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean getBiobankManager() {
        return biobankUserDAO.hasPermission(Permission.MANAGER, biobankDAO.get(authBiobankId), getLoggedUser());
    }

    public boolean getBiobankEditor() {
        return biobankUserDAO.hasPermission(Permission.EDITOR,  biobankDAO.get(authBiobankId), getLoggedUser());
    }

    public boolean getBiobankExecutor() {
        return biobankUserDAO.hasPermission(Permission.EXECUTOR,  biobankDAO.get(authBiobankId), getLoggedUser());
    }

    public boolean getBiobankVisitor() {
        return biobankUserDAO.hasPermission(Permission.VISITOR,  biobankDAO.get(authBiobankId), getLoggedUser());
    }

    public boolean getProjectManager() {
        return projectUserDAO.hasPermission(Permission.MANAGER, projectDAO.get(authProjectId), getLoggedUser());
    }

    public boolean getProjectEditor() {
        return projectUserDAO.hasPermission(Permission.EDITOR, projectDAO.get(authProjectId), getLoggedUser());
    }

    public boolean getProjectExecutor() {
        return projectUserDAO.hasPermission(Permission.EXECUTOR, projectDAO.get(authProjectId), getLoggedUser());
    }

    public boolean getProjectVisitor() {
        return projectUserDAO.hasPermission(Permission.VISITOR, projectDAO.get(authProjectId), getLoggedUser());
    }

    public boolean getIsMyAccount() {
        return getContext().getMyId().equals(userId);
    }

}
