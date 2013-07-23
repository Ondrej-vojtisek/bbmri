package bbmri.action;

import bbmri.entities.Biobank;
import bbmri.entities.Project;
import bbmri.entities.User;

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
        List<User> users = (List<User>) list;
        User user = (User) o;
        return users.contains(user);
    }

    public static boolean ownProject(Object project, Object user) {
        if (project == null || user == null) {
            return false;
        }
        User usr = (User) user;
        Project proj = (Project) project;

        if (proj.getOwner() == null) {
            return false;
        }

        return usr.equals(proj.getOwner());
    }

}



