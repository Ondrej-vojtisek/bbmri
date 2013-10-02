package bbmri.serviceImpl;

import bbmri.dao.AttachmentDao;
import bbmri.dao.ProjectDao;
import bbmri.entities.Attachment;
import bbmri.entities.Project;
import bbmri.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 29.8.13
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service
public class AttachmentServiceImpl extends BasicServiceImpl implements AttachmentService {

    @Autowired
       private ProjectDao projectDao;

       @Autowired
       private AttachmentDao attachmentDao;

    public String getAttachmentPath(Attachment attachment) {
            notNull(attachment);
            return (Attachment.ROOT_DIR_PATH
                    + attachment.getProject().getId().toString()
                    + File.separator
                    + attachment.getProject().getId().toString()
                    + attachment.getAttachmentType().toString());
    }

    public Attachment get(Long id) {
        notNull(id);
        return attachmentDao.get(id);
    }

    public void create(Long projectId, Attachment attachment) {
           notNull(projectId);
           notNull(attachment);

           Project projectDB = projectDao.get(projectId);
           if(projectDB == null){
               return;
               // TODO: exception
           }

           createFolder(attachment);
           attachment.setProject(projectDB);
           attachmentDao.create(attachment);
           projectDB.getAttachments().add(attachment);
           projectDao.update(projectDB);
       }

    public List<Attachment> all(){
        return attachmentDao.all();
    }

    public void remove(Long id){
        notNull(id);
        Attachment attachment = attachmentDao.get(id);

        File file = new File(getAttachmentPath(attachment));

        if(attachment == null){
            return;
            // TODO: exception
        }
               // Exists?
               if (!file.exists()) {
                   throw new IllegalArgumentException(
                           "Delete: no such attachment: " + getAttachmentPath(attachment));
               }
               boolean success = file.delete();

               // Truly deleted?
               if (!success) {
                   throw new IllegalArgumentException("Delete: deletion failed: " + file);
               }

               File dir = new File(Attachment.ROOT_DIR_PATH + attachment.getProject().getId().toString());

               // Correct path to parent directory?
               if (!dir.exists()) {
                   throw new IllegalArgumentException(
                           "Delete: no such folder: " + dir);
               }
               // It is truly a directory?
               if (!dir.isDirectory()) {
                   throw new IllegalArgumentException(
                           "Delete: not a directory: " + dir);
               }

               // If empty - delete it!
               if (dir.list().length < 1) {
                   success = dir.delete();
                   if (!success) {
                       throw new IllegalArgumentException("Delete: deletion failed: " + dir);
                   }
               }
        attachmentDao.remove(attachment);

    }

    public Attachment update(Attachment attachment){
        // TODO
        return null;
    }

    public Integer count(){
        return attachmentDao.count();
    }


    public List<Attachment> getAttachmentsByProject(Long projectId) {
        Project projectDB = projectDao.get(projectId);
        List<Attachment> attachments = attachmentDao.all();
        List<Attachment> results = new ArrayList<Attachment>();
        for (int i = 0; i < attachments.size(); i++) {
            if (attachments.get(i).getProject().equals(projectDB)) {
                results.add(attachments.get(i));
            }
        }
        return results;
    }

    private void createFolder(Attachment attachment) {
            notNull(attachment);
            File rootDir = new File(Attachment.ROOT_DIR);
            if (!rootDir.exists()) {
                boolean success = rootDir.mkdir();
                if (!success) {
                    throw new IllegalArgumentException("Create folder: failed: " + rootDir);
                }
            }
            File dir = new File(Attachment.ROOT_DIR_PATH
                    + attachment.getProject().getId().toString());
            if (!dir.exists()) {
                boolean success =  dir.mkdir();
                if (!success) {
                    throw new IllegalArgumentException("Create folder: failed: " + dir);
                }
            }
        }

}
