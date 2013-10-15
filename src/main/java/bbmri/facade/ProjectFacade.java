package bbmri.facade;

import bbmri.entities.*;
import bbmri.entities.enumeration.AttachmentType;
import bbmri.entities.enumeration.Permission;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.9.13
 * Time: 11:34
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectFacade {

    void approveProject(Long projectId, Long loggedUserId);

    void denyProject(Long projectId, Long loggedUserId);

    List<User> getProjectAdministrators(Long projectId);

    //List<ProjectAdministrator> getProjectAdministrators(Long biobankId);
    void createProject(Project project, Long loggedUserId);

    void updateProject(Project project);

    void removeProject(Long projectId, Long loggedUserId);

    void assignAdministratorToProject(Long project, Long loggedUser, Long newAdministrator, Permission permission);

    void removeAdministratorFromProject(Long project, Long loggedUser, Long newAdministrator);

    void changePermissionOfAdministrator(Long biobank, Long loggedUser, Long newAdministrator, Permission permission);

    boolean isApproved(Long projectId);

    void createAttachment(String fileName, String contentType, Long size, AttachmentType attachmentType, Long projectId);

    void deleteAttachment(Attachment attachment);

    void updateAttachment(Attachment attachment);

    void uploadAttachment(Attachment attachment);

    List<Attachment> getAttachments(Long projectId);

    String getAttachmentPath(Long attachmentId);
}
