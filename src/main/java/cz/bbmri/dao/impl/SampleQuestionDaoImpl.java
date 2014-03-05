package cz.bbmri.dao.impl;

import cz.bbmri.dao.SampleQuestionDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.SampleReservation;
import cz.bbmri.entities.enumeration.RequestState;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class SampleQuestionDaoImpl extends BasicDaoImpl<SampleQuestion> implements SampleQuestionDao {

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
        Query query = em.createQuery("SELECT p FROM SampleQuestion p " +
                "where p.biobank = :bioParam " +
                "and (p.requestState = :requestStateParam OR :requestStateParam IS NULL) " +
                "and p.class LIKE :typeParam" +
                "");

        query.setParameter("bioParam", biobank);
        query.setParameter("typeParam", typeParam);
        query.setParameter("requestStateParam", requestState);

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<SampleReservation> getSampleReservationsOrderedByDate() {
        Query query = em.createQuery("SELECT p FROM SampleReservation p " +
                "WHERE p.requestState = 'CLOSED' " +
                "AND p.class LIKE 'SampleReservation' " +
                "ORDER BY p.validity" +
                "");

        return query.getResultList();
    }


}
