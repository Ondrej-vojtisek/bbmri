package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.ProjectStateDAO;
import cz.bbmri.entity.ProjectState;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("projectStateDAO")
@Transactional
public class ProjectStateDAOImpl extends GenericDAOImpl<ProjectState> implements ProjectStateDAO {

    public ProjectState get(Short id) {
                      return (ProjectState) getCurrentSession().get(ProjectState.class, id);
                  }

}
