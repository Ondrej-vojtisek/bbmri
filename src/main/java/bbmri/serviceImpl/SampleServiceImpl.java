package bbmri.serviceImpl;

import bbmri.dao.BiobankDao;
import bbmri.dao.SampleDao;
import bbmri.entities.Biobank;
import bbmri.entities.Sample;
import bbmri.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
public class SampleServiceImpl implements SampleService {

    @Autowired
    private SampleDao sampleDao;

    @Autowired
    private BiobankDao biobankDao;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public Sample create(Sample sample) {
           try {
                       sampleDao.create(sample);
                       return sample;
                   } catch (DataAccessException ex) {
                       throw ex;
                   }
       }

    public Sample create(Sample sample, Long biobankId) {
        try {
                    sampleDao.create(sample);
                    Biobank biobank = biobankDao.get(biobankId);
                    if (biobank != null) {
                        sample.setBiobank(biobank);
                    }
                    return sample;
                } catch (DataAccessException ex) {
                    throw ex;
                }
    }

    public void remove(Long id) {
        try {
            Sample sample = sampleDao.get(id);
            if (sample != null) {
                sampleDao.remove(sample);
            }
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    /*
    public void remove(Sample sample) {
          try {
            sampleDao.remove(sample);
          } catch (DataAccessException ex) {
              throw ex;
          }
      }
      */

    public Sample update(Sample sample) {
        try {
            sampleDao.update(sample);
            return sample;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Sample> all() {
        try {
            List<Sample> samples = sampleDao.all();
            return samples;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Sample decreaseCount(Long sampleId, Integer requested) {
        try {
            Sample sample = sampleDao.get(sampleId);
            Integer available = sample.getNumOfAvailable();
            Integer numOfSamples = sample.getNumOfSamples();
            if ((available - requested) > 0) {
                sample.setNumOfAvailable(available - requested);
                sample.setNumOfAvailable(numOfSamples - requested);
            } else {
                return sample;
            }
            sampleDao.update(sample);
            return sample;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Sample withdrawSample(Long sampleId, Integer requested) {
          try {
              Sample sample = sampleDao.get(sampleId);
              Integer available = sample.getNumOfAvailable();
              Integer numOfSamples = sample.getNumOfSamples();
              if ((available - requested) >= 0) {
                  available -= requested;
                  numOfSamples -=requested;
              }else if((numOfSamples - requested) >= 0){
                  available = 0;
                  numOfSamples -= requested;
              } else{
                  return sample;
              }
              if(numOfSamples == 0){
                  sampleDao.remove(sample);
                  return null;
              }
              sample.setNumOfAvailable(available);
              sample.setNumOfSamples(numOfSamples);
              sampleDao.update(sample);
              return sample;
          } catch (DataAccessException ex) {
              throw ex;
          }
      }

    public List<Sample> getSamplesByQuery(Sample sample) {
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
        try {
            List<Sample> samples = sampleDao.getSelected(query);

            return samples;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Sample> getSamplesByQueryAndBiobank(Sample sample, Biobank biobank) {
          String query = "WHERE";
          /*
              Queries temporal without %
          * */

          if(biobank == null){
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
          try {
              List<Sample> samples = sampleDao.getSelected(query);
              return samples;
          } catch (DataAccessException ex) {
              throw ex;
          }
      }

    public Integer count() {
        try {
            return sampleDao.count();
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Sample> getAllByBiobank(Long biobankId) {
        try {
            Biobank biobankDB = biobankDao.get(biobankId);
            if (biobankDB == null) {
                return null;
            }
            String query = "WHERE";
            query = query + " p.biobank.id ='" + biobankId.toString() + "'";
            List<Sample> samples = sampleDao.getSelected(query);

            return samples;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Sample get(Long id) {
        try {
            Sample sampleDB = sampleDao.get(id);
            return sampleDB;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

}
