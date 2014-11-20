package cz.bbmri.entities.exception;

/**
 * If we want to check two same objects for modification and ID doensn't match.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class DifferentEntityException extends Exception {

    private static final long serialVersionUID = -1787219534343026948L;

    public DifferentEntityException(String msg) {
        super(msg);
    }

    public DifferentEntityException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
