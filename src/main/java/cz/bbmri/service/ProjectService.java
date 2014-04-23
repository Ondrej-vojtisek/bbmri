package cz.bbmri.service;

import cz.bbmri.entities.Project;
import cz.bbmri.entities.enumeration.ProjectState;
import cz.bbmri.service.simpleService.All;
import cz.bbmri.service.simpleService.AllOrderedBy;
import cz.bbmri.service.simpleService.Get;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * API for handling Projects
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface ProjectService extends Get<Project>, All<Project>, AllOrderedBy<Project> {

    /**
     * Return all projects in given state associated with specified user.
     *
     * @param userId       - ID of user
     * @param projectState - return all projects currently in this state
     * @return list of projects
     */
    List<Project> allByProjectStateAndUser(Long userId, ProjectState projectState);

    /**
     * Return sorted list of projects associated with given user
     *
     * @param userId       - ID of user.
     * @param orderByParam
     * @param desc
     * @return sorted list of projects
     */
    List<Project> getProjectsSortedByUser(Long userId, String orderByParam, boolean desc);

    /**
     * Return all sorted list of projects associated with given sample - all projects requested sample
     *
     * @param sampleId     - ID of sample
     * @param orderByParam
     * @param desc
     * @return list of sorted projects
     */
    List<Project> getProjectsBySample(Long sampleId, String orderByParam, boolean desc);

    /**
     * Approve project (change state of project)
     *
     * @param projectId    - ID of project which will be approved
     * @param loggedUserId - ID of administrator who approved project
     * @param errors       - in case of error, error messages will be stored into errors
     * @return true/false
     */
    boolean approve(Long projectId, Long loggedUserId, ValidationErrors errors);

    /**
     * Deny project (change state of project)
     *
     * @param projectId    - ID of project which will be denied
     * @param loggedUserId - ID of administrator who denied project
     * @param errors       - in case of error, error messages will be stored into errors
     * @return true/false
     */
    boolean deny(Long projectId, Long loggedUserId, ValidationErrors errors);

    /**
     * Store instance of project in DB
     *
     * @param project - new instance of project
     * @param errors  - in case of error, error messages will be stored into errors
     * @return true/false
     */
    boolean create(Project project, ValidationErrors errors);

    /**
     * Update value of attributes of existing project. Only non null values of changeable attributes of given project
     * will be modified in DB
     *
     * @param project      - project which will be updated
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - ID of event initiator. Notification about new file will be sent to project team except
     *                     initiator (BROADCAST)
     * @return true/false
     */
    boolean update(Project project, ValidationErrors errors, Long loggedUserId);

    /**
     * Remove project. Removes all project files, project folder, all asociated entities (sampleRequests, ...)
     *
     * @param projectId    - ID of project which will be removed
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - ID of event initiator. Notification about new file will be sent to project team except
     *                     initiator (BROADCAST)
     * @return true/false
     */
    boolean remove(Long projectId, ValidationErrors errors, Long loggedUserId);

    /**
     * Mark project as finished (change state of project)
     *
     * @param projectId    - ID of project to be finished
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - ID of event initiator. Notification about new file will be sent to project team except
     *                     initiator (BROADCAST)
     * @return true/false
     */
    boolean finish(Long projectId, ValidationErrors errors, Long loggedUserId);

}
