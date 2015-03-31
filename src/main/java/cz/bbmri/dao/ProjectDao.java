package cz.bbmri.dao;

import cz.bbmri.entity.Project;
import cz.bbmri.entity.User;

import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface ProjectDAO extends AbstractDAO<Project, Long> {

    List<Project> getByUser(User user);

}
