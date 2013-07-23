package bbmri.action;


import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EssentialActionBean implements ActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private TheActionBeanContext context;

    @Override
    public TheActionBeanContext getContext() {
        return context;
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = (TheActionBeanContext) context;
    }
}