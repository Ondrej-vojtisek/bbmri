package bbmri.serviceImpl;

import bbmri.dao.BiobankDao;
import bbmri.dao.SampleDao;
import bbmri.entities.Biobank;
import bbmri.entities.Sample;
import bbmri.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class SampleServiceImpl extends BasicServiceImpl implements SampleService {

    @Autowired
    private SampleDao sampleDao;

    @Autowired
    private BiobankDao biobankDao;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public Sample create(Sample sample) {
        notNull(sample);
        sampleDao.create(sample);
        return sample;
    }

    public Sample create(Sample sample, Long biobankId) {
        notNull(sample);
        notNull(biobankId);

        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            return null;
            //TODO: exception
        }

        sampleDao.create(sample);

        sample.setBiobank(biobankDB);
        biobankDB.getSamples().add(sample);
        biobankDao.update(biobankDB);

        return sample;
    }

    public void remove(Long id) {

        notNull(id);
        Sample sampleDB = sampleDao.get(id);
        if (sampleDB != null) {
            Biobank biobankDB = biobankDao.get(sampleDB.getBiobank().getId());
            if (biobankDB != null) {
                biobankDB.getSamples().remove(sampleDB);
                biobankDao.update(biobankDB);
                sampleDB.setBiobank(null);
            }
            sampleDao.remove(sampleDB);
        }
    }

    public Sample update(Sample sample) {
        notNull(sample);
        sampleDao.update(sample);
        return sample;
    }

    public List<Sample> all() {
        return sampleDao.all();
    }

    public Sample decreaseCount(Long sampleId, Integer requested) {
        notNull(sampleId);
        notNull(requested);

        Sample sampleDB = sampleDao.get(sampleId);
        if(sampleDB == null){
            return null;
            // TODO: exception
        }

        Integer available = sampleDB.getNumOfAvailable();
        Integer numOfSamples = sampleDB.getNumOfSamples();

        if ((available - requested) > 0) {
            sampleDB.setNumOfAvailable(available - requested);
            sampleDB.setNumOfSamples(numOfSamples - requested);
        } else {
            return sampleDB;
        }
        sampleDao.update(sampleDB);
        return sampleDB;
    }

    public Sample withdrawSample(Long sampleId, Integer requested) {
        notNull(sampleId);
        notNull(requested);

        Sample sample = sampleDao.get(sampleId);
        Integer available = sample.getNumOfAvailable();
        Integer numOfSamples = sample.getNumOfSamples();
        if ((available - requested) >= 0) {
            available -= requested;
            numOfSamples -= requested;
        } else if ((numOfSamples - requested) >= 0) {
            available = 0;
            numOfSamples -= requested;
        } else {
            return sample;
        }
        if (numOfSamples == 0) {
            sampleDao.remove(sample);
            return null;
        }
        sample.setNumOfAvailable(available);
        sample.setNumOfSamples(numOfSamples);
        sampleDao.update(sample);
        return sample;
    }

    public List<Sample> getSamplesByQuery(Sample sample) {
        notNull(sample);

        String query = "WHERE";
        /*
            Queries temporal without %
        * */

        if (sample.getDiagnosis() != null) {
            //query = query + " p.diagnosis like'" + sample.getDiagnosis().toString() + "'";
            query = query + " p.diagnosis ='" + sample.getDiagnosis().toString() + "'";
            query = query + " AND";
        }
        if (sample.getGrading() != null) {
            query = query + " p.grading ='" + sample.getGrading() + "'";
            query = query + " AND";
        }
        if (sample.getpTNM() != null) {
            //query = query + " p.pTNM like'" + sample.getpTNM() + "'";
            query = query + " p.pTNM ='" + sample.getpTNM() + "'";
            query = query + " AND";
        }

        if (sample.getTNM() != null) {
            //query = query + " p.TNM like'" + sample.getTNM() + "'";
            query = query + " p.TNM ='" + sample.getTNM() + "'";
            query = query + " AND";
        }

        if (sample.getTissueType() != null) {
            //query = query + " p.tissueType like'" + sample.getTissueType() + "'";
            query = query + " p.tissueType ='" + sample.getTissueType() + "'";
            query = query + " AND";
        }

        if (sample.getNumOfAvailable() != null) {
            query = query + " p.numOfAvailable ='" + sample.getNumOfAvailable() + "'";
        }

        if (query.endsWith("AND")) {
            query = query.substring(0, query.length() - 3);
        }

        return sampleDao.getSelected(query);

    }

    public List<Sample> getSamplesByQueryAndBiobank(Sample sample, Biobank biobank) {
        notNull(sample);
        notNull(biobank);

        String query = "WHERE";
          /*
              Queries temporal without %
          * */

        if (biobank == null) {
            return null;
        }

        query = query + " p.biobank.id ='" + biobank.getId() + "'";
        query = query + " AND";

        if (sample.getDiagnosis() != null) {
            //query = query + " p.diagnosis like'" + sample.getDiagnosis().toString() + "'";
            query = query + " p.diagnosis ='" + sample.getDiagnosis().toString() + "'";
            query = query + " AND";
        }
        if (sample.getGrading() != null) {
            query = query + " p.grading ='" + sample.getGrading() + "'";
            query = query + " AND";
        }
        if (sample.getpTNM() != null) {
            //query = query + " p.pTNM like'" + sample.getpTNM() + "'";
            query = query + " p.pTNM ='" + sample.getpTNM() + "'";
            query = query + " AND";
        }

        if (sample.getTNM() != null) {
            //query = query + " p.TNM like'" + sample.getTNM() + "'";
            query = query + " p.TNM ='" + sample.getTNM() + "'";
            query = query + " AND";
        }

        if (sample.getTissueType() != null) {
            //query = query + " p.tissueType like'" + sample.getTissueType() + "'";
            query = query + " p.tissueType ='" + sample.getTissueType() + "'";
            query = query + " AND";
        }

        if (sample.getNumOfAvailable() != null) {
            query = query + " p.numOfAvailable ='" + sample.getNumOfAvailable() + "'";
        }

        if (query.endsWith("AND")) {
            query = query.substring(0, query.length() - 3);
        }
        return sampleDao.getSelected(query);
    }

    public Integer count() {
        return sampleDao.count();
    }

    public List<Sample> getAllByBiobank(Long biobankId) {
        notNull(biobankId);

        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            return null;
        }
        String query = "WHERE";
        query = query + " p.biobank.id ='" + biobankId.toString() + "'";
        List<Sample> samples = sampleDao.getSelected(query);

        return samples;
    }

    public Sample get(Long id) {
        notNull(id);
        return sampleDao.get(id);
    }

}
