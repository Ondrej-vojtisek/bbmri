package cz.bbmri.dao.impl;

import cz.bbmri.dao.UserSettingDao;
import cz.bbmri.entities.User;
import cz.bbmri.entities.systemAdministration.UserSetting;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 19.3.14
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserSettingDaoImpl implements UserSettingDao {

    public void create (UserSetting userSetting){
        throw new NotYetImplementedException("");
    }

    public UserSetting get(User user){
        throw new NotYetImplementedException("");
    }

    public void set(UserSetting userSetting){
        throw new NotYetImplementedException("");
    }

    public void remove (UserSetting userSetting){
        throw new NotYetImplementedException("");
    }

}
