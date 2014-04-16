package cz.bbmri.dao;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.3.14
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public interface GlobalSettingDao {

    boolean set(String key, String value);

    int getReservationValidity();
}
