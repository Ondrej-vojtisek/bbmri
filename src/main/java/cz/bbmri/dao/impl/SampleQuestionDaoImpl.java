package cz.bbmri.dao.impl;

import cz.bbmri.dao.SampleQuestionDao;
import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.entities.sample.Sample;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation for interface handling instances of SampleQuestion. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository
public class SampleQuestionDaoImpl extends BasicDaoImpl<SampleQuestion> implements SampleQuestionDao {

    public List<SampleQuestion> getSortedSampleQuestions(Biobank biobank, String orderByParam, boolean desc) {
        String orderParam = "";
        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY sampleQuestion." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        typedQuery = em.createQuery("SELECT sampleQuestion FROM SampleQuestion sampleQuestion WHERE " +
                "sampleQuestion.biobank = :biobank " +
                orderParam, SampleQuestion.class);
        typedQuery.setParameter("biobank", biobank);
        return typedQuery.getResultList();
    }


    public List<SampleRequest> getSampleRequests(Biobank biobank, RequestState requestState) {
        TypedQuery typedQuery1 = em.createQuery("SELECT p FROM SampleReservation p " +
                "where p.biobank = :bioParam " +
                "and (p.requestState = :requestStateParam OR :requestStateParam IS NULL)", SampleRequest.class);

        typedQuery1.setParameter("bioParam", biobank);
        typedQuery1.setParameter("requestStateParam", requestState);

        return typedQuery1.getResultList();
    }

    public List<SampleReservation> getSampleReservations(Biobank biobank, RequestState requestState) {
        TypedQuery typedQuery1 = em.createQuery("SELECT p FROM SampleReservation p " +
                "where p.biobank = :bioParam " +
                "and (p.requestState = :requestStateParam OR :requestStateParam IS NULL)", SampleReservation.class);

        typedQuery1.setParameter("bioParam", biobank);
        typedQuery1.setParameter("requestStateParam", requestState);

        return typedQuery1.getResultList();
    }

    public List<SampleReservation> getSampleReservationsOrderedByDate() {
        TypedQuery<SampleReservation> typedQuery1 = em.createQuery("SELECT p FROM SampleReservation p " +
                "WHERE p.requestState = 'CLOSED' " +
                "AND p.class LIKE 'SampleReservation' " +
                "ORDER BY p.validity" +
                "", SampleReservation.class);

        return typedQuery1.getResultList();
    }


    public List<SampleRequest> getSampleRequestsSorted(Project project, String orderByParam, boolean desc) {
        String orderParam = "";
        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY sampleRequest." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        TypedQuery<SampleRequest> typedQuery1 = em.createQuery("SELECT sampleRequest FROM SampleRequest sampleRequest WHERE " +
                "sampleRequest.project = :project " +
                orderParam, SampleRequest.class);
        typedQuery1.setParameter("project", project);
        return typedQuery1.getResultList();
    }

    public List<SampleReservation> getSampleReservationsSorted(User user, String orderByParam, boolean desc) {
        String orderParam = "";
        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY sampleReservation." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        TypedQuery<SampleReservation> typedQuery1 = em.createQuery("SELECT sampleReservation FROM SampleReservation sampleReservation WHERE " +
                "sampleReservation.user = :user " +
                orderParam, SampleReservation.class);
        typedQuery1.setParameter("user", user);
        return typedQuery1.getResultList();
    }

    public List<SampleReservation> getSampleReservationsBySample(Sample sample, String orderByParam, boolean desc) {
        String orderParam = "";
        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY sampleReservation." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        TypedQuery<SampleReservation> typedQuery1 = em.createQuery("SELECT sampleReservation FROM SampleReservation sampleReservation JOIN " +
                "sampleReservation.requests request WHERE " +
                "request.sample = :sample " +
                orderParam, SampleReservation.class);
        typedQuery1.setParameter("sample", sample);
        return typedQuery1.getResultList();
    }


}
