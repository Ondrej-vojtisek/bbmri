package bbmri.serviceImpl;

import bbmri.DAO.BiobankDAO;
import bbmri.DAO.SampleDAO;
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
    private SampleDAO sampleDAO;

    @Autowired
    private BiobankDAO biobankDAO;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public Sample create(Sample sample, Long biobankId) {
        try {
                    sampleDAO.create(sample);
                    Biobank biobank = biobankDAO.get(biobankId);
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
            Sample sample = sampleDAO.get(id);
            if (sample != null) {
                sampleDAO.remove(sample);
            }
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Sample update(Sample sample) {
        try {
            sampleDAO.update(sample);
            return sample;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Sample> getAll() {
        try {
            List<Sample> samples = sampleDAO.getAll();
            return samples;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Sample decreaseCount(Long sampleId, Integer requested) {
        try {
            Sample sample = sampleDAO.get(sampleId);
            Integer available = sample.getNumOfAvailable();
            Integer numOfSamples = sample.getNumOfSamples();
            if ((available - requested) > 0) {
                sample.setNumOfAvailable(available - requested);
                sample.setNumOfAvailable(numOfSamples - requested);
            } else {
                return sample;
            }
            sampleDAO.update(sample);
            return sample;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Sample withdrawSample(Long sampleId, Integer requested) {
          try {
              Sample sample = sampleDAO.get(sampleId);
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
                  sampleDAO.remove(sample);
                  return null;
              }
              sample.setNumOfAvailable(available);
              sample.setNumOfSamples(numOfSamples);
              sampleDAO.update(sample);
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
            List<Sample> samples = sampleDAO.getSelected(query);

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
              List<Sample> samples = sampleDAO.getSelected(query);
              return samples;
          } catch (DataAccessException ex) {
              throw ex;
          }
      }

    public Integer getCount() {
        try {
            return sampleDAO.getCount();
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Sample> getAllByBiobank(Long biobankId) {
        try {
            Biobank biobankDB = biobankDAO.get(biobankId);
            if (biobankDB == null) {
                return null;
            }
            String query = "WHERE";
            query = query + " p.biobank.id ='" + biobankId.toString() + "'";
            List<Sample> samples = sampleDAO.getSelected(query);

            return samples;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Sample getById(Long id) {
        try {
            Sample sampleDB = sampleDAO.get(id);
            return sampleDB;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

}
