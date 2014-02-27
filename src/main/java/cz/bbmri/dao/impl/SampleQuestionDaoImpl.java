package cz.bbmri.dao.impl;

import cz.bbmri.dao.SampleQuestionDao;
import cz.bbmri.entities.*;
import cz.bbmri.entities.SampleQuestion;
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
        return getByBiobankAndState(biobank, requestState, SampleRequest.class);
    }

    public List<SampleQuestion> getSampleReservations(Biobank biobank, RequestState requestState) {
        return getByBiobankAndState(biobank, requestState, SampleReservation.class);
    }


    @SuppressWarnings("unchecked")
    private List<SampleQuestion> getByBiobankAndState(Biobank biobank, RequestState requestState, Class typeParam) {
        Query query = em.createQuery("SELECT p FROM SampleQuestion p where p.biobank = :bioParam and " +
                "p.requestState = :requestStateParam and TYPE(p) IN (:typeParam)");
        query.setParameter("bioParam", biobank);
        query.setParameter("requestStateParam", requestState);
        query.setParameter("typeParam", typeParam);
        return query.getResultList();
    }


}
