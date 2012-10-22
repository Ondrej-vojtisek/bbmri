package bbmri.model;

import bbmri.entities.Project;
import bbmri.entities.Researcher;
import net.sourceforge.stripes.action.ActionBeanContext;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MujActionBeanContext extends ActionBeanContext {

    @SuppressWarnings({"unchecked"})
    public List<Researcher> getResearchers() {

        List<Researcher> zaz = (List<Researcher>) getRequest().getSession().getAttribute("res");
        if (zaz == null) {
            getRequest().getSession().setAttribute("res", new ArrayList<Researcher>());
        }/*else if(Researcher.class != getRequest().getSession().getAttribute("zaz").getClass()){
                return null;
        }  */

        return zaz;
    }

    @SuppressWarnings({"unchecked"})
    public List<Project> getProjects() {
       /*     if(Project.class != getRequest().getSession().getAttribute("zaz").getClass()){
                return null;
        }*/

        List<Project> zaz = (List<Project>) getRequest().getSession().getAttribute("zaz");
        if (zaz == null) {
            getRequest().getSession().setAttribute("zaz", new ArrayList<Project>());
        }

        return zaz;
    }

     @SuppressWarnings({"unchecked"})
    public List<Zaznam> getZaznamy() {
      /*  if(Zaznam.class != getRequest().getSession().getAttribute("zaz").getClass()){
                return null;
        }  */
        List<Zaznam> zaz = (List<Zaznam>) getRequest().getSession().getAttribute("zaz");
        if (zaz == null) {
            getRequest().getSession().setAttribute("zaz", new ArrayList<Zaznam>());
        }
        return zaz;
    }
}