package bbmri.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.10.13
 * Time: 16:20
 * To change this template use File | Settings | File Templates.
 */
public class BasicFacade{

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

       public static void notNull(final Object o) throws IllegalArgumentException {
           if (o == null) {
               throw new IllegalArgumentException("Object can't be a null object");
           }
       }

}
