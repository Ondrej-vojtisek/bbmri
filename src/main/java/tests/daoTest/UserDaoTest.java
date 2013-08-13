package tests.daoTest;

import bbmri.DAO.UserDAO;
import bbmri.entities.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tests.AbstractTest;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.8.13
 * Time: 16:02
 * To change this template use File | Settings | File Templates.
 */
public class UserDaoTest extends AbstractTest {

    @Autowired
    UserDAO userDAO;

    private User createTestUser() {
        User user = new User();
        user.setName("Pokusny");
        user.setSurname("Uzivatel");
        return user;
    }

    private User createTestUser2() {
        User user = new User();
        user.setName("Pokusny2");
        user.setSurname("Uzivatel2");
        return user;
    }

    @Test
    public void create() {
        User user = createTestUser();
        userDAO.create(user);
        userDAO.get(user.getId());
        assertEquals(user, userDAO.get(user.getId()));
    }

    @Test
    public void remove() {
        User user = createTestUser();
        userDAO.create(user);
        userDAO.remove(user);
        assertEquals(null, userDAO.get(user.getId()));
    }

    @Test
    public void count() {
        assertEquals(new Integer(0), userDAO.count());
        User user = createTestUser();
        userDAO.create(user);
        assertEquals(new Integer(1), userDAO.count());
        userDAO.create(createTestUser2());
        assertEquals(new Integer(2), userDAO.count());
        userDAO.remove(user);
        assertEquals(new Integer(1), userDAO.count());
    }

    @Test
    public void all() {
        assertEquals(new ArrayList<User>(), userDAO.all());
        User user = createTestUser();
        userDAO.create(user);
        assertEquals(user, userDAO.all().get(0));
        userDAO.remove(user);
        assertEquals(new ArrayList<User>(), userDAO.all());
    }


}
