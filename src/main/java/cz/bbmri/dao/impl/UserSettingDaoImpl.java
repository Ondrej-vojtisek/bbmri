package cz.bbmri.dao.impl;

import cz.bbmri.dao.UserSettingDao;
import cz.bbmri.entities.systemAdministration.UserSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 19.3.14
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserSettingDaoImpl implements UserSettingDao {

    @PersistenceContext
    protected EntityManager em;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    public static void notNull(final Object o) throws IllegalArgumentException {
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
