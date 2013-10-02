package tests.daoTest;

import bbmri.dao.ProjectAdministratorDao;
import bbmri.dao.ProjectDao;
import bbmri.dao.UserDao;
import bbmri.entities.enumeration.Permission;
import bbmri.entities.Project;
import bbmri.entities.ProjectAdministrator;
import bbmri.entities.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 1.10.13
 * Time: 10:53
 * To change this template use File | Settings | File Templates.
 */
public class ProjectAdministratorDaoTest extends AbstractDaoTest {

    @Autowired
    UserDao userDao;

    @Autowired
    ProjectDao projectDao;

    @Autowired
    ProjectAdministratorDao projectAdministratorDao;

    @Test
    public void containsTest() {
        User user1 = createTestUser(1);
        userDao.create(user1);
        User user2 = createTestUser(2);
        userDao.create(user2);

        Project project = createTestProject(1);
        projectDao.create(project);

        ProjectAdministrator pa = createTestProjectAdministrator();
        pa.setPermission(Permission.MANAGER);
        pa.setProject(project);
        pa.setUser(user1);

        projectAdministratorDao.create(pa);

        assertEquals(true, projectAdministratorDao.contains(project, user1));
        assertEquals(false, projectAdministratorDao.contains(project, user2));
    }

    @Test
    public void containsTest2() {
        User user1 = createTestUser(1);
        userDao.create(user1);
        User user2 = createTestUser(2);
        userDao.create(user2);

        Project project = createTestProject(1);
        projectDao.create(project);

        ProjectAdministrator pa = createTestProjectAdministrator();
        pa.setPermission(Permission.MANAGER);
        pa.setProject(project);
        pa.setUser(user1);
        projectAdministratorDao.create(pa);

        ProjectAdministrator pa2 = createTestProjectAdministrator();
        pa2.setPermission(Permission.MANAGER);
        pa2.setProject(project);
        pa2.setUser(user2);
        projectAdministratorDao.create(pa2);

        assertEquals(true, projectAdministratorDao.contains(project, user1));
        assertEquals(true, projectAdministratorDao.contains(project, user2));
    }

    @Test
        public void containsTest3() {
            User user1 = createTestUser(1);
            userDao.create(user1);

            Project project = createTestProject(1);
            projectDao.create(project);

            ProjectAdministrator pa = createTestProjectAdministrator();
            pa.setPermission(Permission.MANAGER);
            pa.setProject(project);
            pa.setUser(user1);
            projectAdministratorDao.create(pa);

            assertEquals(false, projectAdministratorDao.contains(project, null));
            assertEquals(false, projectAdministratorDao.contains(null, user1));
        }

    @Test
    public void getTest() {
        User user1 = createTestUser(1);
        userDao.create(user1);
        User user2 = createTestUser(2);
        userDao.create(user2);

        Project project = createTestProject(1);
        projectDao.create(project);

        ProjectAdministrator pa = createTestProjectAdministrator();
        pa.setPermission(Permission.MANAGER);
        pa.setProject(project);
        pa.setUser(user1);

        projectAdministratorDao.create(pa);

        assertEquals(pa, projectAdministratorDao.get(project, user1));
        assertEquals(null, projectAdministratorDao.get(project, user2));
    }
}
