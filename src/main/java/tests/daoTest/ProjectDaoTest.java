package tests.daoTest;

import bbmri.dao.ProjectDao;
import bbmri.dao.UserDao;
import bbmri.entities.Project;
import bbmri.entities.enumeration.ProjectState;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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


    @Test
    public void getAllByProjectStateTest() {
        Project project1 = createTestProject(1);
        projectDao.create(project1);

        Project project2 = createTestProject(2);
        projectDao.create(project2);

        Project project3 = createTestProject(3);
        projectDao.create(project3);

        project1.setProjectState(ProjectState.APPROVED);
        projectDao.update(project1);

        assertEquals(1, projectDao.getAllByProjectState(ProjectState.APPROVED).size());
        assertEquals(true, projectDao.getAllByProjectState(ProjectState.APPROVED).contains(project1));

        assertEquals(2, projectDao.getAllByProjectState(ProjectState.NEW).size());
        assertEquals(true, projectDao.getAllByProjectState(ProjectState.APPROVED).contains(project2));
        assertEquals(true, projectDao.getAllByProjectState(ProjectState.APPROVED).contains(project3));
    }
}
