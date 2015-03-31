package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.ProjectDAO;
import cz.bbmri.entity.Project;
import cz.bbmri.entity.ProjectUser;
import cz.bbmri.entity.Shibboleth;
import cz.bbmri.entity.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("projectDAO")
@Transactional
public class ProjectDAOImpl extends GenericDAOImpl<Project> implements ProjectDAO {

    public Project get(Long id) {
        return (Project) getCurrentSession().get(Project.class, id);
    }

    public List<Project> getByUser(User user) {

        List<Project> projects = getCurrentSession().createCriteria(Project.class)
                .createAlias("projectUser", "projectUsers")
                .add(Restrictions.eq("projectUsers.user", user)).list();

        return projects;
        }

    }
