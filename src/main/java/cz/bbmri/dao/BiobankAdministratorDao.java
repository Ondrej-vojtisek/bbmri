package cz.bbmri.dao;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.Permission;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 24.9.13
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankAdministratorDao extends BasicDao<BiobankAdministrator> {

    boolean contains(Biobank biobank, User user);

    Permission getHighestPermission(User user);

    BiobankAdministrator get(Biobank biobank, User user);

    List<BiobankAdministrator> get(Biobank biobank, Permission permission);

}
