package cz.bbmri.dao.impl;

import cz.bbmri.dao.AttachmentDao;
import cz.bbmri.entities.Attachment;
import cz.bbmri.entities.Project;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation for interface handling instances of Attachment. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository
public class AttachmentDaoImpl extends BasicDaoImpl<Attachment> implements AttachmentDao {

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
