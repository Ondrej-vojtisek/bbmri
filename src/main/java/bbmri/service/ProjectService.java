package bbmri.service;

import bbmri.entities.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectService  extends BasicService<Project> {

     Project create(Project project, Long userId);

//     List<Project> getAllByUser(Long id);

     User assignUser(Long userId, Long projectId);

     User removeUserFromProject(Long userId, Long projectId);

    // List<User> getAllAssignedUsers(Long projectId);

    // void approve(Long id);

     void approve(Long projectId, Long userId);

     void deny(Long projectId, Long userId);

     List<Project> getAllByProjectState(ProjectState projectState);

    // List<Project> getAllApprovedByUser(Long userId);

     List<Project> getAllWhichUserAdministrate(Long userId);

     List<User> getAllNotAssignedUsers(Long projectId);

     Project changeOwnership(Long projectId, Long newOwnerId);

    List<Project> getAllByUserWithRequests(Long userId);

    Project eagerGet(Long id, boolean users, boolean requestGroups, boolean attachments, boolean sampleQuestions);
}
