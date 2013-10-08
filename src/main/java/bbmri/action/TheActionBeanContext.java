package bbmri.action;

import bbmri.entities.*;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 24.10.12
 * Time: 0:45
 * To change this template use File | Settings | File Templates.
 */

public class TheActionBeanContext extends ActionBeanContext {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final String MY_ID = "myId";
    private static final String SAMPLE_ID = "sample";
    private static final String PROJECT_ID = "project";
    private static final String BIOBANK_ID = "biobank";
    private static final String REQUEST_GROUP_ID = "requestGroup";
    private static final String REQUEST_ID = "request";

    private static final String USER_ID = "userId";


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

    public void setRequestId(Long id) {
        setCurrent(REQUEST_ID, id);
    }

    public Long getRequestId() {
        return getCurrent(REQUEST_ID, null);
    }

    public void dropRequestId() {
        setCurrent(REQUEST_ID, null);
    }

    public void setRequestId(Request request) {
        // Forget the value
        dropRequestId();

        // When user is given
        if (request != null) {
            // Update the identifier
            setRequestId(request.getId());
        }
    }


    public void setUserId(Long id) {
        setCurrent(USER_ID, id);
    }

    public Long getUserId() {
        return getCurrent(USER_ID, null);
    }

    public void dropUserId() {
        setCurrent(USER_ID, null);
    }


    public void setRequestGroupId(Long id) {
        setCurrent(REQUEST_GROUP_ID, id);
    }

    public Long getRequestGroupId() {
        return getCurrent(REQUEST_GROUP_ID, null);
    }

    public void dropRequestGroupId() {
        setCurrent(REQUEST_GROUP_ID, null);
    }

    public void setRequestGroupId(RequestGroup requestGroup) {
        // Forget the value
        dropRequestGroupId();

        // When user is given
        if (requestGroup != null) {
            // Update the identifier
            setRequestGroupId(requestGroup.getId());
        }
    }


    public Project getProject() {
        return (Project) getRequest().getSession().getAttribute("project");
    }

    public void setProject(Project project) {
        getRequest().getSession().setAttribute("project", project);
    }

    public Biobank getBiobank() {
        return (Biobank) getRequest().getSession().getAttribute("biobank");
    }

    public void setBiobank(Biobank biobank) {
        getRequest().getSession().setAttribute("biobank", biobank);
    }

    public Sample getSample() {
        return (Sample) getRequest().getSession().getAttribute("sample");
    }

    public void setSample(Sample sample) {
        getRequest().getSession().setAttribute("sample", sample);
    }

    public SampleQuestion getSampleQuestion() {
        return (SampleQuestion) getRequest().getSession().getAttribute("sampleQuestion");
    }

    public void setSampleQuestion(SampleQuestion sampleQuestion) {
        getRequest().getSession().setAttribute("sampleQuestion", sampleQuestion);
    }

}
