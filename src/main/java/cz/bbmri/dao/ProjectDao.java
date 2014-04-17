package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.ProjectState;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */
public interface ProjectDao extends BasicDao<Project> {

    List<Project> getAllByProjectState(ProjectState projectState);

    List<Project> getAllByUserAndProjectState(User user, ProjectState projectState);

    List<Project> getMyProjectsSorted(User user, String orderByParam, boolean desc);

    List<Project> getProjectsBySample(Sample sample, String orderByParam, boolean desc);

}
