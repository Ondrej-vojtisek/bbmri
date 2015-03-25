package cz.bbmri.trigeredEvents.impl;

import com.sun.org.apache.xml.internal.serializer.utils.SerializerMessages_zh_CN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * Basic class for all class managing auto triggered events. All methods used in more than one class is defined here.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

abstract class Basic /* extends BasicServiceImpl */ {

    @Value("${StoragePath}")
    protected String storagePath;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    protected void logger(String msg) {
        logger.debug("CRON fired at: " + new Date() + " with: " + msg);
    }

    /**
     * Testing logger - single point to turn it off/on
     *
     * @param msg
     */
    protected void log(String msg) {
        logger.debug(msg);
    }

}
