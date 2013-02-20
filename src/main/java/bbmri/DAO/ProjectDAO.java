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
public interface ProjectDAO {

    void create(Project project);

    void remove(Project project);

    void update(Project project);

    Project get(Long id);

    List<Project> getAll();

    List<Project> getAllByUser(User user);

    List<User> getAllUsersByProject(Project project);

    List<Project> getAllByProjectState(ProjectState projectState);

    Integer getCount();

    public byte[] getData(Project project);

}
