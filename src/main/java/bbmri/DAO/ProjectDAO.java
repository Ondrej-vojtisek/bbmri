package bbmri.DAO;

import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.User;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectDAO {

    void create(Project project, EntityManager em);

    void remove(Project project, EntityManager em);

    void update(Project project, EntityManager em);

    Project get(Long id, EntityManager em);

    List<Project> getAll(EntityManager em);

    List<Project> getAllByUser(User user);

    List<User> getAllUsersByProject(Project project);

    void assignUserToProject(User user, Project project);

    void removeUserFromProject(User user, Project project);

    boolean projectContainsUser(User user, Project project);

    List<Project> getAllByProjectState(ProjectState projectState, EntityManager em);

}
