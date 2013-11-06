package bbmri.service.impl;

import bbmri.dao.ProjectAdministratorDao;
import bbmri.dao.ProjectDao;
import bbmri.dao.UserDao;
import bbmri.entities.Project;
import bbmri.entities.ProjectAdministrator;
import bbmri.entities.User;
import bbmri.service.ProjectAdministratorService;
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
@Service
public class ProjectAdministratorServiceImpl extends BasicServiceImpl implements ProjectAdministratorService {

    @Autowired
    private ProjectAdministratorDao projectAdministratorDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserDao userDao;

    public List<ProjectAdministrator> all() {
        return projectAdministratorDao.all();
    }

    public Integer count() {
        return projectAdministratorDao.count();
    }

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

    public void remove(Long id) {
        notNull(id);

        ProjectAdministrator ba = projectAdministratorDao.get(id);
        if (ba == null) {
            return;
        }
        projectAdministratorDao.remove(ba);
    }

    public ProjectAdministrator get(Long projectId, Long userId) {
        notNull(projectId);
        notNull(userId);

        Project projectDB = projectDao.get(projectId);
        User userDB = userDao.get(userId);

        return projectAdministratorDao.get(projectDB, userDB);
    }


    public boolean contains(Long projectId, Long userId) {
        notNull(projectId);
        notNull(userId);

        Project projectDB = projectDao.get(projectId);
        User userDB = userDao.get(userId);

        return !(projectDB == null || userDB == null) && projectAdministratorDao.contains(projectDB, userDB);
    }


}
