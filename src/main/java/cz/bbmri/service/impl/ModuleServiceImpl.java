package cz.bbmri.service.impl;

import cz.bbmri.dao.ModuleDao;
import cz.bbmri.entities.Module;
import cz.bbmri.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Transactional
@Service("moduleService")
public class ModuleServiceImpl extends BasicServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    @Transactional(readOnly = true)
    public Module get(Long moduleId) {
        if(isNull(moduleId, "moduleId", null)) return null;

        Module moduleDB = moduleDao.get(moduleId);
        if(isNull(moduleDB, "moduleDB", null)) return null;

        return moduleDB;
    }
}
