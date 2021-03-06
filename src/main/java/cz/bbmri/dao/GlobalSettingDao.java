package cz.bbmri.dao;

/**
 * Interface to handle global settings of application. Global settings is defined as set of key and values stored in DB,
 * which defines behaviour of system. Each property have its own get method.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface GlobalSettingDao {

    /**
     * Add new value-key couple into DB if the key is not present in DB. Or set new value to key if it is already stored
     * in DB
     *
     * @param key   - property identification
     * @param value - new value of property
     * @return true if success or false otherwise
     */
    boolean set(String key, String value);

    /**
     * Return value of reservation validity property. It defines how long (how many months) sample reservation is preserved
     * before expiration.
     *
     * @return number of months. Number > 0
     */
    int getReservationValidity();
}
