package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.ProjectDao;
import cz.bbmri.dao.SampleRequestDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.service.SampleRequestService;
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
public class SampleRequestServiceImpl extends BasicServiceImpl implements SampleRequestService {

    @Autowired
    private SampleRequestDao sampleRequestDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private ProjectDao projectDao;


    /**
     * Way how to withdraw sample without process of creating project. This should be enabled only for employee of biobank.
     *
     * @param sampleRequest
     * @param biobankId
     * @return
     */
    public SampleRequest withdraw(SampleRequest sampleRequest, Long biobankId) {
        notNull(sampleRequest);
        notNull(biobankId);

        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            return null;
            // TODO: exception
        }
        sampleRequestDao.create(sampleRequest);
        sampleRequest.setBiobank(biobankDB);
        biobankDB.getSampleRequests().add(sampleRequest);
        biobankDao.update(biobankDB);
        sampleRequest.setProject(null);
        sampleRequestDao.update(sampleRequest);
        return sampleRequest;
    }

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

        sampleRequestDao.create(sampleRequest);
        sampleRequest.setBiobank(biobankDB);
        biobankDB.getSampleRequests().add(sampleRequest);
        biobankDao.update(biobankDB);

        sampleRequest.setProject(projectDB);
        projectDB.getSampleRequests().add(sampleRequest);
        projectDao.update(projectDB);
        sampleRequestDao.update(sampleRequest);
        return sampleRequest;
    }

    public boolean remove(Long id) {
        notNull(id);
        SampleRequest sampleRequestDB = sampleRequestDao.get(id);
        if (sampleRequestDB == null) {
            return false;
        }

        Biobank biobankDB = biobankDao.get(sampleRequestDB.getBiobank().getId());
        if (biobankDB != null) {
            biobankDB.getSampleRequests().remove(sampleRequestDB);
            biobankDao.update(biobankDB);
            sampleRequestDB.setBiobank(null);
        }
        Project projectDB = projectDao.get(sampleRequestDB.getProject().getId());
        if (projectDB != null) {
            projectDB.getSampleRequests().remove(sampleRequestDB);
            projectDao.update(projectDB);
            sampleRequestDB.setProject(null);
        }

        sampleRequestDao.remove(sampleRequestDB);
        return true;
    }

    public SampleRequest update(SampleRequest sampleRequest) {
        if (sampleRequest == null) {
            logger.debug("SampleRequest can't be null");
            return null;
        }
        SampleRequest sampleRequestDB = sampleRequestDao.get(sampleRequest.getId());

        if (sampleRequestDB == null) {
            logger.debug("SampleRequestDB can't be null");
            return null;
        }

        if(sampleRequest.getRequestState() != null ) sampleRequestDB.setRequestState(sampleRequest.getRequestState());
        sampleRequestDB.setLastModification(new Date());

        sampleRequestDao.update(sampleRequestDB);
        return sampleRequest;
    }

    @Transactional(readOnly = true)
    public List<SampleRequest> all() {
        return sampleRequestDao.all();
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
    public SampleRequest get(Long id) {

        SampleRequest sampleRequestDB = sampleRequestDao.get(id);

        // eagerGet

        logger.debug("SampleRequest: " + sampleRequestDB.getRequests());

        return sampleRequestDB;
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return sampleRequestDao.count();
    }

    @Transactional(readOnly = true)
    public List<SampleRequest> allOrderedBy(String orderByParam, boolean desc) {
        return sampleRequestDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<SampleRequest> nOrderedBy(String orderByParam, boolean desc, int number) {
        return sampleRequestDao.nOrderedBy(orderByParam, desc, number);
    }

    public List<SampleRequest> getByBiobankAndState(Long biobankId, RequestState requestState) {
        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            logger.debug("BiobankDB can't be null");
            return null;
        }

        if (requestState == null) {
            logger.debug("RequestState can't be null");
            return null;
        }

        return sampleRequestDao.getByBiobankAndState(biobankDB, requestState);
    }

}
