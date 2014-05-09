package cz.bbmri.dao.impl;

import cz.bbmri.dao.GlobalSettingDao;
import cz.bbmri.entities.constant.Constant;
import cz.bbmri.entities.systemAdministration.GlobalSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * Implementation for interface handling instances of GlobalSetting. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Transactional
@Repository
public class GlobalSettingDaoImpl extends BaseForDao implements GlobalSettingDao {

    @PersistenceContext
    private EntityManager em;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

     private GlobalSetting get(String key) {
        notNull(key);

        return em.find(GlobalSetting.class, key);
    }

    public boolean set(String key, String value) {
        notNull(key);
        notNull(value);

        GlobalSetting globalSetting;
        boolean existed = false;

        if(get(key) == null){
            // brand new
            globalSetting = new GlobalSetting();
        }else{
            //  retrieved from db
            globalSetting = get(key);
            existed = true;
        }

        globalSetting.setKey(key);
        globalSetting.setValue(value);
        globalSetting.setLastModification(new Date());

        if(existed){
            //update
            em.merge(globalSetting);
        }else{
            //create new
            em.persist(globalSetting);
        }

        return true;
    }

    public int getReservationValidity(){
        GlobalSetting gl = get(GlobalSetting.RESERVATION_VALIDITY);
        if(gl == null){
            // return default value, because there is no explicit defined
            return Constant.DEFAULT_RESERVATION_VALIDITY;
        }
        int newValue = Integer.parseInt(gl.getValue());
        if(newValue > 0) {
            return newValue;
        } else{
            logger.error("Reservation validity global setting wasn't parsed correctly");
        }
        return Constant.DEFAULT_RESERVATION_VALIDITY;
    }
}
