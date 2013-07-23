package bbmri.action;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 17.7.13
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */

import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.validation.LocalizableError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides necessary functionality required by multilingual application.
 *
 * @author Sochi
 * @version 0.1
 */
public abstract class LocalizableActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    /**
     * Returns the currently used locale settings.
     *
     * @return String representation of used locale
     */
    public String getLocale() {
        return getContext().getLocale().toString();
    }

    /**
     * Returns new instance of localizable message. Supplied key is prolonged by class name.
     *
     * @param key Information message name
     * @param parameters Message parameters
     * @return Message instance
     */
    protected LocalizableMessage getLocalizableMessage(String key, Object... parameters) {
        return new LocalizableMessage(this.getClass().getName() + "." + key, parameters);
    }

    /**
     * Returns new instance of localizable error.
     *
     * @param key Error message name
     * @param parameters Message parameters
     * @return Error message instance
     */
    protected LocalizableError getLocalizableError(String key, Object... parameters) {
        return new LocalizableError(key, parameters);
    }
}
