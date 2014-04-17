package cz.bbmri.action;

import cz.bbmri.entities.User;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class Function {
    public static boolean contains(List list, Object o) {
        List<User> users = (List<User>) list;
        User user = (User) o;
        return users.contains(user);
    }


}



