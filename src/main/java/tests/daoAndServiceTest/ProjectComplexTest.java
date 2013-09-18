package tests.daoAndServiceTest;

import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.User;
import bbmri.service.ProjectService;
import bbmri.service.RequestGroupService;
import bbmri.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 29.8.13
 * Time: 13:17
 * To change this template use File | Settings | File Templates.
 */
public class ProjectComplexTest extends AbstractDaoAndServiceTest {


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

        user = userService.eagerGet(user.getId(),false, true);
        project = projectService.eagerGet(project.getId(), true, false, false, false);

        assertEquals(1, project.getUsers().size());
        assertEquals(user, project.getOwner());
        assertEquals(true, project.getUsers().contains(user));
        assertEquals(true, user.getProjects().contains(project));
        assertEquals(1, user.getProjects().size());
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
        assertEquals(true, user.getProjects().isEmpty());

        assertEquals(user2, userService.get(user2.getId()));
        assertEquals(true, user2.getJudgedProjects().isEmpty());
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

        user2 = userService.eagerGet(user2.getId(), true, false);
        project = projectService.get(project.getId());

       assertEquals(ProjectState.APPROVED, project.getProjectState());
       assertEquals(true, user2.getJudgedProjects().contains(project));
       assertEquals(user2, project.getJudgedByUser());
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

        user2 = userService.eagerGet(user2.getId(), true, false);
        project = projectService.get(project.getId());

        assertEquals(ProjectState.DENIED, project.getProjectState());
        assertEquals(true, user2.getJudgedProjects().contains(project));
        assertEquals(user2, project.getJudgedByUser());
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

        user = userService.eagerGet(user.getId(), false, true);
        user2 = userService.eagerGet(user2.getId(), false, true);

        assertEquals(user.getProjects().size(), 3);
        assertEquals(user2.getProjects().size(), 1);
        assertEquals(true, user.getProjects().contains(project));
        assertEquals(true, user.getProjects().contains(project2));
        assertEquals(true, user.getProjects().contains(project3));
        assertEquals(true, user2.getProjects().contains(project3));
        assertEquals(false, user2.getProjects().contains(project));
        assertEquals(false, user2.getProjects().contains(project2));
    }

    @Test
    public void removeUserFromProjectTest() {
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

        user = userService.eagerGet(user.getId(), false, true);
        user2 = userService.eagerGet(user2.getId(), false, true);

        assertEquals(3, user.getProjects().size());
        assertEquals(true, user2.getProjects().isEmpty());
        assertEquals(true, user.getProjects().contains(project));
        assertEquals(true, user.getProjects().contains(project2));
        assertEquals(true, user.getProjects().contains(project3));
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

        user = userService.eagerGet(user.getId(), false, true);
        user2 = userService.eagerGet(user2.getId(), false, true);

        assertEquals(3, user.getProjects().size());
        assertEquals(1, user2.getProjects().size());
        assertEquals(2, projectService.getAllWhichUserAdministrate(user.getId()).size());
        assertEquals(1, projectService.getAllWhichUserAdministrate(user2.getId()).size());
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

        assertEquals(true, projectService.getAllNotAssignedUsers(project.getId()).contains(user2));
        assertEquals(false, projectService.getAllNotAssignedUsers(project.getId()).contains(user));
        assertEquals(true, projectService.getAllNotAssignedUsers(project2.getId()).contains(user2));
        assertEquals(false, projectService.getAllNotAssignedUsers(project2.getId()).contains(user));
        assertEquals(false, projectService.getAllNotAssignedUsers(project3.getId()).contains(user2));
        assertEquals(true, projectService.getAllNotAssignedUsers(project3.getId()).contains(user));
    }

    /*TODO - this must be refactored after update of class structure*/
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
