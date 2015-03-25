package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.ArchiveDAO;
import cz.bbmri.dao.AttachmentDAO;
import cz.bbmri.entity.Archive;
import cz.bbmri.entity.Attachment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("attachmentDAO")
@Transactional(readOnly = true)
public class AttachmentDAOImpl extends GenericDAOImpl<Attachment> implements AttachmentDAO {

    @Transactional(readOnly = true)
    public Attachment get(Long id) {
                      return (Attachment) getCurrentSession().get(Attachment.class, id);
                  }


}
