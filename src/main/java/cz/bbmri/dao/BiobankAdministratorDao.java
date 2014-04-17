package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.Permission;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */
public interface BiobankAdministratorDao extends BasicDao<BiobankAdministrator> {

    boolean contains(Biobank biobank, User user);

    Permission getHighestPermission(User user);

    BiobankAdministrator get(Biobank biobank, User user);

    List<BiobankAdministrator> get(Biobank biobank, Permission permission);

}
