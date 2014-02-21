package cz.bbmri.action;

import cz.bbmri.entities.Project;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SampleType;

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


}



