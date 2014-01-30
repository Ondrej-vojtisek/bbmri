package cz.bbmri.extension.localization;

import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.localization.LocalizationBundleFactory;
import net.sourceforge.stripes.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class CustomLocalizationBundleFactory implements LocalizationBundleFactory
{
    // Hold the instance of logger class for resource usage dubugging
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    // The configuration parameter for changing the error message resource bundle
    public static final String ERROR = "LocalizationBundleFactory.ErrorMessageBundle";

    // The configuration parameter for changing the field name resource bundle
    public static final String FIELD = "LocalizationBundleFactory.FieldNameBundle";

    // The configuration parameter for changing the shared resource bundle
    public static final String SHARE = "LocalizationBundleFactory.SharedBundle";

    // Path to localization of default resources
    public static final String PATH = "translations";

    // Holds the initial configuration
    private String errorBundleName;
    private String fieldBundleName;
    private String shareBundleName;

    @Override
    public void init(Configuration configuration)
    {
        // Load the appropriate localization resource configuration
        errorBundleName = configuration.getBootstrapPropertyResolver().getProperty(ERROR);
        fieldBundleName = configuration.getBootstrapPropertyResolver().getProperty(FIELD);
        shareBundleName = configuration.getBootstrapPropertyResolver().getProperty(SHARE);

        // In case no error bundle supplied
        if(this.errorBundleName == null)
        {
            this.errorBundleName = PATH + "/errors";
        }

        // In case no field name bundle
        if(this.fieldBundleName == null)
        {
            this.fieldBundleName = PATH + "/fields";
        }

        // In case no shared bundle supplied
        if(this.shareBundleName == null)
        {
            this.shareBundleName = PATH + "/shared";
        }
    }

    @Override
    public ResourceBundle getFormFieldBundle(Locale locale)
    {
        return new TheResourceBundle(locale, log, fieldBundleName, shareBundleName);
    }

    @Override
    public ResourceBundle getErrorMessageBundle(Locale locale)
    {
        return new TheResourceBundle(locale, log, errorBundleName, shareBundleName);
    }
}