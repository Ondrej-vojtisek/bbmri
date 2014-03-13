package cz.bbmri.dao.impl;

import cz.bbmri.dao.AttachmentDao;
import cz.bbmri.entities.Attachment;
import cz.bbmri.entities.Project;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.2.13
 * Time: 21:51
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AttachmentDaoImpl extends BasicDaoImpl<Attachment> implements AttachmentDao {

    @SuppressWarnings("unchecked")
    public List<Attachment> getAttachmentsByProject(Project project) {
        notNull(project);
        Query query = em.createQuery("SELECT p FROM Attachment p where p.project = :projectParam");
        query.setParameter("projectParam", project);
        return query.getResultList();
    }

    public Attachment getAttachmentByPath(String path) {
        notNull(path);
        Query query = em.createQuery("SELECT p FROM Attachment p where p.absolutePath = :pathParam");
        query.setParameter("pathParam", path);

        try {
            return (Attachment) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }

    }

    public List<Attachment> getAttachmentSorted(Project project, String orderByParam, boolean desc) {
        Query query = null;
        String orderParam = "";
        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY attachment." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        query = em.createQuery("SELECT attachment FROM Attachment attachment WHERE " +
                "attachment.project = :project " +
                orderParam);
        query.setParameter("project", project);
        return query.getResultList();
    }


}
