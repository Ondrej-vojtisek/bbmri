package bbmri.DAOimpl;

import bbmri.DAO.AttachmentDAO;
import bbmri.entities.Attachment;
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
public class AttachmentDAOImpl implements AttachmentDAO {

    @PersistenceContext
    private EntityManager em;

    public void create(Attachment attachment) {
        DAOUtils.notNull(attachment);
        em.persist(attachment);
        createFolder(attachment);
    }

    public void remove(Attachment attachment) {
        DAOUtils.notNull(attachment);
        File file = new File(getPath(attachment));
        file.delete();
        File dir = new File("bbmri_data\\" + attachment.getProject().getId().toString());
        if (dir.isDirectory()) {
            if (dir.list().length < 1) {
                dir.delete();
            }
        }
        em.remove(attachment);
    }

    public void update(Attachment attachment) {
        DAOUtils.notNull(attachment);
        em.merge(attachment);
    }

    public List<Attachment> getAll() {
        Query query = em.createQuery("SELECT p FROM Attachment p");
        return query.getResultList();
    }

    public Attachment get(Long id) {
        DAOUtils.notNull(id);
        return em.find(Attachment.class, id);
    }

    public Integer getCount() {
        Query query = em.createQuery("SELECT COUNT (p) FROM Attachment p");
        return Integer.parseInt(query.getSingleResult().toString());
    }


    void createFolder(Attachment attachment) {
        DAOUtils.notNull(attachment);
        File rootDir = new File("bbmri_data");
        if (!rootDir.exists()) {
            rootDir.mkdir();
        }
        File dir = new File("bbmri_data\\" + attachment.getProject().getId().toString());
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public File load(Attachment attachment) {

        return null;
    }

    public void delete(Attachment attachment) {

    }

    public String getPath(Attachment attachment) {
        DAOUtils.notNull(attachment);
        return ("bbmri_data\\" + attachment.getProject().getId().toString() + "\\" + attachment.getId());
    }
}
