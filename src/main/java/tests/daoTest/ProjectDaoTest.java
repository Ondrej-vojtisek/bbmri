package tests.daoTest;

import cz.bbmri.dao.ProjectDao;
import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.enumeration.ProjectState;
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

        project1.setProjectState(ProjectState.CONFIRMED);
        projectDao.update(project1);

        assertEquals(1, projectDao.getAllByProjectState(ProjectState.CONFIRMED).size());
        assertEquals(true, projectDao.getAllByProjectState(ProjectState.CONFIRMED).contains(project1));

        assertEquals(2, projectDao.getAllByProjectState(ProjectState.NEW).size());
        assertEquals(true, projectDao.getAllByProjectState(ProjectState.CONFIRMED).contains(project2));
        assertEquals(true, projectDao.getAllByProjectState(ProjectState.CONFIRMED).contains(project3));
    }
}
