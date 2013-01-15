package bbmri.service;

import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.User;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectService {

    public Project create(Project project, User user);

    public void remove(Long id);

    public Project update(Project project);

    public List<Project> getAll();

    public List<Project> getAllByUser(Long id);

    public User assignUser(Long userId, Long projectId);

    public User removeUserFromProject(Long userId, Long projectId);

    public List<User> getAllAssignedUsers(Long projectId);

    public void approve(Long id);

    public void approve(Long projectId, Long userId);

    public List<Project> getAllByProjectState(ProjectState projectState);

    public List<Project> getAllApprovedByUser(User user);

    public List<Project> getAllWhichUserAdministrate(Long id);

    public Project getById(Long id);

    public List<User> getAllNotAssignedUsers(Long id);

    public Project changeOwnership(Long projectId, Long newOwnerId);

    public Integer getCount();
}
