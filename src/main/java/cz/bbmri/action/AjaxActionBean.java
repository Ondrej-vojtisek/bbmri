package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entity.temp.Money;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.ajax.JavaScriptResolution;
import net.sourceforge.stripes.validation.ValidationErrorHandler;
import net.sourceforge.stripes.validation.ValidationErrors;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class AjaxActionBean extends BasicActionBean implements ValidationErrorHandler {

    private static final String VIEW = "/login.jsp";
    private static final String RESULT = "/result.jsp";
    private static final String ERRORS = "/errors.jsp";

    public int youGiveMe;
    private Money money;

    @DefaultHandler
    public Resolution view() {
        return new ForwardResolution(VIEW);
    }

    public Money getMoney() {
        return money;
    }

    public Resolution doubleMoney() {
        money = new Money(youGiveMe, youGiveMe * 2);
        return new ForwardResolution(RESULT);
    }


    public Resolution handleValidationErrors(ValidationErrors errors) {
        return new ForwardResolution(ERRORS);
    }

}
