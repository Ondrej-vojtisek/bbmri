package tests.daoTest;

import bbmri.dao.ProjectDao;
import bbmri.dao.RequestGroupDao;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.RequestGroup;
import bbmri.entities.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tests.AbstractTest;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.8.13
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */
public class RequestGroupDaoTest extends AbstractTest {

    // List<RequestGroup> getAllByProject(Project project);

    @Autowired
    RequestGroupDao requestGroupDao;

    @Autowired
    ProjectDao projectDao;

    @Test
    public void getAllByProjectTest() {
        Project project1 = createTestProject(1);
        projectDao.create(project1);
        Project project2 = createTestProject(2);
        projectDao.create(project2);
        RequestGroup rq1 = createRequestGroup(1);
        requestGroupDao.create(rq1);
        RequestGroup rq2 = createRequestGroup(2);
        requestGroupDao.create(rq2);
        RequestGroup rq3 = createRequestGroup(3);
        requestGroupDao.create(rq3);

        rq1.setProject(project1);
        rq2.setProject(project1);
        rq3.setProject(project2);

        List<RequestGroup> results = requestGroupDao.getAllByProject(project1);
        log("*** " + results);

        assertEquals(true, results.contains(rq1));
        assertEquals(true, results.contains(rq2));
        assertEquals(false, results.contains(rq3));
        results = requestGroupDao.getAllByProject(project2);
        assertEquals(false, results.contains(rq1));
        assertEquals(false, results.contains(rq2));
        assertEquals(true, results.contains(rq3));
    }
}
