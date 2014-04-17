package cz.bbmri.dao;

import cz.bbmri.entities.systemAdministration.UserSetting;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */
public interface UserSettingDao {

    void create (UserSetting userSetting);

    UserSetting get(Long id);

    void set(UserSetting userSetting);

    void remove (UserSetting userSetting);
}
