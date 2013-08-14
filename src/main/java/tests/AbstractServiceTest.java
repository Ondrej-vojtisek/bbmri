package tests;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 14.8.13
 * Time: 16:14
 * To change this template use File | Settings | File Templates.
 */

@RunWith(MockitoJUnitRunner.class)
@Ignore
public class AbstractServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public void log(String msg){
           logger.debug(msg);

    }
}
