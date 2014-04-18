package cz.bbmri.dao.impl;

import cz.bbmri.dao.UserSettingDao;
import cz.bbmri.entities.systemAdministration.UserSetting;
import org.springframework.stereotype.Repository;

/**
 * Implementation for interface handling instances of UserSetting. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository
public class UserSettingDaoImpl extends BaseForDao implements UserSettingDao {

    public void create(UserSetting userSetting) {
        notNull(userSetting);
        em.persist(userSetting);
    }

    public UserSetting get(Long id) {
        notNull(id);
        return em.find(UserSetting.class, id);
    }

    public void set(UserSetting userSetting) {
        notNull(userSetting);
        em.merge(userSetting);
    }

    public void remove(UserSetting userSetting) {
        notNull(userSetting);
        em.remove(userSetting);
    }

}
