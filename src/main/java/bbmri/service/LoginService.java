package bbmri.service;

import bbmri.entities.User;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 */
public interface LoginService {

    User login(Long id, String password);

   // void logout(User user);

}
