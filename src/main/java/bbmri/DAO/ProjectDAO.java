package bbmri.dao;

import bbmri.entities.Project;
import bbmri.entities.enumeration.ProjectState;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectDao extends BasicDao<Project> {

    List<Project> getAllByProjectState(ProjectState projectState);

}
