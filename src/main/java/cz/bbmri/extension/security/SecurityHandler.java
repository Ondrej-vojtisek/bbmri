package cz.bbmri.extension.security;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.Resolution;

import java.lang.reflect.Method;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

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

