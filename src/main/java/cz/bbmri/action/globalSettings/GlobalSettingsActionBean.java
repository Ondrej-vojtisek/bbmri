package cz.bbmri.action.globalSettings;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.dao.GlobalSettingDao;
import cz.bbmri.entities.systemAdministration.GlobalSetting;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.IntegerTypeConverter;
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
    private GlobalSetting validity;

    public GlobalSetting getValidity() {
        if (validity == null) {
            validity = globalSettingDao.get(GlobalSetting.RESERVATION_VALIDITY);
        }
        return validity;
    }

    public void setValidity(GlobalSetting validity) {
        this.validity = validity;
    }

    /* Methods */
    @DontValidate
    @DefaultHandler
    @HandlesEvent("all")
    @RolesAllowed("administrator")
    public Resolution all() {
        return new ForwardResolution(GLOBAL_SETTINGS);
    }

    @HandlesEvent("saveValidity")
    @RolesAllowed("administrator")
    public Resolution saveValidity() {

        if(!globalSettingDao.set(GlobalSetting.RESERVATION_VALIDITY, validity.getValue())){
        return new ForwardResolution(GLOBAL_SETTINGS);
        }
        successMsg(null);
        return new RedirectResolution(GLOBAL_SETTINGS);
    }
}
