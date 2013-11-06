package bbmri.service;

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
public interface ProjectService extends BasicService<Project> {

    Project create(Project project, Long userId);

    void approve(Long projectId, Long userId);

    void deny(Long projectId, Long userId);

    List<Project> getAllByProjectState(ProjectState projectState);

    List<Project> getEagerByUserWithRequests(Long userId);

    Project eagerGet(Long id, boolean users, boolean requestGroups, boolean attachments, boolean sampleQuestions);

    // For tests only
    // void assignUserToProject(User userDB, Project projectDB, Permission permission);

    // For tests only
    // void removeUserFromProject(User userDB, Project projectDB);

    void removeAdministrator(Long projectId, Long loggedUserId, Long userId);

    void changeAdministratorPermission(Long projectId, Long loggedUserId, Long userId, Permission permission);

    User assignAdministrator(Long userId, Long projectId, Permission permission);
}
