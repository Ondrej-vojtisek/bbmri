package bbmri.action;

import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 28.11.13
 * Time: 14:39
 * To change this template use File | Settings | File Templates.
 */
public class PreLoginActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    @DontValidate
    @HandlesEvent("checkHeaders")
    public Resolution checkHeaders(){

        logger.debug("CheckHeaders");

        if(getContext().getIsShibbolethSession()){
            logger.debug("Touch");
            return new ForwardResolution("/touch.jsp");
        }else{
            logger.debug("Index");
            return new ForwardResolution("/login.jsp");
        }
    }
}
