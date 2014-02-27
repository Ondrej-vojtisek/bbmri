package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.ProjectDao;
import cz.bbmri.dao.SampleQuestionDao;
import cz.bbmri.entities.*;
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
@Service("sampleRequestService")
public class SampleQuestionServiceImpl extends BasicServiceImpl implements SampleQuestionService {

    @Autowired
    private SampleQuestionDao sampleQuestionDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private ProjectDao projectDao;


    public SampleRequest create(SampleRequest sampleRequest, Long biobankId, Long projectId) {
        notNull(sampleRequest);
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
        sampleRequest.setRequestState(RequestState.NEW);
        sampleRequest.setCreated(new Date());

        sampleQuestionDao.create(sampleRequest);
        sampleRequest.setBiobank(biobankDB);
        sampleRequest.setProject(projectDB);
        sampleQuestionDao.update(sampleRequest);
        return sampleRequest;
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

        if (sampleQuestion.getRequestState() != null)
            sampleQuestionDB.setRequestState(sampleQuestion.getRequestState());
        sampleQuestionDB.setLastModification(new Date());

        sampleQuestionDao.update(sampleQuestionDB);
        return sampleQuestion;
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

        if (requestState == null) {
            logger.debug("RequestState can't be null");
            return null;
        }

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
