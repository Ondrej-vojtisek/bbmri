package bbmri.action.project;

import bbmri.action.FindActionBean;
import net.sourceforge.stripes.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.11.13
 * Time: 16:36
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/project/addAdministrator/{$event}/{id}")
public class FindProjectAdminActionBean extends FindActionBean {

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
        return new ForwardResolution(PROJECT_DETAIL_ADMINISTRATORS_ADD);
    }

    @HandlesEvent("find")
    public Resolution find() {
        return new ForwardResolution(PROJECT_DETAIL_ADMINISTRATORS_ADD)
                .addParameter("UserFind", getUserFind())
                .addParameter("id", id);
    }
}
