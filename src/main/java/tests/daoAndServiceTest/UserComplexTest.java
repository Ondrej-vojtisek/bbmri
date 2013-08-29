package tests.daoAndServiceTest;

import bbmri.entities.Project;
import bbmri.entities.User;
import bbmri.service.ProjectService;
import bbmri.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tests.AbstractTest;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 29.8.13
 * Time: 14:17
 * To change this template use File | Settings | File Templates.
 */
public class UserComplexTest extends AbstractTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

          /* ********* GIVEN ********** */
          /* ********* WHEN ********** */
          /* ********* THEN ********** */

    @Test
    public void getAllProjectsTest() {
          /* ********* GIVEN ********** */
        User user = createTestUser(1);
        userService.create(user);

        Project project = createTestProject(1);
        projectService.create(project, user.getId());

        Project project2 = createTestProject(2);
        projectService.create(project2, user.getId());

          /* ********* WHEN ********** */

          /* ********* THEN ********** */

        assertEquals(2, user.getProjects().size());
        assertEquals(true, user.getProjects().contains(project));
        assertEquals(true, user.getProjects().contains(project2));
        }

}
