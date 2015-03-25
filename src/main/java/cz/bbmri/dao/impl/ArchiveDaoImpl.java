package cz.bbmri.dao.impl;

import cz.bbmri.dao.ArchiveDAO;
import cz.bbmri.entity.Archive;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("archiveDAO")
@Transactional
public class ArchiveDAOImpl extends GenericDAOImpl<Archive> implements ArchiveDAO {

    public Archive get(Long id) {
                   return (Archive) getCurrentSession().get(Archive.class, id);
               }

}
