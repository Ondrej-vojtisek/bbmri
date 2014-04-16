package cz.bbmri.action.globalSettings;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.dao.GlobalSettingDao;
import cz.bbmri.entities.systemAdministration.GlobalSetting;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.3.14
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/globalsettings/{$event}")
public class GlobalSettingsActionBean extends PermissionActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private GlobalSettingDao globalSettingDao;

    @ValidateNestedProperties(value = {
               @Validate(field = "value",
                       required = true, on = "saveValidity")
       })
    private Integer validity;

    public GlobalSettingsActionBean(){
        setComponentManager(new ComponentManager());
    }

    public static Breadcrumb getBreadcrumb(boolean active) {
        return new Breadcrumb(GlobalSettingsActionBean.class.getName(),
                "all", false, "cz.bbmri.action.globalSettings.GlobalSettingsActionBean", active);
    }

    public int getValidity() {
        if (validity == null) {
            validity = globalSettingDao.getReservationValidity();
        }
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    /* Methods */
    @DontValidate
    @DefaultHandler
    @HandlesEvent("all")
    @RolesAllowed("administrator")
    public Resolution all() {
        getBreadcrumbs().add(GlobalSettingsActionBean.getBreadcrumb(true));
        return new ForwardResolution(GLOBAL_SETTINGS);
    }

    @HandlesEvent("saveValidity")
    @RolesAllowed("administrator")
    public Resolution saveValidity() {
        if(!globalSettingDao.set(GlobalSetting.RESERVATION_VALIDITY, validity.toString())){
            return new ForwardResolution(this.getClass(), "all");
        }

        successMsg(null);
        return new RedirectResolution(this.getClass(), "all");
    }
}
