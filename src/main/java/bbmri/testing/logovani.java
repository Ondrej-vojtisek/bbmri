package bbmri.testing;
/*
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class logovani {

  public static void main(String[] args) {

    Logger logger = LoggerFactory.getLogger("bbmri.testing.logovani");
    logger.info("Hello world.");

  }
}  */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

public class logovani {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger("bbmri.testing.logovani");
        logger.debug("Hello world2.");

        // print internal state
        //LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        //StatusPrinter.print(lc);
    }
}