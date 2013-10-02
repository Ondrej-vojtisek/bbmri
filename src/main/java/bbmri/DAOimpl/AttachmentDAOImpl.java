package bbmri.daoImpl;

import bbmri.dao.AttachmentDao;
import bbmri.entities.Attachment;
import org.springframework.stereotype.Repository;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.2.13
 * Time: 21:51
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AttachmentDaoImpl extends BasicDaoImpl<Attachment> implements AttachmentDao {
 /*
    public void create(Attachment attachment) {
        notNull(attachment);
        em.persist(attachment);
        createFolder(attachment);
    }
    */
    /*
    public void remove(Attachment attachment) {
        notNull(attachment);
        File file = new File(getPath(attachment));

        // Exists?
        if (!file.exists()) {
            throw new IllegalArgumentException(
                    "Delete: no such attachment: " + getPath(attachment));
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
        em.remove(attachment);
    }
    */

   /*
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
    */
}
