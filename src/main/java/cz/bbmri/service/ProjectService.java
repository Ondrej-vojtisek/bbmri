package cz.bbmri.service;

import cz.bbmri.entities.Project;
import cz.bbmri.entities.enumeration.ProjectState;
import cz.bbmri.service.simpleService.All;
import cz.bbmri.service.simpleService.Get;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectService extends Get<Project>, All<Project> {

    List<Project> allOrderedBy(String orderByParam, boolean desc);

    List<Project> allByProjectStateAndUser(Long userId, ProjectState projectState);

    List<Project> getProjectsSortedByUser(Long userId, String orderByParam, boolean desc);

    List<Project> getProjectsBySample(Long sampleId, String orderByParam, boolean desc);

    boolean approve(Long projectId, Long loggedUserId, ValidationErrors errors);

    boolean deny(Long projectId, Long loggedUserId, ValidationErrors errors);

    boolean create(Project project, ValidationErrors errors);

    boolean update(Project project, ValidationErrors errors, Long loggedUserId);

    boolean remove(Long projectId, ValidationErrors errors, Long loggedUserId);

    boolean finish(Long projectId, ValidationErrors errors, Long loggedUserId);

}
