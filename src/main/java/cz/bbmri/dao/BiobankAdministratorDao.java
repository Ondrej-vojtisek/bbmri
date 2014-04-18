package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.Permission;

import java.util.List;

/**
 * Interface to handle instances of BiobankAdministrator stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface BiobankAdministratorDao extends BasicDao<BiobankAdministrator> {

    /**
     * Check if there is a administrator relationship between the biobank and the user.
     *
     * @param biobank - object of type Biobank to be checked
     * @param user - object of type User to be checked
     * @return true or false existence of relastionship between biobank and user
     */
    boolean contains(Biobank biobank, User user);

    /**
     * Return the highest permission of user to any biobank. In case the user is set as administrator of two biobanks
     * in one as MANAGER and in the second as a VISITOR than the MANAGER persmission will be returned.
     * The order of permissions is defined by Permission class.
     *
     * @param user - the user which is checked
     * @return highest permission of user to any of his biobank
     */
    Permission getHighestPermission(User user);

    /**
     * Return relationship between biobank and user
     *
     * @param biobank
     * @param user
     * @return BiobankAdministrator or null
     */
    BiobankAdministrator get(Biobank biobank, User user);

    /**
     * Return all administrators of the biobank with defined permission. E.G. return all managers.
     *
     * @param biobank - only instances of biobankAdministrator asociated with this biobank
     * @param permission - select only this permission
     * @return list of biobankAdministrators
     */
    List<BiobankAdministrator> get(Biobank biobank, Permission permission);

}
