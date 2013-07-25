package bbmri.service;

import bbmri.entities.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectService {

    Project create(Project project, User user);

     void remove(Long id);

     Project update(Project project);

     List<Project> getAll();

     List<Project> getAllByUser(Long id);

     User assignUser(Long userId, Long projectId);

     User removeUserFromProject(Long userId, Long projectId);

     List<User> getAllAssignedUsers(Long projectId);

     void approve(Long id);

     void approve(Long projectId, Long userId);

     void deny(Long projectId, Long userId);

     List<Project> getAllByProjectState(ProjectState projectState);

     List<Project> getAllApprovedByUser(User user);

     List<Project> getAllWhichUserAdministrate(Long id);

     Project getById(Long id);

     List<User> getAllNotAssignedUsers(Long id);

     Project changeOwnership(Long projectId, Long newOwnerId);

     Integer getCount();

     void saveAttachment(Long id, Attachment attachment);

     Attachment getAttachmentByProject(Long id, AttachmentType attachmentType);

    String getAttachmentPath(Attachment attachment);

    List<Attachment> getAttachmentsByProject(Long id);

    Attachment getAttachmentById(Long id);

    List<Project> getAllByUserWithRequests(Long id);
}
