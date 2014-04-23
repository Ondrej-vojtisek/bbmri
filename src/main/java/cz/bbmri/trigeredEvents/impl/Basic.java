package cz.bbmri.trigeredEvents.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

abstract class Basic {

    @Value("${StoragePath}")
    protected String storagePath;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    protected void logger(String msg) {
        logger.debug("CRON fired at: " + new Date() + " with: " + msg);
    }

    // Testing logger  - single point to turn of logging
    protected void log(String msg) {
        logger.debug(msg);
    }

}
