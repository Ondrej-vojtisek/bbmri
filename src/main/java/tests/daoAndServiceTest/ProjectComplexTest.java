package tests.daoAndServiceTest;

import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.User;
import bbmri.service.ProjectService;
import bbmri.service.RequestGroupService;
import bbmri.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tests.AbstractTest;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 29.8.13
 * Time: 13:17
 * To change this template use File | Settings | File Templates.
 */
public class ProjectComplexTest extends AbstractTest {


    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RequestGroupService requestGroupService;

        /* ********* GIVEN ********** */
        /* ********* WHEN ********** */
        /* ********* THEN ********** */


    @Test
    public void createProjectTest() {
        /* ********* GIVEN ********** */
        Project project = createTestProject(1);
        User user = createTestUser(1);
        userService.create(user);

        /* ********* WHEN ********** */

        projectService.create(project, user.getId());

        /* ********* THEN ********** */

        assertEquals(1, project.getUsers().size());
        assertEquals(project.getOwner(), user);
        assertEquals(user.getProjects().contains(project), true);
        assertEquals(user.getProjects().size(), 1);
        assertEquals(1, projectService.all().size());
    }

    @Test
    public void removeProjectTest() {
        /* ********* GIVEN ********** */
        Project project = createTestProject(1);
        User user = createTestUser(1);
        userService.create(user);
        projectService.create(project, user.getId());

        User user2 = createTestUser(2);
        userService.create(user2);

        projectService.approve(project.getId(), user2.getId());


        // TODO: RequestGroup dodelat
        //RequestGroup requestGroup = createRequestGroup(1);
        //requestGroupService.create();

        /* ********* WHEN ********** */

        projectService.remove(project.getId());

        /* ********* THEN ********** */

        assertEquals(null, projectService.get(project.getId()));
        assertEquals(user, userService.get(user.getId()));
        assertEquals(user.getProjects().isEmpty(), true);

        assertEquals(user2, userService.get(user2.getId()));
        assertEquals(user2.getJudgedProjects().isEmpty(), true);
    }

    @Test
    public void approveProjectTest() {
        /* ********* GIVEN ********** */
        Project project = createTestProject(1);
        User user = createTestUser(1);
        userService.create(user);
        projectService.create(project, user.getId());

        User user2 = createTestUser(2);
        userService.create(user2);

        /* ********* WHEN ********** */

        projectService.approve(project.getId(), user2.getId());

        /* ********* THEN ********** */

        assertEquals(project.getProjectState(), ProjectState.APPROVED);
        assertEquals(user2.getJudgedProjects().contains(project), true);
        assertEquals(project.getJudgedByUser(), user2);
    }

    @Test
    public void denyProjectTest() {
           /* ********* GIVEN ********** */
        Project project = createTestProject(1);
        User user = createTestUser(1);
        userService.create(user);
        projectService.create(project, user.getId());

        User user2 = createTestUser(2);
        userService.create(user2);

           /* ********* WHEN ********** */

        projectService.deny(project.getId(), user2.getId());

           /* ********* THEN ********** */

        assertEquals(project.getProjectState(), ProjectState.DENIED);
        assertEquals(user2.getJudgedProjects().contains(project), true);
        assertEquals(project.getJudgedByUser(), user2);
    }

    @Test
    public void assignUserTest() {
        /* ********* GIVEN ********** */
        Project project = createTestProject(1);
        User user = createTestUser(1);
        userService.create(user);
        projectService.create(project, user.getId());

        Project project2 = createTestProject(2);
        projectService.create(project2, user.getId());

        User user2 = createTestUser(2);
        userService.create(user2);

        Project project3 = createTestProject(3);
        projectService.create(project3, user2.getId());

        /* ********* WHEN ********** */

        projectService.assignUser(user.getId(), project3.getId());

        /* ********* THEN ********** */

        assertEquals(user.getProjects().size(), 3);
        assertEquals(user2.getProjects().size(), 1);
        assertEquals(user.getProjects().contains(project), true);
        assertEquals(user.getProjects().contains(project2), true);
        assertEquals(user.getProjects().contains(project3), true);
        assertEquals(user2.getProjects().contains(project3), true);
        assertEquals(user2.getProjects().contains(project), false);
        assertEquals(user2.getProjects().contains(project2), false);
    }

