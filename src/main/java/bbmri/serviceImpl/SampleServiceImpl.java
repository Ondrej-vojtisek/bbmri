package bbmri.serviceImpl;

import bbmri.DAO.BiobankDAO;
import bbmri.DAO.SampleDAO;
import bbmri.DAOimpl.BiobankDAOImpl;
import bbmri.DAOimpl.SampleDAOImpl;
import bbmri.entities.Biobank;
import bbmri.entities.Request;
import bbmri.entities.RequestState;
import bbmri.entities.Sample;
import bbmri.service.SampleService;
import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 7.11.12
 * Time: 0:22
 * To change this template use File | Settings | File Templates.
 */
public class SampleServiceImpl implements SampleService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
    SampleDAO sampleDAO;
    BiobankDAO biobankDAO;

    private SampleDAO getSampleDAO() {
        if (sampleDAO == null) {
            sampleDAO = new SampleDAOImpl();
        }
        return sampleDAO;
    }

    private BiobankDAO getBiobankDAO() {
        if (biobankDAO == null) {
            biobankDAO = new BiobankDAOImpl();
        }
        return biobankDAO;
    }


    public Sample create(Sample sample, Long biobankId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        getSampleDAO().create(sample, em);
        Biobank biobank = getBiobankDAO().get(biobankId, em);
        if (biobank != null) {
            sample.setBiobank(biobank);

        }
        em.getTransaction().commit();
        em.close();
        return sample;
    }

    public void remove(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Sample sample = getSampleDAO().get(id, em);
        if (sample != null) {
            getSampleDAO().remove(sample, em);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Sample update(Sample sample) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        getSampleDAO().update(sample, em);
        em.getTransaction().commit();
        em.close();
        return sample;
    }

    public List<Sample> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Sample> samples = getSampleDAO().getAll(em);
        em.getTransaction().commit();
        em.close();
        return samples;
    }

    public Sample decreaseCount(Long sampleId, Integer requested) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Sample sample = getSampleDAO().get(sampleId, em);
        Integer count = sample.getNumOfAvailable();
        if ((count - requested) > 0) {
            count -= requested;
        } else {
            em.close();
            return sample;
        }
        sample.setNumOfAvailable(count);
        Request request = null;
        for (int i = 0; i < sample.getRequests().size(); i++) {
            if (sample.getRequests().get(i).getRequestState() == RequestState.APPROVED) {
                request = sample.getRequests().get(i);
                request.setRequestState(RequestState.EQUIPPED);
                break;
            }
        }
        em.getTransaction().commit();
        em.close();
        return sample;
    }

    public List<Sample> getSamplesByQuery(Sample sample) {
        String query = "WHERE";
        if (sample.getDiagnosis() != null) {
            query = query + " p.diagnosis like'" + sample.getDiagnosis().toString() + "'";
            query = query + " AND";
        }
        if (sample.getGrading() != null) {
            query = query + " p.grading ='" + sample.getGrading() + "'";
            query = query + " AND";
        }
        if (sample.getpTNM() != null) {
            query = query + " p.pTNM like'" + sample.getpTNM() + "'";
            query = query + " AND";
        }

        if (sample.getTNM() != null) {
            query = query + " p.TNM like'" + sample.getTNM() + "'";
            query = query + " AND";
        }

        if (sample.getTissueType() != null) {
            query = query + " p.tissueType like'" + sample.getTissueType() + "'";
            query = query + " AND";
        }

        if (sample.getNumOfAvailable() != null) {
            query = query + " p.numOfAvailable ='" + sample.getNumOfAvailable() + "'";
        }

        if (query.endsWith("AND")) {
            query = query.substring(0, query.length() - 3);
        }


        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Sample> samples = getSampleDAO().getSelected(em, query);
        em.getTransaction().commit();
        em.close();
        return samples;

    }

}
