package cz.bbmri.entities.webEntities;

/**
 * Object representing one breadcrumb on web page. Breadcrumb is a navigaion tool used on webpages. More can be found at
 * http://en.wikipedia.org/wiki/Breadcrumb_(navigation).
 * Breadcrumb can be active - means current page which user reads, or passive - link to visited pages.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class Breadcrumb {


    /**
     * Text of breadcrumb. It can be localizable or simple string - behaviour is defined by "notMsgKey"
     */
    private String msgKey;

    /**
     * Breadcrumb is a link to visited page. ActionBean is part of link.
     */
    private String actionBean;

    /**
     * Breadcrumb is a link to visited page. Event is part of link (which event from the actionBean will be triggered)
     */
    private String event;

    /**
     * Breadcrumb is a link to visited page. If link need param specification "param" can be used. for instance param can be
     * "projectId".
     */
    private String param;

    /**
     * Value of param. For instance param="projectId" paramValue="1" - link to page in context of project with id 1
     */
    private Long paramValue;

    /**
     * Active breadcrumb is the one referring to currently open page, active is set to false for visited pages
     */
    private boolean active;

    /**
     * Flag how to interpret attribute msg. if true than msg is interpreted as String and it is not localized.
     */
    private boolean notMsgKey = false;

    /**
     * Text which can be added to msgKey but which is not localized even if msg is translated.
     * Typical usage is when we want to create breadcrumb of Container (or Box, Rack, ...). Than we set rack as localizable
     * message but we would also like to print the name of rack. Set objectName as "rack.name"
     * -> output: "Localized.string.for.Rack" + "separator defined on .jsp" + "not localized Rack name"
     */
    private String objectName;

    /**
     * Constructor for events where it is not necessary to define param.
     *
     * @param actionBean
     * @param event
     * @param notMsgKey
     * @param msgKey
     * @param active
     */
    public Breadcrumb(String actionBean, String event, boolean notMsgKey, String msgKey, boolean active) {
        this.actionBean = actionBean;
        this.event = event;
        this.notMsgKey = notMsgKey;
        this.msgKey = msgKey;
        this.active = active;
    }

    /**
     * Constructor for events when it is necessary to define param and value of param
     *
     * @param actionBean
     * @param event
     * @param notMsgKey
     * @param msgKey
     * @param active
     * @param param
     * @param paramValue
     */
    public Breadcrumb(String actionBean, String event, boolean notMsgKey, String msgKey, boolean active,
                      String param, Long paramValue) {
        this.actionBean = actionBean;
        this.event = event;
        this.notMsgKey = notMsgKey;
        this.msgKey = msgKey;
        this.active = active;
        this.param = param;
        this.paramValue = paramValue;
    }

    /**
     * Param + param value + objectName in addition.
     *
     * @param actionBean
     * @param event
     * @param notMsgKey
     * @param msgKey
     * @param active
     * @param param
     * @param paramValue
     * @param objectName
     */
    public Breadcrumb(String actionBean, String event, boolean notMsgKey, String msgKey, boolean active, String param, Long paramValue, String objectName) {
        this.actionBean = actionBean;
        this.event = event;
        this.notMsgKey = notMsgKey;
        this.msgKey = msgKey;
        this.active = active;
        this.param = param;
        this.paramValue = paramValue;
        this.objectName = objectName;
    }

    public String getMsgKey() {
        return msgKey;
    }

    public void setMsgKey(String msgKey) {
        this.msgKey = msgKey;
    }

    public String getActionBean() {
        return actionBean;
    }

    public void setActionBean(String actionBean) {
        this.actionBean = actionBean;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Long getParamValue() {
        return paramValue;
    }

    public void setParamValue(Long paramValue) {
        this.paramValue = paramValue;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isNotMsgKey() {
        return notMsgKey;
    }

    public void setNotMsgKey(boolean notMsgKey) {
        this.notMsgKey = notMsgKey;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
}
