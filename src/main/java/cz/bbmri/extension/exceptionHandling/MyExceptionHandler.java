package cz.bbmri.extension.exceptionHandling;

import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.exception.ActionBeanNotFoundException;
import net.sourceforge.stripes.exception.DefaultExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class MyExceptionHandler extends DefaultExceptionHandler {
    private static final String NON_SPECIFIED_ERROR = "/errors/unspecified.jsp";

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public Resolution catchActionBeanNotFound(
        ActionBeanNotFoundException exc,
        HttpServletRequest req, HttpServletResponse resp){

        return new ErrorResolution(HttpServletResponse.SC_NOT_FOUND);
    }


//    public Resolution catchAll(Throwable exc, HttpServletRequest req,
//        HttpServletResponse resp){
//        exc.printStackTrace();
//        logger.debug(exc.getMessage());
//        return new ForwardResolution(NON_SPECIFIED_ERROR);
//    }
}
