package cz.bbmri.service;

import cz.bbmri.entities.ProjectAdministrator;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.11.13
 * Time: 16:43
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectAdministratorService extends BasicService<ProjectAdministrator> {

    ProjectAdministrator get(Long projectId, Long userId);

    boolean contains(Long projectId, Long userId);
}
