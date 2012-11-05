package bbmri.service;

import bbmri.entities.Researcher;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 17:08
 * To change this template use File | Settings | File Templates.
 */
public interface LoginService {

    public Researcher login(Long id, String password);

    public void logout(Researcher researcher);

}
