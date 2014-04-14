package cz.bbmri.dao.impl;

import cz.bbmri.dao.AttachmentDao;
import cz.bbmri.entities.Attachment;
import cz.bbmri.entities.Project;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.2.13
 * Time: 21:51
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AttachmentDaoImpl extends BasicDaoImpl<Attachment, Long> implements AttachmentDao {

    public List<Attachment> getAttachmentsByProject(Project project) {
        notNull(project);
        // Typed query instead of query to avoid unchecked assignment
        typedQuery = em.createQuery("SELECT p FROM Attachment p where p.project = :projectParam",
                Attachment.class);
        typedQuery.setParameter("projectParam", project);

        return typedQuery.getResultList();
    }

    public Attachment getAttachmentByPath(String path) {
        notNull(path);
        typedQuery = em.createQuery("SELECT p FROM Attachment p where p.absolutePath = :pathParam",
                Attachment.class);
        typedQuery.setParameter("pathParam", path);

        return getSingleResult();

    }

    public List<Attachment> getAttachmentSorted(Project project, String orderByParam, boolean desc) {
        notNull(project);
        String orderParam = "";

        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY attachment." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        typedQuery = em.createQuery("SELECT attachment FROM Attachment attachment WHERE " +
                "attachment.project = :projectParam " +
                orderParam, Attachment.class);
        typedQuery.setParameter("projectParam", project);

        return typedQuery.getResultList();
    }


}
