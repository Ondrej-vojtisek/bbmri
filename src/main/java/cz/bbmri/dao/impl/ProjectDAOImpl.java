package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.ProjectDAO;
import cz.bbmri.entity.Project;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
