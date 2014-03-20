package cz.bbmri.dao;

import cz.bbmri.entities.User;
import cz.bbmri.entities.systemAdministration.UserSetting;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 19.3.14
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public interface UserSettingDao {

    void create (UserSetting userSetting);

    UserSetting get(User user);

    void set(UserSetting userSetting);

    void remove (UserSetting userSetting);
}
