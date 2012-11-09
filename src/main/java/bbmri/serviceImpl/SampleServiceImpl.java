package bbmri.serviceImpl;

import bbmri.DAO.SampleDAO;
import bbmri.DAOimpl.SampleDAOImpl;
import bbmri.entities.Sample;
import bbmri.service.SampleService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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

    private SampleDAO getSampleDAO() {
        if (sampleDAO == null) {
            sampleDAO = new SampleDAOImpl();
        }
        return sampleDAO;
    }

    public Sample create(Sample sample){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        getSampleDAO().create(sample, em);
        em.getTransaction().commit();
        em.close();
        return sample;
    }

    public void remove(Long id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
           Sample sample = getSampleDAO().get(id, em);
        if (sample != null) {
            getSampleDAO().remove(sample, em);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Sample update(Sample sample){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        getSampleDAO().update(sample, em);
        em.getTransaction().commit();
        em.close();
        return sample;
    }

    public List<Sample> getAll(){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Sample> samples = getSampleDAO().getAll(em);
        em.getTransaction().commit();
        em.close();
        return samples;
    }

}
