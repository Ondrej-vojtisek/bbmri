package cz.bbmri.service.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 9.12.13
 * Time: 17:15
 * To change this template use File | Settings | File Templates.
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
