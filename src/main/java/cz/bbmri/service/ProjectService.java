package cz.bbmri.service;

import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.enumeration.ProjectState;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectService extends BasicService<Project>, PermissionService<Project, ProjectAdministrator> {

    Project create(Project project, Long userId);

    boolean approve(Long projectId, Long userId);

    boolean deny(Long projectId, Long userId);

    List<Project> getAllByProjectState(ProjectState projectState);

    List<Project> getAllByUserAndProjectState(ProjectState projectState, Long userId);

    List<Project> getEagerByUserWithRequests(Long userId);

  //  Project eagerGet(Long id, boolean users, boolean attachments, boolean sampleRequests);

    Project changeState(Long projectId, ProjectState projectState);

}
