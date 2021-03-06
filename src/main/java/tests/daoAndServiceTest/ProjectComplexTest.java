package tests.daoAndServiceTest;

import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.enumeration.ProjectState;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.service.ProjectAdministratorService;
import cz.bbmri.service.ProjectService;
import cz.bbmri.service.UserService;
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
    private ProjectAdministratorService projectAdministratorService;

        /* ********* GIVEN ********** */
        /* ********* WHEN ********** */
        /* ********* THEN ********** */

    @Test
    public void createProjectTest() {
//        /* ********* GIVEN ********** */
//        Project project = createTestProject(1);
//        User user = createTestUser(1);
//        userService.create(user);
//
//        /* ********* WHEN ********** */
//
//        projectService.create(project, user.getId());
//
//        /* ********* THEN ********** */
//
//        user = userService.eagerGet(user.getId(),false, true, false, false, false);
//        project = projectService.eagerGet(project.getId(), true, false, false);
//
//        assertEquals(1, project.getProjectAdministrators().size());
//        assertEquals(1, user.getProjectAdministrators().size());
//       // ProjectAdministrator pa = project.getProjectAdministrators().get(0);
//        ProjectAdministrator pa = projectAdministratorService.get(project.getId(), user.getId());
//        assertEquals(Permission.MANAGER, pa.getPermission());
//        assertEquals(user, pa.getUser());
//        assertEquals(project, pa.getProject());
//       // assertEquals(true, user.getSystemRoles().contains(SystemRole.PROJECT_TEAM_MEMBER));

    }

    @Test
    public void removeProjectTest() {
        /* ********* GIVEN ********** */
//        Project project = createTestProject(1);
//        User user = createTestUser(1);
//        userService.create(user);
//        projectService.create(project, user.getId());
//
//        User user2 = createTestUser(2);
//        userService.create(user2);
//
//        projectService.approve(project.getId(), user2.getId());
//
//        /* ********* WHEN ********** */
//
//        projectService.remove(project.getId());
//
//        /* ********* THEN ********** */
//
//        user = userService.eagerGet(user.getId(), false, true, false, false, false);
//
//        assertEquals(null, projectService.get(project.getId()));
//        assertEquals(true, user.getProjectAdministrators().isEmpty());
//
//        assertEquals(user2, userService.get(user2.getId()));
//        assertEquals(true, user2.getJudgedProjects().isEmpty());
//        assertEquals(false, user.getSystemRoles().contains(SystemRole.PROJECT_TEAM_MEMBER));

    }

    @Test
    public void approveProjectTest() {
        /* ********* GIVEN ********** */
//        Project project = createTestProject(1);
//        User user = createTestUser(1);
//        userService.create(user);
//        projectService.create(project, user.getId());
//
//        User user2 = createTestUser(2);
//        userService.create(user2);
//
//        /* ********* WHEN ********** */
//
//        projectService.approve(project.getId(), user2.getId());
//
//        /* ********* THEN ********** */
//
//        user2 = userService.eagerGet(user2.getId(), true, false, false, false, false);
//        project = projectService.get(project.getId());
//
//       assertEquals(ProjectState.APPROVED, project.getProjectState());
//       assertEquals(true, user2.getJudgedProjects().contains(project));
//       assertEquals(user2, project.getJudgedByUser());
    }

    @Test
    public void denyProjectTest() {
           /* ********* GIVEN ********** */
//        Project project = createTestProject(1);
//        User user = createTestUser(1);
//        userService.create(user);
//        projectService.create(project, user.getId());
//
//        User user2 = createTestUser(2);
//        userService.create(user2);
//
//           /* ********* WHEN ********** */
//
//        projectService.deny(project.getId(), user2.getId());
//
//           /* ********* THEN ********** */
//
//        user2 = userService.eagerGet(user2.getId(), true, false, false, false, false);
//        project = projectService.get(project.getId());
//
//        assertEquals(ProjectState.DENIED, project.getProjectState());
//        assertEquals(true, user2.getJudgedProjects().contains(project));
//        assertEquals(user2, project.getJudgedByUser());
    }

    @Test
    public void assignUserTest() {
        /* ********* GIVEN ********** */
//        Project project = createTestProject(1);
//        User user = createTestUser(1);
//        userService.create(user);
//        projectService.create(project, user.getId());
//
//        Project project2 = createTestProject(2);
//        projectService.create(project2, user.getId());
//
//        User user2 = createTestUser(2);
//        userService.create(user2);
//
//        Project project3 = createTestProject(3);
//        projectService.create(project3, user2.getId());
//
//        /* ********* WHEN ********** */
//
//       // projectService.changeAdministratorPermission(project3.getId(), user.getId(), Permission.MANAGER);
//       // projectService.assignUser(user.getId(), project3.getId());
//
//        /* ********* THEN ********** */
//
//        user = userService.eagerGet(user.getId(), false, true, false, false, false);
//        user2 = userService.eagerGet(user2.getId(), false, true, false, false, false);
//
//        assertEquals(user.getProjectAdministrators().size(), 3);
//        assertEquals(user2.getProjectAdministrators().size(), 1);

    }

    //@Test
    public void removeUserFromProjectTest() {
            /* ********* GIVEN ********** */
//        Project project = createTestProject(1);
//        User user = createTestUser(1);
//        userService.create(user);
//        projectService.create(project, user.getId());
//
//        Project project2 = createTestProject(2);
//        projectService.create(project2, user.getId());
//
//        User user2 = createTestUser(2);
//        userService.create(user2);
//
//        Project project3 = createTestProject(3);
//        projectService.create(project3, user2.getId());
//
//      //  projectService.assignUserToProject(user, project3, Permission.MANAGER);
//      //  projectService.assignUser(user.getId(), project3.getId());
//
//            /* ********* WHEN ********** */
//
//    //    projectService.removeUserFromProject(user2, project3);
//    //    projectService.removeUserFromProject(user2.getId(), project3.getId());
//
//            /* ********* THEN ********** */
//
//        user = userService.eagerGet(user.getId(), false, true, false, false, false);
//        user2 = userService.eagerGet(user2.getId(), false, true, false, false, false);
//
//         assertEquals(3, user.getProjectAdministrators().size());
//        assertEquals(true, user2.getProjectAdministrators().isEmpty());

    }

}
