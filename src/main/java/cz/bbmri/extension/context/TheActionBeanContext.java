package cz.bbmri.extension.context;

import cz.bbmri.entities.Project;
import cz.bbmri.entities.User;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.validation.LocalizableError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 24.10.12
 * Time: 0:45
 * To change this template use File | Settings | File Templates.
 */

public class TheActionBeanContext extends ActionBeanContext {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /* Shibboleth attributes */

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
    private static final String SHIB_MEFAPERSON = "mefaperson";


    private static final String MY_ID = "myId";
//    private static final String SAMPLE_ID = "sample";
//    private static final String PROJECT_ID = "project";
//    private static final String BIOBANK_ID = "biobank";
//    private static final String REQUEST_GROUP_ID = "requestGroup";
//    private static final String REQUEST_ID = "request";
//    private static final String USER_ID = "userId";

    private static final String BBMRI_PROPERTIES = "bbmri_general.properties";
    private static final String PROPERTIES_STORAGE_PATH = "StoragePath";


    /**
     * Stores the given attribute in session.
     *
     * @param key   Attribute key
     * @param value Stored value
     */
    protected void setCurrent(String key, Object value) {
        // Retrieve the session instance and set attribute
        getRequest().getSession().setAttribute(key, value);
    }

    /**
     * Searches for an attribute in session according to given key.
     *
     * @param key          Session attribute key
     * @param defaultValue Default value
     * @return Loaded value or null
     */
    protected <T> T getCurrent(String key, T defaultValue) {
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
        // Forget the value
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

    public void dropMyId() {
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


    public Project getProject() {
        return (Project) getRequest().getSession().getAttribute("project");
    }

    public void setProject(Project project) {
        getRequest().getSession().setAttribute("project", project);
    }


    public String getHeaderParam(String param) {
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
        if (getShibbolethSession() == null) return false;

        return true;
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

    /*TODO: String -> Boolean or boolean*/
    public String getShibbolethMefaPerson() {
        return getHeaderParam(SHIB_MEFAPERSON);
    }

    public String getShibbolethOrganization() {
        return getHeaderParam(SHIB_ORGANIZATION);
    }

    public String getShibbolethSn() {
        return getHeaderParam(SHIB_SN);
    }

    public String getPropertiesStoragePath() {
        if (getProperties().get(PROPERTIES_STORAGE_PATH).toString() == null) {
            getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.TheActionBeanContext.bbmriGeneralNotFound"));
            return null;
        }
        return getProperties().get(PROPERTIES_STORAGE_PATH).toString();
    }

    private Properties getProperties() {
        return loadProperties("/" + BBMRI_PROPERTIES);
    }


    private Properties loadProperties(String fileName) {
        try {
            // Read the resource and cast them into properties
            Resource resource = new ClassPathResource(fileName);
            return PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            return new Properties();
        }
    }
}
