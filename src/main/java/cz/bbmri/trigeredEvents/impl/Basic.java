package cz.bbmri.trigeredEvents.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 17.4.14
 * Time: 11:46
 * To change this template use File | Settings | File Templates.
 */
abstract class Basic {

    @Value("${StoragePath}")
    protected String storagePath;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    protected void logger(String msg) {
        logger.debug("CRON fired at: " + new Date() + " with: " + msg);
    }

}
