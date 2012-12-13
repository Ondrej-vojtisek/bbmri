package bbmri.serviceImpl;

import bbmri.DAO.BiobankDAO;
import bbmri.DAO.SampleDAO;
import bbmri.entities.Biobank;
import bbmri.entities.Request;
import bbmri.entities.RequestState;
import bbmri.entities.Sample;
import bbmri.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
@Service
public class SampleServiceImpl implements SampleService {

    @Autowired
    private SampleDAO sampleDAO;

    @Autowired
    private BiobankDAO biobankDAO;

    public Sample create(Sample sample, Long biobankId) {

        sampleDAO.create(sample);
        Biobank biobank = biobankDAO.get(biobankId);
        if (biobank != null) {
            sample.setBiobank(biobank);

        }
        return sample;
    }

    public void remove(Long id) {
        Sample sample = sampleDAO.get(id);
        if (sample != null) {
            sampleDAO.remove(sample);
        }
    }

    public Sample update(Sample sample) {
        sampleDAO.update(sample);
        return sample;
    }

    public List<Sample> getAll() {
        List<Sample> samples = sampleDAO.getAll();
        return samples;
    }

    public Sample decreaseCount(Long sampleId, Integer requested) {
        Sample sample = sampleDAO.get(sampleId);
        Integer count = sample.getNumOfAvailable();
        if ((count - requested) > 0) {
            count -= requested;
        } else {
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

        List<Sample> samples = sampleDAO.getSelected(query);
        return samples;

    }
    public Integer getCount(){
       return sampleDAO.getCount();
    }

}
