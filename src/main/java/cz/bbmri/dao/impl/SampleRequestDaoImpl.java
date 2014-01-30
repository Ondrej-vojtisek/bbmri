package cz.bbmri.dao.impl;

import cz.bbmri.dao.SampleRequestDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.SampleRequest;
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
public class SampleRequestDaoImpl extends BasicDaoImpl<SampleRequest> implements SampleRequestDao {

    @SuppressWarnings("unchecked")
    public List<SampleRequest> getByBiobankAndProcessed(Biobank biobank, boolean processed) {
        Query query = em.createQuery("SELECT p FROM SampleRequest p where p.biobank = :bioParam and " +
                "p.processed = :boolParam");
        query.setParameter("bioParam", biobank);
        query.setParameter("boolParam", processed);
        return query.getResultList();
    }

}
