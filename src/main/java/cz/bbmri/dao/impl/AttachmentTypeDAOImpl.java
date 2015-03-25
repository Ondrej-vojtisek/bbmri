package cz.bbmri.dao.impl;

import cz.bbmri.dao.AttachmentTypeDAO;
import cz.bbmri.entity.AttachmentType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("attachmentTypeDAO")
public class AttachmentTypeDAOImpl extends GenericDAOImpl<AttachmentType> implements AttachmentTypeDAO {

    @Transactional(readOnly = true)
    public AttachmentType get(Integer id) {
                      return (AttachmentType) getCurrentSession().get(AttachmentType.class, id);
                  }
}
