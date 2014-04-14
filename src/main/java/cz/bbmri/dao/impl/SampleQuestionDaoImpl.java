package cz.bbmri.dao.impl;

import cz.bbmri.dao.SampleQuestionDao;
import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.RequestState;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class SampleQuestionDaoImpl extends BasicDaoImpl<SampleQuestion, Long> implements SampleQuestionDao {

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


    public List<SampleQuestion> getSampleRequests(Biobank biobank, RequestState requestState) {
        return getByBiobankAndState(biobank, requestState, "SampleRequest");
    }

    public List<SampleQuestion> getSampleReservations(Biobank biobank, RequestState requestState) {
        return getByBiobankAndState(biobank, requestState, "SampleReservation");
    }


    /**
     * Hack - comparing specialized class like string. This is probably bug in hibernate. I wasn't able to
     * make this working with .getClass or with Type(p) = :param ...
     */
    @SuppressWarnings("unchecked")
    private List<SampleQuestion> getByBiobankAndState(Biobank biobank, RequestState requestState, String typeParam) {
        typedQuery = em.createQuery("SELECT p FROM SampleQuestion p " +
                "where p.biobank = :bioParam " +
                "and (p.requestState = :requestStateParam OR :requestStateParam IS NULL) " +
                "and p.class LIKE :typeParam" +
                "", SampleQuestion.class);

        typedQuery.setParameter("bioParam", biobank);
        typedQuery.setParameter("typeParam", typeParam);
        typedQuery.setParameter("requestStateParam", requestState);

        return typedQuery.getResultList();
    }

    @SuppressWarnings("unchecked")
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

        TypedQuery<SampleReservation> typedQuery1  = em.createQuery("SELECT sampleReservation FROM SampleReservation sampleReservation JOIN " +
                "sampleReservation.requests request WHERE " +
                "request.sample = :sample " +
                orderParam, SampleReservation.class);
        typedQuery1.setParameter("sample", sample);
        return typedQuery1.getResultList();
    }


}