    @Test
    public void removeUserFromTest() {
            /* ********* GIVEN ********** */
        Project project = createTestProject(1);
        User user = createTestUser(1);
        userService.create(user);
        projectService.create(project, user.getId());

        Project project2 = createTestProject(2);
        projectService.create(project2, user.getId());

        User user2 = createTestUser(2);
        userService.create(user2);

        Project project3 = createTestProject(3);
        projectService.create(project3, user2.getId());

        projectService.assignUser(user.getId(), project3.getId());

            /* ********* WHEN ********** */

        projectService.removeUserFromProject(user2.getId(), project3.getId());

            /* ********* THEN ********** */

        assertEquals(user.getProjects().size(), 3);
        assertEquals(user2.getProjects().isEmpty(), true);
        assertEquals(user.getProjects().contains(project), true);
        assertEquals(user.getProjects().contains(project2), true);
        assertEquals(user.getProjects().contains(project3), true);
    }

    @Test
    public void getAllWhichUserAdministrateTest() {
         /* ********* GIVEN ********** */
        Project project = createTestProject(1);
        User user = createTestUser(1);
        userService.create(user);
        projectService.create(project, user.getId());

        Project project2 = createTestProject(2);
        projectService.create(project2, user.getId());

        User user2 = createTestUser(2);
        userService.create(user2);

        Project project3 = createTestProject(3);
        projectService.create(project3, user2.getId());
        projectService.assignUser(user.getId(), project3.getId());

           /* ********* WHEN ********** */

           /* ********* THEN ********** */

        assertEquals(user.getProjects().size(), 3);
        assertEquals(user2.getProjects().size(), 1);
        assertEquals(projectService.getAllWhichUserAdministrate(user.getId()).size(), 2);
        assertEquals(projectService.getAllWhichUserAdministrate(user2.getId()).size(), 1);
    }

    @Test
    public void getAllNotAsignedTest() {
        /* ********* GIVEN ********** */
        Project project = createTestProject(1);
        User user = createTestUser(1);
        userService.create(user);
        projectService.create(project, user.getId());

        Project project2 = createTestProject(2);
        projectService.create(project2, user.getId());

        User user2 = createTestUser(2);
        userService.create(user2);

        Project project3 = createTestProject(3);
        projectService.create(project3, user2.getId());

        /* ********* WHEN ********** */

        /* ********* THEN ********** */

        assertEquals(projectService.getAllNotAssignedUsers(project.getId()).contains(user2), true);
        assertEquals(projectService.getAllNotAssignedUsers(project.getId()).contains(user), false);
        assertEquals(projectService.getAllNotAssignedUsers(project2.getId()).contains(user2), true);
        assertEquals(projectService.getAllNotAssignedUsers(project2.getId()).contains(user), false);
        assertEquals(projectService.getAllNotAssignedUsers(project3.getId()).contains(user2), false);
        assertEquals(projectService.getAllNotAssignedUsers(project3.getId()).contains(user), true);
    }

    @Test
    public void changeOwnershipTest() {
        /* ********* GIVEN ********** */
        Project project = createTestProject(1);
        User user = createTestUser(1);
        userService.create(user);
        projectService.create(project, user.getId());

        User user2 = createTestUser(2);
        userService.create(user2);

        projectService.assignUser(user2.getId(), project.getId());

        /* ********* WHEN ********** */

        projectService.changeOwnership(project.getId(), user2.getId());

        /* ********* THEN ********** */

        assertEquals(project.getOwner().equals(user2), true);
        assertEquals(project.getUsers().contains(user2), true);
        assertEquals(project.getUsers().contains(user), true);
    }
}
