package bbmri.service;

import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.Researcher;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectService {

    public Project create(Project project, Researcher researcher);

    public void remove(Long id);

    public Project update(Project project);

    public List<Project> getAll();

    public List<Project> getAllByResearcher(Long id);

    public Researcher assignResearcher(Long researcherId, Long projectId);

    public Researcher removeResearcherFromProject(Long researcherId, Long projectId);

    public List<Researcher> getAllAssignedResearchers(Long projectId);

    public void approve(Long id);

    public List<Project> getAllByProjectState(ProjectState projectState);

    public List<Project> getAllApprovedByResearcher(Researcher researcher);

    public List<Project> getAllWhichResearcherAdministrate(Long id);

    public Project getById(Long id);

    public List<Researcher> getAllNotAssignedResearchers(Long id);

    public Project changeOwnership(Long projectId, Long newOwnerId);

}
