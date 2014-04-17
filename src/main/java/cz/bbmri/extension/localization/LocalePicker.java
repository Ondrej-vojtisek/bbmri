package cz.bbmri.extension.localization;

import net.sourceforge.stripes.localization.DefaultLocalePicker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
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

