package cz.bbmri.service.simpleService;

import java.util.List;

/**
 * There is no shared API on service layer because it is not intended to implement every method provided on DAO also on
 * service layer. Interface provides shared definition of single function.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface All<T> {

    /**
     * Return all entities of given type T stored in DB
     *
     * @return list of all entities
     */
    List<T> all();
}
