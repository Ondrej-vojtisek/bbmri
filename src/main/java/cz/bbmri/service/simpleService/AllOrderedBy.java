package cz.bbmri.service.simpleService;

import java.util.List;

/**
 * There is no shared API on service layer because it is not intended to implement every method provided on DAO also on
 * service layer. Interface provides shared definition of single function.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface AllOrderedBy<T> {

    /**
     * Returns all instances of type T. Output is sorted by given params.
     *
     * @param orderByParam
     * @param desc
     * @return
     */
    List<T> allOrderedBy(String orderByParam, boolean desc);
}
