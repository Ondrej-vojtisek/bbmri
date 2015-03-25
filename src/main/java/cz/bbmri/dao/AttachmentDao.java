package cz.bbmri.dao;

import cz.bbmri.entity.Attachment;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface AttachmentDAO extends AbstractDAO<Attachment, Long> {

//    Attachment getAttachmentByPath(String path);

    void remove(Attachment attachment);

}
