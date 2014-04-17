package cz.bbmri.extension.localization;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 22.7.13
 * Time: 14:58
 * To change this template use File | Settings | File Templates.
 */

import net.sourceforge.stripes.localization.DefaultLocalizationBundleFactory;

import java.util.*;

/**
 * With thanks to Freddy's Stripes Book http://www.pragprog.com/titles/fdstr
 *
 * @author DJDaveMark
 * @author Fred Daoud
 */
public class MultipleResourceBundle extends ResourceBundle {



    private Locale locale;
    private List<String> bundleNames;

    public MultipleResourceBundle(Locale locale, List<String> bundleNames) {
        this.locale = locale;
        this.bundleNames = bundleNames;
    }

    @Override
    public Enumeration<String> getKeys() {
        return null;
    }

    @Override
    protected Object handleGetObject(String key) {
        Object result = null;
        if (bundleNames != null) {
            // Look in each configured bundle
            for (String bundleName : bundleNames) {

                if (bundleName != null) {

                    result = getFromBundle(locale, bundleName, key);
                    if (result != null) {
                        break;
                    }
                }
            }
        }
        if (result == null) {

            // Try the application's default bundle
            String bundleName = DefaultLocalizationBundleFactory.BUNDLE_NAME;
            result = getFromBundle(locale, bundleName, key);
        }
        return result;
    }

    /**
     * Returns null if the bundle or key is not found. No exceptions thrown.
     */
    private String getFromBundle(Locale loc, String bundleName, String key) {
        String result = null;
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, loc);
        if (bundle != null) {
            try {
                result = bundle.getString(key);
            } catch (MissingResourceException exc) {
            }
        }

        return result;
    }
}
