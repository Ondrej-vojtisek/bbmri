package cz.bbmri.service.impl;

import cz.bbmri.dao.ArchiveDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.systemAdministration.Archive;
import cz.bbmri.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Transactional
@Service("archiveService")
public class ArchiveServiceImpl extends BasicServiceImpl implements ArchiveService {

    @Autowired
    private ArchiveDao archiveDao;

    @Transactional(readOnly = true)
    public List<Archive> allOrderedBy(String orderByParam, boolean desc) {
        return archiveDao.allOrderedBy(orderByParam, desc);
    }
}
