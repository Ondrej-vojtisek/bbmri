package cz.bbmri.service.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 22.2.14
 * Time: 15:09
 * To change this template use File | Settings | File Templates.
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
