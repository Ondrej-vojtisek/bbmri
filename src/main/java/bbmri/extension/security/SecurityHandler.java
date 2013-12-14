package bbmri.extension.security;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 18.7.13
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.Resolution;

import java.lang.reflect.Method;

/**
 * Optional interface that can be implemented by a SecurityManager to determine
 * what to do when access has been denied.
 *
 * @author Fred Daoud
 * @version
 */
public interface SecurityHandler
{
    /**
     * Determines what to do when access has been denied.
     *
     * @param bean    the action bean to which access was denied
     * @param handler the event handler to which access was denied
     * @return the Resolution to be executed when access has been denied
     */
    Resolution handleAccessDenied(ActionBean bean, Method handler);
}

