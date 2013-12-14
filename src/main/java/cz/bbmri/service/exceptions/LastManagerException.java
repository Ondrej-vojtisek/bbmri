package cz.bbmri.service.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 18.11.13
 * Time: 11:48
 * To change this template use File | Settings | File Templates.
 */
public class LastManagerException extends Exception  {

    private static final long serialVersionUID = -1787219534343026948L;

    public LastManagerException(String msg) {
        super(msg);
    }

    public LastManagerException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
