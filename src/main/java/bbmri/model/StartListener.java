package bbmri.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartListener implements ServletContextListener {

    Logger logger = LoggerFactory.getLogger("StartListener.class");

    @Override
    public void contextInitialized(ServletContextEvent ev) {
      //  logger.info("Inicialization phase");
       // ServletContext servletContext = ev.getServletContext();

    }

    @Override
    public void contextDestroyed(ServletContextEvent ev) {
      //  logger.info("End phase");
    }
}