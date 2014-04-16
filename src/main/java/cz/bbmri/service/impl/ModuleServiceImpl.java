package cz.bbmri.service.impl;

import cz.bbmri.dao.ModuleDao;
import cz.bbmri.entities.Module;
import cz.bbmri.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 19.2.14
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("moduleService")
public class ModuleServiceImpl extends BasicServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    public Module get(Long moduleId) {
        if(isNull(moduleId, "moduleId", null)) return null;

        Module moduleDB = moduleDao.get(moduleId);
        if(isNull(moduleDB, "moduleDB", null)) return null;

        return moduleDB;
    }
}
