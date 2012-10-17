package bbmri.model;

import bbmri.entities.Project;
import bbmri.entities.Researcher;
import net.sourceforge.stripes.action.ActionBeanContext;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MujActionBeanContext extends ActionBeanContext {
    @SuppressWarnings({"unchecked"})
    public List<Zaznam> getZaznamy() {
        List<Zaznam> zaz = (List<Zaznam>) getRequest().getSession().getAttribute("zaz");
        if (zaz == null) {
            getRequest().getSession().setAttribute("zaz", new ArrayList<Zaznam>());
        }
        return zaz;
    }

    @SuppressWarnings({"unchecked"})
    public List<Researcher> getResearchers() {
        List<Researcher> zaz = (List<Researcher>) getRequest().getSession().getAttribute("zaz");
        if (zaz == null) {
            getRequest().getSession().setAttribute("zaz", new ArrayList<Researcher>());
        }
        return zaz;
    }

    @SuppressWarnings({"unchecked"})
    public List<Project> getProjects() {


        List<Project> zaz = (List<Project>) getRequest().getSession().getAttribute("zaz");
        if (zaz == null) {
            getRequest().getSession().setAttribute("zaz", new ArrayList<Project>());
        }
        return zaz;
    }

              /*
    public void pokus(){
                getServletContext().getAttribute("owner");

    }        */
}