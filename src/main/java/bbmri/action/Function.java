package bbmri.action;

import bbmri.entities.Project;
import bbmri.entities.Researcher;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 9:22
 * To change this template use File | Settings | File Templates.
 */
public class Function {
    public static boolean contains(List list, Object o) {
        List<Researcher> researchers = (List<Researcher>) list;
        Researcher researcher = (Researcher) o;
        return researchers.contains(researcher);
    }

    public static boolean ownProject(Object project, Object researcher) {
        if (project == null || researcher == null) {
            return false;
        }
        Researcher res = (Researcher) researcher;
        Project proj = (Project) project;

        if (proj.getOwner() == null) {
            return false;
        }

        return res.equals(proj.getOwner());
    }

    public static boolean isAdmin(Object administrator, Object loggedResearcher) {
        if (administrator == null || loggedResearcher == null) {
            return false;
        }
        Researcher admin = (Researcher) administrator;
        Researcher logged = (Researcher) loggedResearcher;

        return admin.equals(logged);
    }

}



