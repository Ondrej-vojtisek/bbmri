package tests.daoTest;

import cz.bbmri.dao.BiobankAdministratorDao;
import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 1.10.13
 * Time: 11:27
 * To change this template use File | Settings | File Templates.
 */
public class BiobankAdministratorDaoTest extends AbstractDaoTest {

    @Autowired
    UserDao userDao;

    @Autowired
    BiobankDao biobankDao;

    @Autowired
    BiobankAdministratorDao biobankAdministratorDao;

    @Test
    public void containsTest() {
        User user1 = createTestUser(1);
        userDao.create(user1);
        User user2 = createTestUser(2);
        userDao.create(user2);

        Biobank biobank = createTestBiobank(1);
        biobankDao.create(biobank);

        BiobankAdministrator ba = createTestBiobankAdministrator();
        ba.setPermission(Permission.MANAGER);
        ba.setBiobank(biobank);
        ba.setUser(user1);

        biobankAdministratorDao.create(ba);

        assertEquals(true, biobankAdministratorDao.contains(biobank, user1));
        assertEquals(false, biobankAdministratorDao.contains(biobank, user2));
    }

     @Test
    public void containsTest2() {
        User user1 = createTestUser(1);
        userDao.create(user1);
        User user2 = createTestUser(2);
        userDao.create(user2);

        Biobank biobank = createTestBiobank(1);
        biobankDao.create(biobank);

        BiobankAdministrator pa = createTestBiobankAdministrator();
        pa.setPermission(Permission.MANAGER);
        pa.setBiobank(biobank);
        pa.setUser(user1);
        biobankAdministratorDao.create(pa);

        BiobankAdministrator pa2 = createTestBiobankAdministrator();
        pa2.setPermission(Permission.MANAGER);
        pa2.setBiobank(biobank);
        pa2.setUser(user2);
        biobankAdministratorDao.create(pa2);

        assertEquals(true, biobankAdministratorDao.contains(biobank, user1));
        assertEquals(true, biobankAdministratorDao.contains(biobank, user2));
    }

     @Test
    public void containsTest3() {
        User user1 = createTestUser(1);
        userDao.create(user1);

        Biobank biobank = createTestBiobank(1);
        biobankDao.create(biobank);

        BiobankAdministrator pa = createTestBiobankAdministrator();
        pa.setPermission(Permission.MANAGER);
        pa.setBiobank(biobank);
        pa.setUser(user1);
        biobankAdministratorDao.create(pa);

        assertEquals(false, biobankAdministratorDao.contains(biobank, null));
        assertEquals(false, biobankAdministratorDao.contains(null, user1));
    }

     @Test
    public void getTest() {
        User user1 = createTestUser(1);
        userDao.create(user1);
        User user2 = createTestUser(2);
        userDao.create(user2);

        Biobank biobank = createTestBiobank(1);
        biobankDao.create(biobank);

        BiobankAdministrator pa = createTestBiobankAdministrator();
        pa.setPermission(Permission.MANAGER);
        pa.setBiobank(biobank);
        pa.setUser(user1);

        biobankAdministratorDao.create(pa);

        assertEquals(pa, biobankAdministratorDao.get(biobank, user1));
        assertEquals(null, biobankAdministratorDao.get(biobank, user2));
    }
}
