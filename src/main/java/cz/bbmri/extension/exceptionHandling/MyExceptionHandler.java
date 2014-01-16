package cz.bbmri.extension.exceptionHandling;

import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.FileUploadLimitExceededException;
import net.sourceforge.stripes.exception.ActionBeanNotFoundException;
import net.sourceforge.stripes.exception.DefaultExceptionHandler;
import net.sourceforge.stripes.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 22.12.13
 * Time: 18:42
 * To change this template use File | Settings | File Templates.
 */
public class MyExceptionHandler extends DefaultExceptionHandler {
    private static final String NON_SPECIFIED_ERROR = "/errors/unspecified.jsp";

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public Resolution catchActionBeanNotFound(
        ActionBeanNotFoundException exc,
        HttpServletRequest req, HttpServletResponse resp){

        return new ErrorResolution(HttpServletResponse.SC_NOT_FOUND);
    }


    public Resolution catchAll(Throwable exc, HttpServletRequest req,
        HttpServletResponse resp){
        logger.debug(exc.getMessage());
        return new ForwardResolution(NON_SPECIFIED_ERROR);
    }
}
