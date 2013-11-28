package bbmri.facade.impl;

import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.Message;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.10.13
 * Time: 16:20
 * To change this template use File | Settings | File Templates.
 */
public class BasicFacade {

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public static void notNull(final Object o) throws IllegalArgumentException {
        if (o == null) {
            throw new IllegalArgumentException("Object can't be a null object");
        }
    }

    public static void objectNotFound(final Object o, final Long id) throws IllegalArgumentException {
        if (o == null) {
            throw new IllegalArgumentException("Object can't be a null object. " +
                    "It means that given identifier isn't present in database. ID: " + id);
        }
    }

    public static void fatalError(ValidationErrors errors){
        errors.addGlobalError(new LocalizableError("bbmri.facade.impl.BasicFacade.dbg.fatal"));
    }



}
