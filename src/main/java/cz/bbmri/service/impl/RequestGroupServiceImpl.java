package cz.bbmri.service.impl;

import cz.bbmri.dao.RequestDao;
import cz.bbmri.dao.RequestGroupDao;
import cz.bbmri.dao.SampleDao;
import cz.bbmri.dao.SampleRequestDao;
import cz.bbmri.entities.Request;
import cz.bbmri.entities.RequestGroup;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.service.RequestGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 6.2.13
 * Time: 12:03
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("requestGroupService")
public class RequestGroupServiceImpl extends BasicServiceImpl implements RequestGroupService {

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private RequestGroupDao requestGroupDao;

    @Autowired
    private SampleRequestDao sampleRequestDao;

    @Autowired
    private SampleDao sampleDao;

    public boolean create(List<Long> samplesId, Long sampleRequestId) {
        if (samplesId == null) {
            logger.debug("SamplesId can't be null");
            return false;
        }

        if (samplesId.isEmpty()) {
            logger.debug("SamplesId can't be empty");
            return false;
        }

        if (sampleRequestId == null) {
            logger.debug("sampleRequestId can't be null");
            return false;
        }

        SampleRequest sampleRequestDB = sampleRequestDao.get(sampleRequestId);

        if (sampleRequestDB == null) {
            logger.debug("sampleRequestDB can't be null");
            return false;
        }

        // Create requestGroup and assign to SampleRequest

        RequestGroup requestGroup = new RequestGroup();

        // Actual date

        requestGroup.setSampleRequest(sampleRequestDB);

        // ManyToOne relationship

        requestGroup.setLastModification(new Date());

        // Find all samples and create Requests

        for (Long sampleId : samplesId) {

            Sample sampleDB = sampleDao.get(sampleId);

            if (sampleDB == null) {
                logger.debug("sampleDB can't be null - sampleId was: " + sampleId);
                return false;
            }

            Request request = new Request();

            // ManyToOne relationship

            request.setSample(sampleDB);

            // Implicit request one sample - it can be changed later from front-end

            request.setNumOfRequested(1);

            // ManyToOne relationship

            request.setRequestGroup(requestGroup);

            requestDao.create(request);
        }

        requestGroupDao.create(requestGroup);
        return true;
    }

    public boolean remove(Long id) {
        return false;
    }

    public RequestGroup update(RequestGroup requestGroup) {
        return null;

    }

    @Transactional(readOnly = true)
    public List<RequestGroup> all() {
        return requestGroupDao.all();
    }

    @Transactional(readOnly = true)
    public RequestGroup get(Long id) {
        notNull(id);
        return requestGroupDao.get(id);
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return requestGroupDao.count();
    }

    @Transactional(readOnly = true)
    public List<RequestGroup> allOrderedBy(String orderByParam, boolean desc) {
        return requestGroupDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<RequestGroup> nOrderedBy(String orderByParam, boolean desc, int number) {
        return requestGroupDao.nOrderedBy(orderByParam, desc, number);
    }
}
