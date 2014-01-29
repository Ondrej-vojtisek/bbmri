package cz.bbmri.action.biobank;

import cz.bbmri.action.FindActionBean;
import net.sourceforge.stripes.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.11.13
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
@HttpCache(allow = false)
@UrlBinding("/biobank/addAdministrator/{$event}/{id}")
public class FindBiobankAdminActionBean extends FindActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @DefaultHandler
    public Resolution display() {
        return new ForwardResolution(BIOBANK_DETAIL_ADD_ADMINISTRATOR);
    }

    @HandlesEvent("find")
    public Resolution find() {
        return new ForwardResolution(BIOBANK_DETAIL_ADD_ADMINISTRATOR)
                .addParameter("UserFind", getUserFind())
                .addParameter("id", id);
    }
}
