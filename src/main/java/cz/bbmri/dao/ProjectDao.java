package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.ProjectState;

import java.util.List;

/**
 * Interface to handle instances of Project stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface ProjectDao extends BasicDao<Project> {

    /**
     * Return all projects is the same lifecycle (specified by projectState)
     *
     * @param projectState
     * @return All project with given projectState
     */
    List<Project> getAllByProjectState(ProjectState projectState);

    /**
     * Return all projects of the user with given projectState
     *
     * @param user
     * @param projectState
     * @return List of all projects in projectState which are associated with user
     */
    List<Project> getAllByUserAndProjectState(User user, ProjectState projectState);

    /**
     * Return all instances of project associated with user ordered by given parameter. Desc param changes if it is ordered DESC or ASC
     *
     * @param user
     * @param orderByParam - select column by which will the result be sorted
     * @param desc - flag determining if order will be DESC (true) or default ASC (false)
     * @return
     */
    List<Project> getMyProjectsSorted(User user, String orderByParam, boolean desc);

    /**
     * Return all instances of project associated with sample (via sampleRequest) ordered by given parameter. Desc param changes if it is ordered DESC or ASC
     *
     * @param sample
     * @param orderByParam - select column by which will the result be sorted
     * @param desc - flag determining if order will be DESC (true) or default ASC (false)
     * @return
     */
    List<Project> getProjectsBySample(Sample sample, String orderByParam, boolean desc);

}
