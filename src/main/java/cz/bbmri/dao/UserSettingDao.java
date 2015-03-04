package cz.bbmri.dao;

import cz.bbmri.entities.systemAdministration.UserSetting;

/**
 * Interface to handle user setting of application. It is defined as a set of values for each user. These values defines
 * behaviour of system only for the user.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface UserSettingDao {

    /**
     * Create userSetting instance in DB.
     *
     * @param userSetting
     */
    void create (UserSetting userSetting);

    /**
     * Update userSetting record in DB
     *
     * @param userSetting
     */
    void set(UserSetting userSetting);

    /**
     * Remove userSetting from DB
     *
     * @param userSetting
     */
    void remove (UserSetting userSetting);

}
