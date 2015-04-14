package cz.bbmri.dao.impl;

import cz.bbmri.dao.GlobalSettingDAO;
import cz.bbmri.entity.Country;
import cz.bbmri.entity.GlobalSetting;
import cz.bbmri.entity.constant.Constant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("globalSettingDAO")
@Transactional
public class GlobalSettingDAOImpl extends BaseDAOImpl implements GlobalSettingDAO {

    private GlobalSetting get(String key) {

       return (GlobalSetting) getCurrentSession().get(GlobalSetting.class, key);
   }

   public boolean set(String key, String value) {

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

       getCurrentSession().save(globalSetting);

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
