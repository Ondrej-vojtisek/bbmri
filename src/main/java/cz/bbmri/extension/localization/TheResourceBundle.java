package cz.bbmri.extension.localization;

import org.slf4j.Logger;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *  Resource bundles contains localizable messages. The resourceBundle extends ResourceBundle to enable choosing between
 *  bundles. It contains logger in case to inform developer if anything failes.
 *
 * @author Jan Sochor (jan.sochor@icebolt.info) - THALAMOSS project thalamoss-data.ics.muni.cz
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class TheResourceBundle extends ResourceBundle {
    // Hold assigned instance
    // of appropriate logger
    private Logger logger;

    // Selected locale
    private Locale locale;

    // Resource file names
    private String source;
    private String shared;

    /**
     * Class constructor.
     */
    public TheResourceBundle(Locale locale, String source) {
        this(locale, source, null);
    }

    /**
     * Class constructor including logger.
     */
    public TheResourceBundle(Locale locale, Logger log, String source) {
        this(locale, log, source, null);
    }

    /**
     * Additional class constructor.
     */
    private TheResourceBundle(Locale locale, String source, String shared) {
        this(locale, null, source, shared);
    }

    /**
     * Additional class constructor with logger.
     */
    public TheResourceBundle(Locale locale, Logger logger, String source, String shared) {
        this.locale = locale;
        this.source = source;
        this.shared = shared;

        // Assign the pointer
        // to given instance
        this.logger = logger;
    }

    @Override
    public Enumeration<String> getKeys() {
        // Empty result
        return null;
    }

    @Override
    protected Object handleGetObject(String full) {
        // Load the desired value from the resource
        Object result = getResult(locale, source, full);

        // In case the associated
        // value was not found
        if (result == null) {
            // When path to shared
            // resource is given
            if (shared != null) {
                // Load the content from shared resource
                result = getResult(locale, shared, full);
            }
        }

        // Return the result
        return result;
    }

    /**
     * Returns null if the bundle or the key is not found, instead of throwing an exception.
     *
     * @param locale - elected locale settings
     * @param source - from which resource bundle
     * @param key    - key to be localized
     * @return translated message
     */
    private String getResult(Locale locale, String source, String key) {
        // Predefine the result
        String result = null;

        // Create new resource bundle instance
        ResourceBundle bundle = ResourceBundle.getBundle(source, locale);

        // When no failure
        if (bundle != null) {
            try {
                // Look up the desired value
                result = bundle.getString(key);
            } catch (MissingResourceException e) {
                // Nothing to be done in here
            }
        }

        if (logger != null) {
            // Display a debugger message in case a logger instance has been supplied
            // logger.debug("Resource " + source + " (" + locale + ") using '" + key + "' results into '" + result + "'");
        }

        // Return the result
        return result;
    }
}