package cz.bbmri.facade;

import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.AttachmentType;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.9.13
 * Time: 11:34
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectFacade extends PermissionFacade{

    boolean approveProject(Long projectId, Long loggedUserId, ValidationErrors errors);

    boolean denyProject(Long projectId, Long loggedUserId, ValidationErrors errors);

  //  List<User> getProjectAdministrators(Long projectId);

    Project createProject(Project project,
                          Long loggedUserId,
                          String bbmriPath,
                          ValidationErrors errors);

    boolean updateProject(Project project, Long loggedUserId);

    boolean removeProject(Long projectId, String bbmriPath, ValidationErrors errors, Long loggedUserId);

    boolean isApproved(Long projectId);

    int createAttachment(FileBean fileBean,
                          AttachmentType attachmentType,
                          Long projectId,
                          String bbmriPath,
                          ValidationErrors errors,
                          Long loggedUserId);

    StreamingResolution downloadFile(Long attachmentId) throws FileNotFoundException;

    boolean deleteAttachment(Long attachmentId, ValidationErrors errors, Long loggedUserId);

    List<Attachment> getAttachments(Long projectId);

    List<Project> all();

    Project get(Long id);

    List<Project> getProjects(Long userId);

    boolean hasBiobankExecutePermission(Long userId);

    boolean markAsFinished(Long projectId, Long loggedUserId);

    ProjectAdministrator getProjectAdministrator(Long projectAdministratorId);

    List<User> getProjectAdministratorsUsers(Long projectId);

    List<Biobank> getAllBiobanks();

    boolean createSampleQuestion(SampleQuestion sampleQuestion, Long projectId, Long biobankId, ValidationErrors errors);

    List<SampleQuestion> getProjectSampleQuestions(Long projectId);
}
