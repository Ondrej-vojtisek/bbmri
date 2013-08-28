package bbmri.serviceImpl;

import bbmri.dao.AttachmentDao;
import bbmri.dao.ProjectDao;
import bbmri.dao.RequestGroupDao;
import bbmri.dao.UserDao;
import bbmri.entities.*;
import bbmri.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private RequestGroupDao requestGroupDao;

    /*
    public Project create(Project project) {
           project.setProjectState(ProjectState.NEW);
           projectDao.create(project);
           return project;
       }
    */

    public Project create(Project project, User user) {
        project.setProjectState(ProjectState.NEW);
        projectDao.create(project);
        User userDB = userDao.get(user.getId());
        project.getUsers().add(userDB);
        projectDao.update(project);
        return project;
    }

    public void remove(Long id) {
        Project project = projectDao.get(id);
        if (project != null) {
            projectDao.remove(project);
        }
    }
    /*
    public void remove(Project project) {
             projectDao.remove(project);
     }
     */

    public Project update(Project project) {
        Project projectDB = projectDao.get(project.getId());

        if (projectDB.getProjectState() != ProjectState.NEW &&
                project.getProjectState() != ProjectState.NEW &&
                project.getProjectState() != ProjectState.APPROVED) {
            projectDB.setProjectState(project.getProjectState());
        }
        projectDB.setAnnotation(project.getAnnotation());
        projectDB.setFundingOrganization(project.getFundingOrganization());
        projectDB.setName(project.getName());

        projectDao.update(projectDB);
        return project;
    }

    public List<Project> all() {
        List<Project> projects = projectDao.all();
        return projects;
    }

    public List<Project> getAllByUser(Long id) {
            User userDB = userDao.get(id);
          //  List<Project> projects = projectDao.getAllByUser(userDB);
            return userDB.getProjects();
    }

    public List<Project> getAllByUserWithRequests(Long id) {
           User userDB = userDao.get(id);
        //   List<Project> projects = projectDao.getAllByUser(userDB);
           List<Project> projects = userDB.getProjects();
           if(projects != null){
               for(int i = 0; i < projects.size(); i++){
                  projects.get(i).setRequestGroups(requestGroupDao.getAllByProject(projects.get(i)));
               }
           }
           return projects;
       }

    public List<Project> getAllWhichUserAdministrate(Long id) {
        User userDB = userDao.get(id);
//        List<Project> projects = projectDao.getAllByUser(userDB);
        List<Project> projects = userDB.getProjects();

        List<Project> result = new ArrayList<Project>();
        if (projects != null) {
            for (Project project : projects) {
                if (project.getUsers().get(0).equals(userDB)) {
                    result.add(project);
                }
            }
        }
        return result;
    }


    public List<Project> getAllByProjectState(ProjectState projectState) {
        List<Project> projects = projectDao.getAllByProjectState(projectState);
        return projects;
    }

    public List<Project> getAllApprovedByUser(User user) {
        User userDB = userDao.get(user.getId());
//        List<Project> projects = projectDao.getAllByUser(user);
        List<Project> projects = userDB.getProjects();

        List<Project> result = new ArrayList<Project>();
        if (projects != null) {
            for (Project project : projects) {
                if (project.getProjectState() == ProjectState.APPROVED ||
                        project.getProjectState() == ProjectState.STARTED) {
                    result.add(project);
                }
            }
        }
        return result;
    }


    public User assignUser(Long userId, Long projectId) {
        User userDB = userDao.get(userId);
        Project projectDB = projectDao.get(projectId);

        if (!projectDB.getUsers().contains(userDB)) {
            projectDB.getUsers().add(userDB);
            projectDao.update(projectDB);
        }
        return userDB;
    }

    public User removeUserFromProject(Long userId, Long projectId) {
        User userDB = userDao.get(userId);
        Project projectDB = projectDao.get(projectId);
        if (projectDB.getUsers().contains(userDB) == true) {
            projectDB.getUsers().remove(userDB);
            projectDao.update(projectDB);
        }
        return userDB;
    }

    public List<User> getAllAssignedUsers(Long projectId) {
        Project projectDB = projectDao.get(projectId);
       // List<User> users = projectDao.getAllUsersByProject(projectDB);
        return projectDB.getUsers();
    }

    public void approve(Long id) {
        Project projectDB = projectDao.get(id);
        if (projectDB.getProjectState() == ProjectState.NEW) {
            projectDB.setProjectState(ProjectState.APPROVED);
            projectDao.update(projectDB);
        }

    }

    public void approve(Long projectId, Long userId) {
        Project projectDB = projectDao.get(projectId);
        User userDB = userDao.get(userId);
        if (projectDB.getProjectState() == ProjectState.NEW) {
            projectDB.setProjectState(ProjectState.APPROVED);
            projectDB.setJudgedByUser(userDB);
            projectDao.update(projectDB);
        }
    }

    public void deny(Long projectId, Long userId) {
           Project projectDB = projectDao.get(projectId);
           User userDB = userDao.get(userId);
           if (projectDB.getProjectState() == ProjectState.NEW) {
               projectDB.setProjectState(ProjectState.CANCELED);
               projectDB.setJudgedByUser(userDB);
               projectDao.update(projectDB);
           }
       }

    public Project get(Long id) {
        Project project;
        project = projectDao.get(id);
        return project;
    }

    public List<User> getAllNotAssignedUsers(Long id) {
        List<User> result = new ArrayList<User>();
        Project project;
        project = projectDao.get(id);
        List<User> allUsers = userDao.all();
        if (allUsers != null) {
            for (User user : allUsers) {
                if (!user.getProjects().contains(project)) {
                    result.add(user);
                }
            }
        }
        return result;
    }

    public Project changeOwnership(Long projectId, Long newOwnerId) {
        Project project = projectDao.get(projectId);
        User newOwner = userDao.get(newOwnerId);

        User oldOwner = userDao.get(project.getOwner().getId());

        if (project.getUsers().contains(newOwner)) {
            project.getUsers().remove(oldOwner);
            project.getUsers().remove(newOwner);
            projectDao.update(project);
            project.getUsers().add(0, newOwner);
            project.getUsers().add(oldOwner);
            projectDao.update(project);
        }
        return project;
    }

    public Integer count() {
        return projectDao.count();
    }

    public void saveAttachment(Long id, Attachment attachment) {
        Project projectDB = projectDao.get(id);
        attachment.setProject(projectDB);
        attachmentDao.create(attachment);
        projectDB.getAttachments().add(attachment);
        projectDao.update(projectDB);
    }

    public Attachment getAttachmentByProject(Long id, AttachmentType attachmentType) {
        Project projectDB = projectDao.get(id);
        List<Attachment> attachments = projectDB.getAttachments();
        if(attachments != null){
            for(int i = 0; i < attachments.size(); i++){
                if(attachments.get(i).getAttachmentType().equals(attachmentType)){
                    return  attachments.get(i);
                }
            }
        }
        return null;
    }

    public String getAttachmentPath(Attachment attachment) {
        return attachmentDao.getPath(attachment);
    }

    public List<Attachment> getAttachmentsByProject(Long id){
        Project projectDB = projectDao.get(id);
        List<Attachment> attachments = attachmentDao.all();
        List<Attachment> results = new ArrayList<Attachment>();
        for(int i = 0; i < attachments.size(); i++){
            if(attachments.get(i).getProject().equals(projectDB)){
                results.add(attachments.get(i));
            }
        }
        return results;
    }

    public Attachment getAttachmentById(Long id){
        return attachmentDao.get(id);
    }
}
