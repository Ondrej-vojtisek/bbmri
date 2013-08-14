package tests.daoTest;

import bbmri.dao.UserDao;
import bbmri.entities.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tests.AbstractTest;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Test of BasicDao - all methods used in User, Role.. etc. Tested on User for example.
 * User: Ori
 * Date: 13.8.13
 * Time: 16:02
 * To change this template use File | Settings | File Templates.
 */
public class BasicDaoTest extends AbstractTest {

    @Autowired
    UserDao userDao;

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
        userDao.create(user);
        userDao.get(user.getId());
        assertEquals(user, userDao.get(user.getId()));
    }

    @Test
    public void remove() {
        User user = createTestUser();
        userDao.create(user);
        userDao.remove(user);
        assertEquals(null, userDao.get(user.getId()));
    }

    @Test
    public void count() {
        assertEquals(new Integer(0), userDao.count());
        User user = createTestUser();
        userDao.create(user);
        assertEquals(new Integer(1), userDao.count());
        userDao.create(createTestUser2());
        assertEquals(new Integer(2), userDao.count());
        userDao.remove(user);
        assertEquals(new Integer(1), userDao.count());
    }

    @Test
    public void all() {
        assertEquals(new ArrayList<User>(), userDao.all());
        User user = createTestUser();
        userDao.create(user);
        assertEquals(user, userDao.all().get(0));
        userDao.remove(user);
        assertEquals(new ArrayList<User>(), userDao.all());
    }


}
