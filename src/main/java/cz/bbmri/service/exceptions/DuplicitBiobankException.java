package cz.bbmri.service.exceptions;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class DuplicitBiobankException extends Exception {

    private static final long serialVersionUID = -1787219534343026948L;

       public DuplicitBiobankException(String msg) {
           super(msg);
       }

       public DuplicitBiobankException(String msg, Throwable cause) {
           super(msg, cause);
       }
}
