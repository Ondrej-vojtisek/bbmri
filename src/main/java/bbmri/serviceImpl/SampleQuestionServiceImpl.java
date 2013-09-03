package bbmri.serviceImpl;

import bbmri.dao.BiobankDao;
import bbmri.dao.ProjectDao;
import bbmri.dao.SampleQuestionDao;
import bbmri.entities.Biobank;
import bbmri.entities.Project;
import bbmri.entities.SampleQuestion;
import bbmri.service.SampleQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SampleQuestionServiceImpl extends BasicServiceImpl implements SampleQuestionService {

    @Autowired
    private SampleQuestionDao sampleQuestionDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private ProjectDao projectDao;

    public SampleQuestion withdraw(SampleQuestion sampleQuestion, Long biobankId) {
        notNull(sampleQuestion);
        notNull(biobankId);

        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            return null;
            // TODO: exception
        }

        sampleQuestionDao.create(sampleQuestion);
        sampleQuestion.setBiobank(biobankDB);
        sampleQuestion.setProject(null);
        sampleQuestionDao.update(sampleQuestion);
        return sampleQuestion;
    }

    public SampleQuestion create(SampleQuestion sampleQuestion, Long biobankId, Long projectId) {
        notNull(sampleQuestion);
        notNull(biobankId);
        notNull(projectId);
        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            return null;
            // TODO: exception
        }
        Project projectDB = projectDao.get(projectId);
        if (projectDB == null) {
            return null;
            //TODO: exception
        }
        sampleQuestionDao.create(sampleQuestion);

        sampleQuestion.setBiobank(biobankDB);
        biobankDB.getSampleQuestions().add(sampleQuestion);
        biobankDao.update(biobankDB);

        sampleQuestion.setProject(projectDB);
        sampleQuestionDao.update(sampleQuestion);
        return sampleQuestion;
    }

    public void remove(Long id) {
        notNull(id);
        SampleQuestion sampleQuestionDB = sampleQuestionDao.get(id);
        if (sampleQuestionDB != null) {
            Biobank biobankDB = biobankDao.get(sampleQuestionDB.getBiobank().getId());
            if (biobankDB != null) {
                biobankDB.getSampleQuestions().remove(sampleQuestionDB);
                biobankDao.update(biobankDB);
                sampleQuestionDB.setBiobank(null);
            }
            sampleQuestionDao.remove(sampleQuestionDB);
        }
    }

    public SampleQuestion update(SampleQuestion sampleQuestion) {
        sampleQuestionDao.update(sampleQuestion);
        return sampleQuestion;
    }

    public List<SampleQuestion> all() {
        return sampleQuestionDao.all();
    }

    public List<SampleQuestion> getAllByProject(Project project) {
        notNull(project);
        String query = "WHERE";
        query = query + " p.project.id ='" + project.getId().toString() + "'";
        return sampleQuestionDao.getSelected(query);
    }

    public List<SampleQuestion> getAllByBiobank(Biobank biobank) {
        notNull(biobank);
        String query = "WHERE";
        query = query + " p.biobank.id ='" + biobank.getId().toString() + "' AND " +
                "p.processed = 'f'";
        return sampleQuestionDao.getSelected(query);
    }

    public SampleQuestion get(Long id) {
        return sampleQuestionDao.get(id);
    }

    public Integer count() {
        return sampleQuestionDao.count();
    }
}
