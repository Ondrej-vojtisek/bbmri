package cz.bbmri.service.simpleService;

import java.util.List;

/**
 * There is no shared API on service layer because it is not intended to implement every method provided on DAO also on
 * service layer. Interface provides shared definition of single function.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface Find<T> {

    /**
     * Find similar instances of T to given one. Search is implemented only by non null attributes of T.
     *
     * @param question        - instance of T
     * @param requiredResults - maximal number of results
     * @return list of similar patients to given one
     */
    List<T> find(T question, int requiredResults);
}
