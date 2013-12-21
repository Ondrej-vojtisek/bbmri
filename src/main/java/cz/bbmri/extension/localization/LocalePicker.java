package cz.bbmri.extension.localization;

import net.sourceforge.stripes.localization.DefaultLocalePicker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 14.12.13
 * Time: 17:12
 * To change this template use File | Settings | File Templates.
 */
public class LocalePicker extends DefaultLocalePicker {

    public static final String LOCALE = "locale";

    @Override
    public Locale pickLocale(HttpServletRequest request) {
        HttpSession session = request.getSession();
        // Look in the request.
        String locale = request.getParameter(LOCALE);
        if (locale != null) {
            session.setAttribute(LOCALE, locale);
        }
        // Not found in the request? Look in the session.
        else {
            locale = (String) session.getAttribute(LOCALE);
        }
        // Use the locale if found.
        if (locale != null) {
            return new Locale(locale);
        }
        // Otherwise, use the default.
        return super.pickLocale(request);
    }
}

