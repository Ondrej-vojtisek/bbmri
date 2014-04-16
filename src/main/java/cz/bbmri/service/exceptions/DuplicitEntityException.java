package cz.bbmri.service.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 15.4.14
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */
public class DuplicitEntityException extends Exception {

    private static final long serialVersionUID = -1787219534343026948L;

    public DuplicitEntityException(String msg) {
        super(msg);
    }

    public DuplicitEntityException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
