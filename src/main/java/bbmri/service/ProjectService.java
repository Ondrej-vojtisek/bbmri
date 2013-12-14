package bbmri.service;

import bbmri.entities.ProjectAdministrator;
import bbmri.entities.User;
import bbmri.entities.enumeration.Permission;
import bbmri.entities.Project;
import bbmri.entities.enumeration.ProjectState;

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

    List<Project> getEagerByUserWithRequests(Long userId);

    Project eagerGet(Long id, boolean users, boolean requestGroups, boolean attachments, boolean sampleQuestions);

    Project changeState(Long projectId, ProjectState projectState);

}
