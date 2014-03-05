package cz.bbmri.dao;

import cz.bbmri.entities.systemAdministration.GlobalSetting;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.3.14
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public interface GlobalSettingDao {

    GlobalSetting get(String key);

    boolean set(String key, String value);
}
