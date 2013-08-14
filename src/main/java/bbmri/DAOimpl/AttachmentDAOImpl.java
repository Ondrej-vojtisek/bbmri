package bbmri.daoImpl;

import bbmri.dao.AttachmentDao;
import bbmri.entities.Attachment;
import bbmri.entities.Project;
import bbmri.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.2.13
 * Time: 21:51
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AttachmentDaoImpl extends BasicDaoImpl<Attachment> implements AttachmentDao {

    public void create(Attachment attachment) {
        notNull(attachment);
        em.persist(attachment);
        createFolder(attachment);
    }

    public void remove(Attachment attachment) {
        notNull(attachment);
        File file = new File(getPath(attachment));
        file.delete();
        File dir = new File(Attachment.ROOT_DIR_PATH + attachment.getProject().getId().toString());
        if (dir.isDirectory()) {
            if (dir.list().length < 1) {
                dir.delete();
            }
        }
        em.remove(attachment);
    }

    private void createFolder(Attachment attachment) {
        notNull(attachment);
        File rootDir = new File(Attachment.ROOT_DIR);
        if (!rootDir.exists()) {
            rootDir.mkdir();
        }
        File dir = new File(Attachment.ROOT_DIR_PATH + attachment.getProject().getId().toString());
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public String getPath(Attachment attachment) {
        notNull(attachment);
        return (Attachment.ROOT_DIR_PATH + attachment.getProject().getId().toString() + "\\" + attachment.getId() + attachment.getAttachmentType().toString());
    }
}
