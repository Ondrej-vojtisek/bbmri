package cz.bbmri.entities.webEntities;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.3.14
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
public class Breadcrumb {

    private String msgKey;

    private String actionBean;

    private String event;

    private String param;

    private Long paramValue;

    private boolean active;

    // if true then msgKey interpreted as String not as localized String
    private boolean notMsgKey = false;

    private String objectName;

    public Breadcrumb(String actionBean, String event, boolean notMsgKey, String msgKey, boolean active) {
        this.actionBean = actionBean;
        this.event = event;
        this.notMsgKey = notMsgKey;
        this.msgKey = msgKey;
        this.active = active;
    }

    public Breadcrumb(String actionBean, String event, boolean notMsgKey, String msgKey, boolean active, String param, Long paramValue) {
        this.actionBean = actionBean;
        this.event = event;
        this.notMsgKey = notMsgKey;
        this.msgKey = msgKey;
        this.active = active;
        this.param = param;
        this.paramValue = paramValue;
    }


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
