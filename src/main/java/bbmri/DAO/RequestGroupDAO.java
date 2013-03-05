package bbmri.DAO;

import bbmri.entities.Project;
import bbmri.entities.RequestGroup;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 6.2.13
 * Time: 12:16
 * To change this template use File | Settings | File Templates.
 */
public interface RequestGroupDAO {

    void create(RequestGroup requestGroup);

    void remove(RequestGroup requestGroup);

    void update(RequestGroup requestGroup);

    List<RequestGroup> getAll();

    RequestGroup get(Long id);

    Integer getCount();

    List<RequestGroup> getAllByProject(Project project);

}
