package cz.bbmri.dao.impl;

import cz.bbmri.dao.RequestGroupDao;
import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.RequestState;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 6.2.13
 * Time: 12:17
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class RequestGroupDaoImpl extends BasicDaoImpl<RequestGroup> implements RequestGroupDao {

    @SuppressWarnings("unchecked")
    public List<RequestGroup> getByBiobankAndState(Biobank biobank, RequestState requestState){
        notNull(biobank);
        notNull(requestState);
        Query query = em.createQuery("SELECT p FROM RequestGroup p WHERE p.biobank = :biobankParam " +
                "and p.requestState = :requestStateParam");
        query.setParameter("biobankParam", biobank);
        query.setParameter("requestStateParam", requestState);
        return query.getResultList();
    }
}
