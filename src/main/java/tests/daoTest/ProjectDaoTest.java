package tests.daoTest;

import bbmri.dao.ProjectDao;
import bbmri.dao.UserDao;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.8.13
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */
public class ProjectDaoTest extends AbstractDaoTest {

    @Autowired
    ProjectDao projectDao;

    @Autowired
    UserDao userDao;

    private List<Project> prepareTestedEnvironment(User user) {
        Project project1 = createTestProject(1);
        projectDao.create(project1);

        project1.getUsers().add(user);
        projectDao.update(project1);

        Project project2 = createTestProject(2);
        projectDao.create(project2);

        project2.getUsers().add(user);
        projectDao.update(project2);

        Project project3 = createTestProject(3);
        project3.setProjectState(ProjectState.APPROVED);
        projectDao.create(project3);

        project3.getUsers().add(user);
        projectDao.update(project3);

        List<Project> results = new ArrayList<Project>();

        results.add(project1);
        results.add(project2);
        results.add(project3);
        return results;
    }

    @Test
    public void getAllByProjectStateTest() {
        User user = createTestUser(1);
        userDao.create(user);
        List<Project> results = prepareTestedEnvironment(user);
        List<Project> approved = projectDao.getAllByProjectState(ProjectState.APPROVED);
        assertEquals(1, approved.size());
        assertEquals(approved.get(0), results.get(2));
        List<Project> newProjects = projectDao.getAllByProjectState(ProjectState.NEW);
        assertEquals(2, newProjects.size());
        assertEquals(newProjects.get(0), results.get(0));
        assertEquals(newProjects.get(1), results.get(1));
    }
}
