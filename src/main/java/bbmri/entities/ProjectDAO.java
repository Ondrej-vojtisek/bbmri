package bbmri.entities;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectDAO {

    public void addProject(Project project);

    public void removeProject(Project project);

    public void updateProject(Project project);

    public List<Project> getAllProjects();

}
