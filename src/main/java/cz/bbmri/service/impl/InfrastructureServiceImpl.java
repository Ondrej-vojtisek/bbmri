package cz.bbmri.service.impl;

import cz.bbmri.dao.InfrastructureDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Infrastructure;
import cz.bbmri.service.InfrastructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Transactional
@Service("infrastructureService")
public class InfrastructureServiceImpl extends BasicServiceImpl implements InfrastructureService {

    @Autowired
    private InfrastructureDao infrastructureDao;

    public void initialize(Biobank biobank) {
        if (isNull(biobank, "biobank", null)) return;

        if (biobank.getInfrastructure() != null) {
            logger.debug("Biobank already has infrastructure");
            return;
        }

        Infrastructure infrastructure = new Infrastructure();
        infrastructure.setBiobank(biobank);
        infrastructureDao.create(infrastructure);
    }

    @Transactional(readOnly = true)
    public Infrastructure get(Long id) {
        if (isNull(id, "id", null)) return null;
        return infrastructureDao.get(id);
    }


}
