package tests;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.8.13
 * Time: 14:03
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-applicationContext.xml"})
@Transactional
@Ignore
public class AbstractTest {
    /**
       * Logger to log all messages to.
       */

      public Logger logger = LoggerFactory.getLogger(this.getClass().getName());

      /**
       * This method logs a message for INFO level.
       *
       * @param message message to log
       */
      protected void log(String message) {
          logger.debug(message);
      }

}
