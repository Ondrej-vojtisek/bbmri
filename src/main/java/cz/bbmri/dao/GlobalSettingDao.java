package cz.bbmri.dao;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */
public interface GlobalSettingDao {

    boolean set(String key, String value);

    int getReservationValidity();
}
