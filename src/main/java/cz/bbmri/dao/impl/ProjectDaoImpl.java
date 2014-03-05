package cz.bbmri.dao.impl;

import cz.bbmri.dao.ProjectDao;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.ProjectState;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class ProjectDaoImpl extends BasicDaoImpl<Project> implements ProjectDao {

    @SuppressWarnings("unchecked")
    public List<Project> getAllByProjectState(ProjectState projectState) {
        notNull(projectState);
        Query query = em.createQuery("SELECT p FROM Project p where p.projectState = :param");
        query.setParameter("param", projectState);
        return query.getResultList();
    }

    //select b.fname, b.lname from Users b JOIN b.groups c where c.groupName = :groupName

    @SuppressWarnings("unchecked")
    public List<Project> getAllByUserAndProjectState(User user, ProjectState projectState) {
        notNull(projectState);
        Query query = em.createQuery("" +
                "SELECT project FROM Project project JOIN project.projectAdministrators admin JOIN admin.user user " +
                    "where project.projectState = :stateParam " +
                    "and user = :userParam ");
        query.setParameter("stateParam", projectState);
        query.setParameter("userParam", user);

        return query.getResultList();
    }

}
