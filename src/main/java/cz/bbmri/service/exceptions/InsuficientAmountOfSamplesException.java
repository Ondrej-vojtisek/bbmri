package cz.bbmri.service.exceptions;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class InsuficientAmountOfSamplesException extends Exception {

    private static final long serialVersionUID = -1787219534343026948L;

     public InsuficientAmountOfSamplesException(String msg) {
         super(msg);
     }

     public InsuficientAmountOfSamplesException(String msg, Throwable cause) {
         super(msg, cause);
     }
}
