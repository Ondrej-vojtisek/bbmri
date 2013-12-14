package bbmri.extension.localization;

import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.localization.LocalizationBundleFactory;
import net.sourceforge.stripes.util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 22.7.13
 * Time: 14:56
 * To change this template use File | Settings | File Templates.
 */
public class CustomLocalizationBundleFactory
                             implements LocalizationBundleFactory {
    /**
     * The Configuration Key which specifies
     * multiple resource bundles.
     */
    public static final String RESOURCE_BUNDLES_BASE_NAMES =
                                        "ResourceBundles.BaseNames";
    private String[] bundles;

    public ResourceBundle getFormFieldBundle(Locale locale) {
        return new MultipleResourceBundle(locale, getBundleNames());
    }

    public ResourceBundle getErrorMessageBundle(Locale locale) {
        return new MultipleResourceBundle(locale, getBundleNames());
    }

    public void init(Configuration config) {
        String bundleNames = config.getBootstrapPropertyResolver()
                          .getProperty(RESOURCE_BUNDLES_BASE_NAMES);
        bundles = StringUtil.standardSplit(bundleNames);
    }

    public List<String> getBundleNames() {
        return Arrays.asList(bundles);
    }
}
