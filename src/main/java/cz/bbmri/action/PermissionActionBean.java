package cz.bbmri.action;

import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.dao.PermissionDAO;
import cz.bbmri.entity.Permission;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/permission/{$event}/{id}")
public class PermissionActionBean  extends ComponentActionBean {

    @SpringBean
    private PermissionDAO permissionDAO;

    public List<Permission> getAll(){
           return permissionDAO.all();
       }
}
