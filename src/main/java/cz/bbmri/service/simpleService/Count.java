package cz.bbmri.service.simpleService;

/**
 * There is no shared API on service layer because it is not intended to implement every method provided on DAO also on
 * service layer. Interface provides shared definition of single function.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface Count {

    /**
     * Return count of all instances
     *
     * @return count
     */
    Integer count();

}
