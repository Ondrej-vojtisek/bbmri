package cz.bbmri.dao.impl;

import cz.bbmri.dao.ProjectDao;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.ProjectState;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository
public class ProjectDaoImpl extends BasicDaoImpl<Project> implements ProjectDao {

    public List<Project> getAllByProjectState(ProjectState projectState) {
        notNull(projectState);
        typedQuery = em.createQuery("SELECT p FROM Project p where p.projectState = :param", Project.class);
        typedQuery.setParameter("param", projectState);
        return typedQuery.getResultList();
    }

    //select b.fname, b.lname from Users b JOIN b.groups c where c.groupName = :groupName
    public List<Project> getAllByUserAndProjectState(User user, ProjectState projectState) {
        notNull(projectState);
        typedQuery  = em.createQuery("" +
                "SELECT project FROM Project project JOIN project.projectAdministrators admin JOIN admin.user user " +
                "WHERE project.projectState = :stateParam " +
                "and user = :userParam ", Project.class);
        typedQuery .setParameter("stateParam", projectState);
        typedQuery .setParameter("userParam", user);

        return typedQuery .getResultList();
    }

    public List<Project> getMyProjectsSorted(User user, String orderByParam, boolean desc) {
        String orderParam = "";
        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY project." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        typedQuery  = em.createQuery("SELECT project FROM Project project JOIN project.projectAdministrators admin " +
                "JOIN admin.user user " +
                "WHERE user = :userParam " +
                orderParam, Project.class);
        typedQuery .setParameter("userParam", user);
        return typedQuery .getResultList();
    }

    public List<Project> getProjectsBySample(Sample sample, String orderByParam, boolean desc) {
        String orderParam = "";
        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY project." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        typedQuery  = em.createQuery("SELECT project FROM Project project JOIN project.sampleRequests sampleRequest " +
                "JOIN sampleRequest.requests request " +
                "WHERE request.sample = :sample " +
                orderParam, Project.class);
        typedQuery .setParameter("sample", sample);
        return typedQuery .getResultList();
    }

}
