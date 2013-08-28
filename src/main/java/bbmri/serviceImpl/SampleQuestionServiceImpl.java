package bbmri.serviceImpl;

import bbmri.dao.BiobankDao;
import bbmri.dao.ProjectDao;
import bbmri.dao.SampleQuestionDao;
import bbmri.entities.Biobank;
import bbmri.entities.Project;
import bbmri.entities.SampleQuestion;
import bbmri.service.SampleQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:44
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service
public class SampleQuestionServiceImpl implements SampleQuestionService {

    @Autowired
    private SampleQuestionDao sampleQuestionDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private ProjectDao projectDao;

    public SampleQuestion withdraw(SampleQuestion sampleQuestion, Long biobankId){
        try {
                    sampleQuestionDao.create(sampleQuestion);
                    Biobank biobankDB = biobankDao.get(biobankId);
                    if (biobankDB != null) {
                        sampleQuestion.setBiobank(biobankDB);
                    }
                    sampleQuestion.setProject(null);
                    sampleQuestionDao.update(sampleQuestion);
                    return sampleQuestion;
                } catch (DataAccessException ex) {
                    throw ex;
                }
    }

    /*
    public SampleQuestion create(SampleQuestion sampleQuestion) {
            try {
                sampleQuestionDao.create(sampleQuestion);
                return sampleQuestion;
            } catch (DataAccessException ex) {
                throw ex;
            }
        }
        */

    public SampleQuestion create(SampleQuestion sampleQuestion, Long biobankId, Long projectId) {
        try {
            sampleQuestionDao.create(sampleQuestion);
            Biobank biobankDB = biobankDao.get(biobankId);
            if (biobankDB != null) {
                sampleQuestion.setBiobank(biobankDB);
            }
            if(projectId == null){
                sampleQuestion.setProject(null);
                sampleQuestionDao.update(sampleQuestion);
                return sampleQuestion;
            }

            Project projectDB = projectDao.get(projectId);
            if (projectDB != null) {
                sampleQuestion.setProject(projectDB);
            }
            sampleQuestionDao.update(sampleQuestion);
            return sampleQuestion;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void remove(Long id) {
        try {
            SampleQuestion sampleQuestionDB = sampleQuestionDao.get(id);
            if (sampleQuestionDB != null) {
                sampleQuestionDao.remove(sampleQuestionDB);
            }
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    /*
    public void remove(SampleQuestion sampleQuestion) {
           try {
                   sampleQuestionDao.remove(sampleQuestion);
           } catch (DataAccessException ex) {
               throw ex;
           }
       }
       */

    public SampleQuestion update(SampleQuestion sampleQuestion) {
           try {
               sampleQuestionDao.update(sampleQuestion);
               return sampleQuestion;
           } catch (DataAccessException ex) {
               throw ex;
           }
       }

       public List<SampleQuestion> all() {
           try {
               List<SampleQuestion> sampleQuestions = sampleQuestionDao.all();
               return sampleQuestions;
           } catch (DataAccessException ex) {
               throw ex;
           }
       }

    public List<SampleQuestion> getAllByProject(Project project) {
        try {
            if(project == null){
                return null;
            }
            String query = "WHERE";
            query = query + " p.project.id ='" + project.getId().toString() + "'";
            List<SampleQuestion> sampleQuestions = sampleQuestionDao.getSelected(query);
            return sampleQuestions;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<SampleQuestion> getAllByBiobank(Biobank biobank) {
        try {
            if(biobank == null){
                return null;
            }
            String query = "WHERE";
            query = query + " p.biobank.id ='" + biobank.getId().toString() + "' AND " +
            "p.processed = 'f'";
            List<SampleQuestion> sampleQuestions = sampleQuestionDao.getSelected(query);
            return sampleQuestions;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public SampleQuestion get(Long id) {
              try {
                  SampleQuestion sampleQuestionDB = sampleQuestionDao.get(id);
                  return sampleQuestionDB;
              } catch (DataAccessException ex) {
                  throw ex;
              }
          }

    public Integer count() {
           try {
               return sampleQuestionDao.count();
           } catch (DataAccessException ex) {
               throw ex;
           }
       }
}
