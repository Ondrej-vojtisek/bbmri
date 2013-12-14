package cz.bbmri.facade.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 3.12.13
 * Time: 16:27
 * To change this template use File | Settings | File Templates.
 */
public class AuthorizationException extends Exception {


    private static final long serialVersionUID = -1787219534343026948L;

    public AuthorizationException(String msg) {
        super(msg);
    }

    public AuthorizationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
