package cz.bbmri.service.simpleService;

/**
 * There is no shared API on service layer because it is not intended to implement every method provided on DAO also on
 * service layer. Interface provides shared definition of single function.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface Get<T> {

    /**
     * Return one instance of type T by its unique identifier
     *
     * @param id - identifier
     * @return instance of T or null
     */
    T get(Long id);
}
