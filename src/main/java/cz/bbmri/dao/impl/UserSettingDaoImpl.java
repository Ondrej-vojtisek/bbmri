package cz.bbmri.dao.impl;

import cz.bbmri.dao.UserSettingDao;
import cz.bbmri.entities.systemAdministration.UserSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository
public class UserSettingDaoImpl implements UserSettingDao {

    @PersistenceContext
    private EntityManager em;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    private static void notNull(final Object o) throws IllegalArgumentException {
        if (o == null) {
            throw new IllegalArgumentException("Object can't be a null object");
        }
    }

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
