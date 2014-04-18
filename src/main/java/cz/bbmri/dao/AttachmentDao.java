package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Attachment;
import cz.bbmri.entities.Project;

import java.util.List;

/**
 * Interface to handle instances of Attachment stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface AttachmentDao extends BasicDao<Attachment> {

    /**
     * Find record about database by its path in file system of application server.
     *
     * @param path - path where file is located on application server
     * @return record of file stored on given location in file system
     */
    Attachment getAttachmentByPath(String path);

    /**
     * Return all instances of Attachment associated with project ordered by given parameter. Desc param changes if it is ordered DESC or ASC
     *
     * @param project      - all attachments associated with this project
     * @param orderByParam - select column by which will the result be sorted
     * @param desc         - flag determining if order will be DESC (true) or default ASC (false)
     * @return sorted list of all attachment associated with specified project
     */
    List<Attachment> getAttachmentSorted(Project project, String orderByParam, boolean desc);
}
