package cz.bbmri.service.impl;

import cz.bbmri.dao.ProjectAdministratorDao;
import cz.bbmri.dao.ProjectDao;
import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.service.ProjectAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.11.13
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("projectAdministratorService")
public class ProjectAdministratorServiceImpl extends BasicServiceImpl implements ProjectAdministratorService {

    @Autowired
    private ProjectAdministratorDao projectAdministratorDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    public List<ProjectAdministrator> all() {
        return projectAdministratorDao.all();
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return projectAdministratorDao.count();
    }

    @Transactional(readOnly = true)
    public ProjectAdministrator get(Long id) {
        notNull(id);
        return projectAdministratorDao.get(id);
    }

    public ProjectAdministrator update(ProjectAdministrator projectAdministrator) {
        notNull(projectAdministrator);

        ProjectAdministrator ba = projectAdministratorDao.get(projectAdministrator.getId());
        if (ba == null) {
            return null;
        }

        // Only permission can be updated

        if (projectAdministrator.getPermission() != null) ba.setPermission(projectAdministrator.getPermission());

        projectAdministratorDao.update(ba);

        return ba;
    }

    public boolean remove(Long id) {
        notNull(id);

        ProjectAdministrator ba = projectAdministratorDao.get(id);
        if (ba == null) {
            return false;
        }
        projectAdministratorDao.remove(ba);
        return true;
    }

    @Transactional(readOnly = true)
    public ProjectAdministrator get(Long projectId, Long userId) {
        notNull(projectId);
        notNull(userId);

        Project projectDB = projectDao.get(projectId);
        User userDB = userDao.get(userId);

        return projectAdministratorDao.get(projectDB, userDB);
    }

    @Transactional(readOnly = true)
    public boolean contains(Long projectId, Long userId) {
        notNull(projectId);
        notNull(userId);

        Project projectDB = projectDao.get(projectId);
        User userDB = userDao.get(userId);

        return !(projectDB == null || userDB == null) && projectAdministratorDao.contains(projectDB, userDB);
    }

    @Transactional(readOnly = true)
    public List<ProjectAdministrator> allOrderedBy(String orderByParam, boolean desc){
        return projectAdministratorDao.allOrderedBy(orderByParam, desc);
    }

}
