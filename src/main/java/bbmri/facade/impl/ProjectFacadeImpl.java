package bbmri.facade.impl;

import bbmri.entities.Attachment;
import bbmri.entities.Project;
import bbmri.entities.ProjectAdministrator;
import bbmri.entities.User;
import bbmri.entities.enumeration.AttachmentType;
import bbmri.entities.enumeration.Permission;
import bbmri.entities.enumeration.SystemRole;
import bbmri.facade.ProjectFacade;
import bbmri.service.AttachmentService;
import bbmri.service.ProjectAdministratorService;
import bbmri.service.ProjectService;
import bbmri.service.UserService;
import com.mysql.jdbc.NotImplemented;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.StreamingResolution;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.11.13
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ProjectFacadeImpl extends BasicFacade implements ProjectFacade {


    @Autowired
    private UserService userService;

    @Autowired
    private ProjectAdministratorService projectAdministratorService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AttachmentService attachmentService;

    public void approveProject(Long projectId, Long loggedUserId) {
        throw new NotImplementedException("TODO");
    }

    public void denyProject(Long projectId, Long loggedUserId) {
        throw new NotImplementedException("TODO");
    }

    public List<User> getProjectAdministrators(Long projectId) {
        throw new NotImplementedException("TODO");
    }

    //List<ProjectAdministrator> getProjectAdministrators(Long biobankId);
    public Project createProject(Project project, Long loggedUserId) {
        notNull(project);
        notNull(loggedUserId);

        return projectService.create(project, loggedUserId);
    }

    public void updateProject(Project project) {
        notNull(project);
        projectService.update(project);
    }

    public void removeProject(Long projectId) {
        notNull(projectId);
        projectService.remove(projectId);
    }

    public void assignAdministratorToProject(Long project, Long loggedUser, Long newAdministrator, Permission permission) {
        notNull(project);
        notNull(loggedUser);
        notNull(newAdministrator);
        notNull(permission);

        Project projectDB = projectService.get(project);
        User logged = userService.get(loggedUser);
        User newAdmin = userService.get(newAdministrator);

        if (projectDB == null || logged == null || newAdmin == null) {
            return;
            // TODO: Exception
        }

        if (logged.equals(newAdmin)) {
            return;
            // TODO: exception - can assign yourself again
        }

        if (projectAdministratorService.get(project, newAdministrator) != null) {
            //TODO: exception - he is already admin
            return;
        }

        projectService.assignAdministrator(newAdministrator, project, permission);

    }

    public boolean isApproved(Long projectId) {
        throw new NotImplementedException("TODO");
    }

    public void createAttachment(FileBean fileBean, AttachmentType attachmentType, Long projectId) {
        notNull(fileBean);
        notNull(attachmentType);
        notNull(projectId);

        Project projectDB = projectService.get(projectId);

        if (projectDB == null) {
            return;
            // TODO: exception
        }

        Attachment attachment = new Attachment();
        attachment.setFileName(fileBean.getFileName());
        attachment.setContentType(fileBean.getContentType());
        attachment.setSize(fileBean.getSize());
        attachment.setAttachmentType(attachmentType);

        attachment = attachmentService.create(projectId, attachment);

        File file = new File(attachmentService.getAttachmentPath(attachment));

        try {
            fileBean.save(file);
        } catch (IOException e) {
            attachmentService.remove(attachment.getId());
            // TODO: throw exception

        }
    }

    public StreamingResolution downloadFile(Long attachmentId) throws FileNotFoundException {
        notNull(attachmentId);
        Attachment attachment = attachmentService.get(attachmentId);
        if (attachment == null) {
            return null;
            // TODO: exception
        }
        FileInputStream fis = new FileInputStream(attachmentService.getAttachmentPath(attachment));
        return new StreamingResolution(attachment.getContentType(), fis).setFilename(attachment.getFileName());
    }


    //    @DontValidate
    //    public Resolution download() throws Exception {
    //        //   System.err.println("Attachment ID : " + attachment.getId());
    //        attachment = attachmentService.get(attachment.getId());
    //        String fileName = attachment.getFileName();
    //        String filePath = attachmentService.getAttachmentPath(attachment);
    //        return new StreamingResolution(attachment.getContentType(),
    //                new FileInputStream(filePath)).setFilename(fileName);
    //    }

    public void deleteAttachment(Long attachmentId) {
        notNull(attachmentId);
        attachmentService.remove(attachmentId);
    }

    public void updateAttachment(Attachment attachment) {
        throw new NotImplementedException("TODO");
    }

    public List<Attachment> getAttachments(Long projectId) {
        notNull(projectId);
        Project projectDB = projectService.eagerGet(projectId, false, false, true, false);
        return projectDB.getAttachments();
    }

    public List<Project> all() {
        return projectService.all();
    }

    public Project get(Long id) {
        notNull(id);
        return projectService.get(id);
    }

    public boolean hasPermission(Permission permission, Long projectId, Long userId) {
        notNull(permission);
        notNull(projectId);
        notNull(userId);

        ProjectAdministrator pa = projectAdministratorService.get(projectId, userId);

        if (pa == null) {
            return false;
        }

        return pa.getPermission().include(permission);
    }

    public void changeProjectAdministratorPermission(Long projectAdministrator,
                                                     Permission permission,
                                                     Long loggedUser) {
        notNull(projectAdministrator);
        notNull(permission);
        notNull(loggedUser);

        ProjectAdministrator pa = projectAdministratorService.get(projectAdministrator);
        if (pa == null) {
            return;
            // TODO: exception
        }

        User userDB = userService.get(loggedUser);
        if (userDB == null) {
            return;
            //TODO: exception
        }
        // TODO: Question: will there be an permission check? Can I remove my own permissions?
        // TODO: There must solved situation of last administrator remove

        pa.setPermission(permission);
        projectAdministratorService.update(pa);
    }

    public void removeProjectAdministrator(Long projectAdministrator, Long loggedUser) {
        notNull(projectAdministrator);
        notNull(loggedUser);

        ProjectAdministrator pa = projectAdministratorService.get(projectAdministrator);
        if (pa == null) {
            return;
            // TODO: exception
        }

        User userDB = userService.get(loggedUser);
        if (userDB == null) {
            return;
            //TODO: exception
        }

        // TODO: There must solved situation of last administrator remove

        projectAdministratorService.remove(pa.getId());
        userDB = userService.eagerGet(userDB.getId(), false, false, true);
        if (userDB.getProjectAdministrators().size() < 1) {
            // If userDB doesn't manage other Biobank than the deleted one -> remove its system role
            userService.removeSystemRole(userDB.getId(), SystemRole.PROJECT_TEAM_MEMBER);
        }
    }

    public List<Project> getProjects(Long userId){
        notNull(userId);
        User userDB = userService.eagerGet(userId, false, true, false);
        List<ProjectAdministrator> paList = userDB.getProjectAdministrators();
        List<Project> projects = new ArrayList<Project>();

        for(ProjectAdministrator pa : paList){
            projects.add(pa.getProject());
        }

        return projects;
    }
}
