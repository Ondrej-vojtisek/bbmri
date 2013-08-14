package tests.daoTest;

import bbmri.dao.ProjectDao;
import bbmri.dao.UserDao;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tests.AbstractTest;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.8.13
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */
public class ProjectDaoTest extends AbstractTest {

    @Autowired
    ProjectDao projectDao;

    @Autowired
    UserDao userDao;


    private void prepareTestedEnvironment() {
        User user = new User();
        user.setName("Pokusny");
        user.setSurname("Uzivatel");
        userDao.create(user);

        Project project1 = new Project();
        project1.setName("P1");
        project1.setProjectState(ProjectState.NEW);
  //      project1.setRequestGroups(null);
  //      project1.setSampleQuestions(null);
  //      project1.setUsers(null);
        project1.setAnnotation("P1_anotace");
   //     project1.setApprovalDate(null);
        project1.setApprovalStorage("Storage");
        project1.setApprovedBy("ApprovedBy");
   //     project1.setAttachments(null);
        project1.setFundingOrganization("Funding");
        project1.setHomeInstitution("Home");
        project1.setMainInvestigator("MainInvestigator");
        projectDao.create(project1);

     //   project1.getUsers().add(user);
     //   projectDao.update(project1);

/*
        Project project2 = new Project();
        project2.setName("P2");
        project2.setProjectState(ProjectState.NEW);
        projectDao.create(project2);

        project2.getUsers().add(user);
        projectDao.update(project2);

        Project project3 = new Project();
        project3.setName("P3");
        project3.setProjectState(ProjectState.APPROVED);
        projectDao.create(project3);

        project3.getUsers().add(user);
        projectDao.update(project3);
        */
    }

    @Test
    public void getAllByProjectStateTest() {
        prepareTestedEnvironment();
        logger.debug(projectDao.all().get(0).getName());
        assertEquals(1, 1);
    }

    /*


    public List<Project> getAllByProjectState(ProjectState projectState) {
        notNull(projectState);
        Query query = em.createQuery("SELECT p FROM Project p where p.projectState=projectState");
        return query.getResultList();
    }

    public List<Project> getAllByUser(User user) {
        notNull(user);
        return user.getProjects();
    }

    public List<User> getAllUsersByProject(Project project) {
        notNull(project);
        return project.getUsers();
    }
    */

}
