package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.ProjectDao;
import cz.bbmri.dao.SampleQuestionDao;
import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.service.SampleQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
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
@Service("sampleRequestService")
public class SampleQuestionServiceImpl extends BasicServiceImpl implements SampleQuestionService {

    @Autowired
    private SampleQuestionDao sampleQuestionDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserDao userDao;


    public SampleRequest create(SampleRequest sampleRequest, Long biobankId, Long projectId) {
        notNull(sampleRequest);
        notNull(biobankId);
        notNull(projectId);

        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            logger.debug("biobankDB can't be null");
            return null;
        }
        Project projectDB = projectDao.get(projectId);
        if (projectDB == null) {
            logger.debug("projectDB can't be null");
            return null;
        }
        sampleRequest.setRequestState(RequestState.NEW);
        sampleRequest.setCreated(new Date());

        sampleRequest.setBiobank(biobankDB);
        sampleRequest.setProject(projectDB);
        sampleQuestionDao.update(sampleRequest);
        return sampleRequest;
    }

    public SampleQuestion create(SampleQuestion sampleQuestion, Long biobankId) {
        notNull(sampleQuestion);
        notNull(biobankId);

        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            logger.debug("biobankDB can't be null");
            return null;
        }

        sampleQuestion.setRequestState(RequestState.NEW);
        sampleQuestion.setCreated(new Date());
        sampleQuestion.setBiobank(biobankDB);
        sampleQuestionDao.create(sampleQuestion);

        return sampleQuestion;
    }

    public boolean remove(Long id) {
        notNull(id);
        SampleQuestion sampleQuestionDB = sampleQuestionDao.get(id);
        if (sampleQuestionDB == null) {
            return false;
        }

        if (sampleQuestionDB.getBiobank() != null) {
            sampleQuestionDB.setBiobank(null);
        }

        if (sampleQuestionDB instanceof SampleRequest) {
            if (((SampleRequest) sampleQuestionDB).getProject() != null) {
                ((SampleRequest) sampleQuestionDB).setProject(null);
            }
        }

        if (sampleQuestionDB instanceof SampleReservation) {
            if (((SampleReservation) sampleQuestionDB).getUser() != null) {
                ((SampleReservation) sampleQuestionDB).setUser(null);
            }
        }

        sampleQuestionDao.remove(sampleQuestionDB);
        return true;
    }

    public SampleQuestion update(SampleQuestion sampleQuestion) {
        if (sampleQuestion == null) {
            logger.debug("SampleQuestion can't be null");
            return null;
        }
        SampleQuestion sampleQuestionDB = sampleQuestionDao.get(sampleQuestion.getId());

        if (sampleQuestionDB == null) {
            logger.debug("SampleQuestionDB can't be null");
            return null;
        }

        boolean changed = false;

        if (sampleQuestion.getRequestState() != null) {

            // set new state
            changed = true;
            sampleQuestionDB.setRequestState(sampleQuestion.getRequestState());

            // completition of reservation triggers validity for sample reservation

            if (sampleQuestion.getRequestState() == RequestState.CLOSED
                    && sampleQuestionDB instanceof SampleReservation) {
                setValidity(sampleQuestionDB);
            }
        }

        // only if sampleQuestion was changed
        if (changed){
            sampleQuestionDB.setLastModification(new Date());
            sampleQuestionDao.update(sampleQuestionDB);
        }
        return sampleQuestion;
    }

    private void setValidity(SampleQuestion sampleQuestion) {
        if (sampleQuestion == null) return;

        if (!(sampleQuestion instanceof SampleReservation)) return;

        Date today = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        // add one month
        cal.add(Calendar.MONTH, 1);

        // Validity one month
        ((SampleReservation) sampleQuestion).setValidity(cal.getTime());
    }

    @Transactional(readOnly = true)
    public List<SampleQuestion> all() {
        return sampleQuestionDao.all();
    }

    @Transactional(readOnly = true)
    public SampleQuestion get(Long id) {

        SampleQuestion sampleQuestionDB = sampleQuestionDao.get(id);

        // eagerGet

        logger.debug("SampleRequest: " + sampleQuestionDB.getRequests());

        return sampleQuestionDB;
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return sampleQuestionDao.count();
    }

    @Transactional(readOnly = true)
    public List<SampleQuestion> allOrderedBy(String orderByParam, boolean desc) {
        return sampleQuestionDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<SampleQuestion> nOrderedBy(String orderByParam, boolean desc, int number) {
        return sampleQuestionDao.nOrderedBy(orderByParam, desc, number);
    }

    @Transactional(readOnly = true)
    public List<SampleQuestion> getSampleRequests(Long biobankId, RequestState requestState) {
        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            logger.debug("BiobankDB can't be null");
            return null;
        }

        // requestState CAN be null

        return sampleQuestionDao.getSampleRequests(biobankDB, requestState);
    }

    @Transactional(readOnly = true)
    public List<SampleQuestion> getSampleReservations(Long biobankId, RequestState requestState) {
        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            logger.debug("BiobankDB can't be null");
            return null;
        }

        if (requestState == null) {
            logger.debug("RequestState can't be null");
            return null;
        }

        return sampleQuestionDao.getSampleReservations(biobankDB, requestState);
    }


}
