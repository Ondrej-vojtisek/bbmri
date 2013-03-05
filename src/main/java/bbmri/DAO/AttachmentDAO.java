package bbmri.DAO;

import bbmri.entities.Attachment;
import bbmri.entities.Project;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.2.13
 * Time: 21:51
 * To change this template use File | Settings | File Templates.
 */
public interface AttachmentDAO {

    void create(Attachment attachment);

    void remove(Attachment attachment);

    void update(Attachment attachment);

    List<Attachment> getAll();

    Attachment get(Long id);

    Integer getCount();

    String getPath(Attachment attachment);

}
