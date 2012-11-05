package bbmri.DAO;

import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.Researcher;

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

    public void create(Project project, EntityManager em);

    public void remove(Project project, EntityManager em);

    public void update(Project project, EntityManager em);

    public Project get(Long id, EntityManager em);

    public List<Project> getAll(EntityManager em);

    public List<Project> getAllByResearcher(Researcher researcher);

    public List<Researcher> getAllResearchersByProject(Project project);

    public void assignResearcherToProject(Researcher researcher, Project project);

    public void removeResearcherFromProject(Researcher researcher, Project project);

    public boolean projectContainsResearcher(Researcher researcher, Project project);

    public List<Project> getAllByProjectState(ProjectState projectState, EntityManager em);

}
