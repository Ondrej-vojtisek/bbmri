package cz.bbmri.service;

import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.enumeration.ProjectState;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectService extends BasicService<Project>, PermissionService<Project, ProjectAdministrator> {

    List<Project> allOrderedBy(String orderByParam, boolean desc);

    Project create(Project project, Long userId);

    boolean approve(Long projectId, Long userId);

    boolean deny(Long projectId, Long userId);

    List<Project> getAllByProjectState(ProjectState projectState);

    List<Project> getAllByUserAndProjectState(ProjectState projectState, Long userId);

    List<Project> getEagerByUserWithRequests(Long userId);

    Project changeState(Long projectId, ProjectState projectState);

    List<Project> getMyProjectsSorted(Long userId, String orderByParam, boolean desc);

    List<Project> getProjectsBySample(Long sampleId, String orderByParam, boolean desc);

    boolean hasPermission(Permission permission, Long objectId, Long userId);

    boolean changeAdministratorPermission(Long objectAdministratorId, Permission permission, ValidationErrors errors,
                                          Long loggedUserId);

    boolean removeAdministrator(Long objectAdministratorId, ValidationErrors errors, Long loggedUserId);

    boolean assignAdministrator(Long objectId, Long newAdministratorId, Permission permission, ValidationErrors errors,
                                Long loggedUserId);

    boolean approveProject(Long projectId, Long loggedUserId, ValidationErrors errors);

    boolean denyProject(Long projectId, Long loggedUserId, ValidationErrors errors);

    Project createProject(Project project,
                          Long loggedUserId,
                          ValidationErrors errors);

    boolean updateProject(Project project, Long loggedUserId);

    boolean removeProject(Long projectId, ValidationErrors errors, Long loggedUserId);

    List<Project> getProjects(Long userId, ProjectState projectState);

    boolean markAsFinished(Long projectId, Long loggedUserId);

    ProjectAdministrator getProjectAdministrator(Long projectAdministratorId);

}
