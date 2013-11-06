package bbmri.facade;

import bbmri.entities.*;
import bbmri.entities.enumeration.AttachmentType;
import bbmri.entities.enumeration.Permission;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.StreamingResolution;

import java.io.FileNotFoundException;
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
    Project createProject(Project project, Long loggedUserId);

    void updateProject(Project project);

    void removeProject(Long projectId);

    void assignAdministratorToProject(Long project, Long loggedUser, Long newAdministrator, Permission permission);

    boolean isApproved(Long projectId);

    void createAttachment(FileBean fileBean, AttachmentType attachmentType, Long projectId);

    StreamingResolution downloadFile(Long attachmentId) throws FileNotFoundException;

    void deleteAttachment(Long attachmentId);

    void updateAttachment(Attachment attachment);

    List<Attachment> getAttachments(Long projectId);

    List<Project> all();

    Project get(Long id);

    boolean hasPermission(Permission permission, Long projectId, Long userId);

    void changeProjectAdministratorPermission(Long projectAdministrator, Permission permission, Long loggedUser);

    void removeProjectAdministrator(Long projectAdministrator, Long loggedUser);

    List<Project> getProjects(Long userId);
}
