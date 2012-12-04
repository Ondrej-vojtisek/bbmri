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

    void create(Project project, EntityManager em);

    void remove(Project project, EntityManager em);

    void update(Project project, EntityManager em);

    Project get(Long id, EntityManager em);

    List<Project> getAll(EntityManager em);

    List<Project> getAllByResearcher(Researcher researcher);

    List<Researcher> getAllResearchersByProject(Project project);

    void assignResearcherToProject(Researcher researcher, Project project);

    void removeResearcherFromProject(Researcher researcher, Project project);

    boolean projectContainsResearcher(Researcher researcher, Project project);

    List<Project> getAllByProjectState(ProjectState projectState, EntityManager em);

}
