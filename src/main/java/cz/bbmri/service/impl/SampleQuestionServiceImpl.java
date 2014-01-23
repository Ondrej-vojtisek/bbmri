package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.ProjectDao;
import cz.bbmri.dao.SampleQuestionDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.service.SampleQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:44
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("sampleQuestionService")
public class SampleQuestionServiceImpl extends BasicServiceImpl implements SampleQuestionService {

    @Autowired
    private SampleQuestionDao sampleQuestionDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private ProjectDao projectDao;


    /**
     * Way how to withdraw sample without process of creating project. This should be enabled only for employee of biobank.
     *
     * @param sampleQuestion
     * @param biobankId
     * @return
     */
    public SampleQuestion withdraw(SampleQuestion sampleQuestion, Long biobankId) {
        notNull(sampleQuestion);
        notNull(biobankId);

        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            return null;
            // TODO: exception
        }
        sampleQuestion.setProcessed(false);
        sampleQuestionDao.create(sampleQuestion);
        sampleQuestion.setBiobank(biobankDB);
        biobankDB.getSampleQuestions().add(sampleQuestion);
        biobankDao.update(biobankDB);
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
        sampleQuestion.setRequestState(RequestState.NEW);
        sampleQuestion.setProcessed(false);
        sampleQuestion.setCreated(new Date());

        sampleQuestionDao.create(sampleQuestion);
        sampleQuestion.setBiobank(biobankDB);
        biobankDB.getSampleQuestions().add(sampleQuestion);
        biobankDao.update(biobankDB);

        sampleQuestion.setProject(projectDB);
        projectDB.getSampleQuestions().add(sampleQuestion);
        projectDao.update(projectDB);
        sampleQuestionDao.update(sampleQuestion);
        return sampleQuestion;
    }

    public boolean remove(Long id) {
        notNull(id);
        SampleQuestion sampleQuestionDB = sampleQuestionDao.get(id);
        if(sampleQuestionDB == null){
            return false;
        }

            Biobank biobankDB = biobankDao.get(sampleQuestionDB.getBiobank().getId());
            if (biobankDB != null) {
                biobankDB.getSampleQuestions().remove(sampleQuestionDB);
                biobankDao.update(biobankDB);
                sampleQuestionDB.setBiobank(null);
            }
            Project projectDB = projectDao.get(sampleQuestionDB.getProject().getId());
            if (projectDB != null) {
                projectDB.getSampleQuestions().remove(sampleQuestionDB);
                projectDao.update(projectDB);
                sampleQuestionDB.setProject(null);
            }

            sampleQuestionDao.remove(sampleQuestionDB);
            return true;
    }

    public SampleQuestion update(SampleQuestion sampleQuestion) {
        sampleQuestionDao.update(sampleQuestion);
        return sampleQuestion;
    }

    @Transactional(readOnly = true)
    public List<SampleQuestion> all() {
        return sampleQuestionDao.all();
    }

    /*
    public List<SampleQuestion> getAllByProject(Project project) {
        notNull(project);
        return sampleQuestionDao.getByProject(project);
    }

  public List<SampleQuestion> getAllByBiobank(Biobank biobank) {
        notNull(biobank);
        return sampleQuestionDao.getByBiobank(biobank);
    }
  */

    @Transactional(readOnly = true)
    public SampleQuestion get(Long id) {
        return sampleQuestionDao.get(id);
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return sampleQuestionDao.count();
    }

    @Transactional(readOnly = true)
    public List<SampleQuestion> allOrderedBy(String orderByParam, boolean desc){
        return sampleQuestionDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<SampleQuestion> nOrderedBy(String orderByParam, boolean desc, int number){
        return sampleQuestionDao.nOrderedBy(orderByParam, desc, number);
    }

}
