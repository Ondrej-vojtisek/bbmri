package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractCompositeDAO;
import cz.bbmri.dao.BiobankUserDAO;
import cz.bbmri.entity.Biobank;
import cz.bbmri.entity.BiobankUser;
import cz.bbmri.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("biobankUserDAO")
@Transactional
public class BiobankUserDAOImpl extends GenericDAOImpl<BiobankUser> implements BiobankUserDAO {

    public static List<User> getOtherBiobankUsers(Biobank biobank, User user){
        notNull(biobank);
        notNull(user);

        Set<BiobankUser> biobankUsers = biobank.getBiobankUser();

        List<User> users = new ArrayList<User>();

        for(BiobankUser biobankUser : biobankUsers){
            if(!biobankUser.getUser().equals(user)){
                users.add(biobankUser.getUser());
            }
        }

        return users;
    }

}
