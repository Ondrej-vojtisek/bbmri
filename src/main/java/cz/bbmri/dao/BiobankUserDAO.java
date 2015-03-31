package cz.bbmri.dao;

import cz.bbmri.entity.Biobank;
import cz.bbmri.entity.BiobankUser;
import cz.bbmri.entity.Permission;
import cz.bbmri.entity.User;

import java.util.List;
import java.util.Set;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface BiobankUserDAO extends AbstractCompositeDAO<BiobankUser> {

    BiobankUser get(Biobank biobank, User user);

    boolean hasPermission(Permission permission, Biobank biobank, User user);

}
