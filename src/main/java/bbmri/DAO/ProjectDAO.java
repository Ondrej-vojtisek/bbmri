package bbmri.DAO;

import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.User;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectDAO extends DAO<Project> {

    List<Project> getAllByUser(User user);

    List<User> getAllUsersByProject(Project project);

    List<Project> getAllByProjectState(ProjectState projectState);

}
