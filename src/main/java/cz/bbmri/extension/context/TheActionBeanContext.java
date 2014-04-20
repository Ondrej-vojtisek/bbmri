package cz.bbmri.extension.context;

import cz.bbmri.entities.User;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * Context of all actionBeans
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class TheActionBeanContext extends ActionBeanContext {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * Shibboleth attributes
     * - all attributes which are retrived from shibboleth header
     */
    private static final String SHIB_SESSION_ID = "Shib-Session-ID";
    private static final String SHIB_EPPN = "eppn";
    private static final String SHIB_TARGETED_ID = "targeted-id";
    private static final String SHIB_PERSISTENT_ID = "persistent-id";
    private static final String SHIB_AFFILIATION = "affiliation";
    private static final String SHIB_CN = "cn";
    private static final String SHIB_SN = "sn";
    private static final String SHIB_GIVEN_NAME = "givenName";
    private static final String SHIB_ORGANIZATION = "o";
    private static final String SHIB_MAIL = "mail";
    private static final String SHIB_DISPLAY_NAME = "displayName";

    private static final String MY_ID = "myId";

    /**
     * Stores the given attribute in session
     *
     * @param key   - Attribute key
     * @param value - Stored value
     */
    void setCurrent(String key, Object value) {
        // Retrieve the session instance and set attribute
        getRequest().getSession().setAttribute(key, value);
    }

    /**
     * Searches for an attribute in session by the given key
     *
     * @param key          - Session attribute key
     * @param defaultValue - Default value
     * @return Loaded value or null
     */
    <T> T getCurrent(String key, T defaultValue) {
        // Load the attribute from retrieved session instance
        T value = (T) getRequest().getSession().getAttribute(key);

        // When unavailable
        if (value == null) {
            // Replace with the default
            value = defaultValue;

            // Store the default
            setCurrent(key, value);
        }

        // Retrieved
        return value;
    }

    public void dropUser() {
        // Forget the logged user
        dropMyId();
        // When logged
        // Retrieve the session instance
        HttpSession session = getRequest().getSession();
        // When session available
        if (session != null) {
            // Invalidate the session
            session.invalidate();
        }
    }

    public void setMyId(Long id) {
        setCurrent(MY_ID, id);
    }

    public Long getMyId() {
        return getCurrent(MY_ID, null);
    }

    void dropMyId() {
        setCurrent(MY_ID, null);
    }

    public void setLoggedUser(User user) {
        // Forget the value
        dropMyId();

        // When user is given
        if (user != null) {
            // Update the identifier
            setMyId(user.getId());
        }
    }

    /**
     * Hack to parse data from shibboleth headers in UTF-8. HTTP header is encoded using iso-8859-1 but without explicit
     * specification application would try to parse it as UTF-8.
     *
     * @param param - Attribute to search in header
     * @return text in UTF-8
     */
    String getHeaderParam(String param) {
        String paramText = getRequest().getHeader(param);
        if (paramText != null)
            try {
                paramText = new String(paramText.getBytes("iso-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        return paramText;
    }

    public String getShibbolethSession() {
        /* No need for utf conversion*/
        return getRequest().getHeader(SHIB_SESSION_ID);
    }

    public boolean getIsShibbolethSession() {
        return getShibbolethSession() != null;
    }

    public String getShibbolethEppn() {
        return getHeaderParam(SHIB_EPPN);
    }

    public String getShibbolethTargetedId() {
        return getHeaderParam(SHIB_TARGETED_ID);
    }

    public String getShibbolethPersistentId() {
        return getHeaderParam(SHIB_PERSISTENT_ID);
    }

    public String getShibbolethCn() {
        return getHeaderParam(SHIB_CN);
    }

    public String getShibbolethAffiliation() {
        return getHeaderParam(SHIB_AFFILIATION);
    }

    public String getShibbolethDisplayName() {
        return getHeaderParam(SHIB_DISPLAY_NAME);
    }

    public String getShibbolethGivenName() {
        return getHeaderParam(SHIB_GIVEN_NAME);
    }

    public String getShibbolethMail() {
        return getHeaderParam(SHIB_MAIL);
    }

    public String getShibbolethOrganization() {
        return getHeaderParam(SHIB_ORGANIZATION);
    }

    public String getShibbolethSn() {
        return getHeaderParam(SHIB_SN);
    }
}
