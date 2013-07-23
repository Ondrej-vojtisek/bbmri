package bbmri.action;

import net.sourceforge.stripes.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 17.7.13
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */

@HttpCache(allow=false)
@RolesAllowed({"user", "administrator"})
@UrlBinding("/dashboard")
public class DashboardActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private String Data;


    public String getData(){
        if(getLoggedUser() == null){
            return "NULL";
        }
        String s = getLoggedUser().toString() + " has roles: " + getLoggedUser().getRoles();
        return s;
    }

    private static final String DEFAULT = "/dashboard.jsp";

       @DefaultHandler
       public Resolution view(){
           return new ForwardResolution(DEFAULT);
       }

    @HandlesEvent("edit")
    public Resolution edit(){

        return null;
    }

    @PermitAll
    @HandlesEvent("iamsecure")
   public Resolution videt() {
      return new ForwardResolution("/WEB-INF/jsp/some.jsp");
   }

    @RolesAllowed({"developer"})
       @HandlesEvent("develop")
      public Resolution pokus() {
         return new ForwardResolution("/WEB-INF/jsp/some.jsp");
    }

}
