package cz.bbmri.dao.impl;

import cz.bbmri.dao.GlobalSettingDao;
import cz.bbmri.entities.systemAdministration.GlobalSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.3.14
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Repository
public class GlobalSettingDaoImpl implements GlobalSettingDao {

    @PersistenceContext
    protected EntityManager em;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

     public GlobalSetting get(String key) {
        if (key == null) {
            logger.debug("Key can't be null");
            return null;
        }

        return em.find(GlobalSetting.class, key);
    }

    public boolean set(String key, String value) {
        if (key == null) {
            logger.debug("Key can't be null");
            return false;
        }

        if (value == null) {
            logger.debug("Value can't be null");
            return false;
        }

        logger.debug("Key: " + key);
        logger.debug("Value: " + value);



        GlobalSetting globalSetting = null;
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

        logger.debug("globalSettings: " + globalSetting);
        em.merge(globalSetting);

        if(existed){
            //update
            em.merge(globalSetting);
        }else{
            //create new
            em.persist(globalSetting);
        }

        return true;
    }
}