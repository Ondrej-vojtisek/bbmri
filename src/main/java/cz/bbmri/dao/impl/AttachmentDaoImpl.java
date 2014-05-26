package cz.bbmri.dao.impl;

import cz.bbmri.dao.AttachmentDao;
import cz.bbmri.entities.*;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
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

    public List<ProjectAttachment> getAttachmentSorted(Project project, String orderByParam, boolean desc) {
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
        TypedQuery<ProjectAttachment> typedQuery1 = em.createQuery("SELECT attachment FROM ProjectAttachment attachment WHERE " +
                "attachment.project = :projectParam " +
                orderParam, ProjectAttachment.class);
        typedQuery1.setParameter("projectParam", project);

        return typedQuery1.getResultList();
    }

    public List<BiobankAttachment> getAttachmentSorted(Biobank biobank, String orderByParam, boolean desc) {
        notNull(biobank);

        String orderParam = "";

        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY attachment." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        TypedQuery<BiobankAttachment> typedQuery1 = em.createQuery("SELECT attachment FROM BiobankAttachment attachment WHERE " +
                "attachment.biobank = :biobankParam " +
                orderParam, BiobankAttachment.class);
        typedQuery1.setParameter("biobankParam", biobank);

        return typedQuery1.getResultList();
    }


}
