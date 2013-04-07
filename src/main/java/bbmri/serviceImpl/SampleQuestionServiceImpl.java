package bbmri.serviceImpl;

import bbmri.DAO.BiobankDAO;
import bbmri.DAO.ProjectDAO;
import bbmri.DAO.SampleQuestionDAO;
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
    private SampleQuestionDAO sampleQuestionDAO;

    @Autowired
    private BiobankDAO biobankDAO;

    @Autowired
    private ProjectDAO projectDAO;

    public SampleQuestion create(SampleQuestion sampleQuestion, Long biobankId, Long projectId) {
        try {
            sampleQuestionDAO.create(sampleQuestion);
            Biobank biobankDB = biobankDAO.get(biobankId);
            if (biobankDB != null) {
                sampleQuestion.setBiobank(biobankDB);
            }
            Project projectDB = projectDAO.get(projectId);
            if (projectDB != null) {
                sampleQuestion.setProject(projectDB);
            }

            return sampleQuestion;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void remove(Long id) {
        try {
            SampleQuestion sampleQuestionDB = sampleQuestionDAO.get(id);
            if (sampleQuestionDB != null) {
                sampleQuestionDAO.remove(sampleQuestionDB);
            }
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public SampleQuestion update(SampleQuestion sampleQuestion) {
           try {
               sampleQuestionDAO.update(sampleQuestion);
               return sampleQuestion;
           } catch (DataAccessException ex) {
               throw ex;
           }
       }

       public List<SampleQuestion> getAll() {
           try {
               List<SampleQuestion> sampleQuestions = sampleQuestionDAO.getAll();
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
            List<SampleQuestion> sampleQuestions = sampleQuestionDAO.getSelected(query);
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
            List<SampleQuestion> sampleQuestions = sampleQuestionDAO.getSelected(query);
            return sampleQuestions;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public SampleQuestion getById(Long id) {
              try {
                  SampleQuestion sampleQuestionDB = sampleQuestionDAO.get(id);
                  return sampleQuestionDB;
              } catch (DataAccessException ex) {
                  throw ex;
              }
          }
}
