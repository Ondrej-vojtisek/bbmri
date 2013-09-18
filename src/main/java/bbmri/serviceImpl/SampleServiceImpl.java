package bbmri.serviceImpl;

import bbmri.dao.BiobankDao;
import bbmri.dao.RequestDao;
import bbmri.dao.SampleDao;
import bbmri.entities.Biobank;
import bbmri.entities.Request;
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

    @Autowired
    private RequestDao requestDao;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /*
    public Sample create(Sample sample) {
        notNull(sample);
        sampleDao.create(sample);
        return sample;
    }
    */

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
        sampleDao.update(sample);
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

            List<Request> requests = sampleDB.getRequests();
            if(requests != null){
                for(Request request : requests){
                    requestDao.remove(request);
                }
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

        if ((available - requested) >= 0) {
            sampleDB.setNumOfAvailable(available - requested);
            sampleDB.setNumOfSamples(numOfSamples - requested);


        } else {
            //TODO: exception

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
            // TODO: exception
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
        return sampleDao.getSelected(sample);
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
        List<Sample> results = biobankDB.getSamples();
        /*DONT DELETE THIS COMMENT*/
        logger.debug(" DONT DELETE" + results);
        return results;
    }

    public Sample get(Long id) {
        notNull(id);
        return sampleDao.get(id);
    }

    public Sample eagerGet(Long id, boolean biobank, boolean request){
          notNull(id);
          Sample sampleDB = sampleDao.get(id);


          /*Not only comments - this force hibernate to load mentioned relationship from db. Otherwise it wont be accessible from presentational layer of application.*/

          if(biobank){
              logger.debug("" + sampleDB.getBiobank());
          }
          if(request){
              logger.debug("" + sampleDB.getRequests());
          }

          return sampleDB;

      }

}
