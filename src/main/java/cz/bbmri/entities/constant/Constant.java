package cz.bbmri.entities.constant;


/**
 * All constants which are not associated with only one entity are defined here.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class Constant {

    /**
     * Return value of successful operation
     */
    public static final int SUCCESS = 0;

    /**
     * Return value of non successful operation
     */
    public static final int NOT_SUCCESS = -1;

    /**
     * To detect that object was ovewritten during its createn.
     */
    public static final int OVERWRITTEN = -2;

    /**
     * Default number of maximum search results returned by similarity search queries from DB
     */
    public static final int MAXIMUM_FIND_RESULTS = 5;

    /**
     * Reservation of samples are preserved for three month by default
     */
    public static final int DEFAULT_RESERVATION_VALIDITY = 3;

}
