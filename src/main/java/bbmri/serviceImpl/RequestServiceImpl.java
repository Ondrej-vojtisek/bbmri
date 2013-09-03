package bbmri.serviceImpl;

import bbmri.dao.BiobankDao;
import bbmri.dao.ProjectDao;
import bbmri.dao.RequestDao;
import bbmri.dao.SampleDao;
import bbmri.entities.Request;
import bbmri.entities.Sample;
import bbmri.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service
public class RequestServiceImpl extends BasicServiceImpl implements RequestService {

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private SampleDao sampleDao;

    @Autowired
    private BiobankDao biobankDao;


    public Request create(Long sampleId, Integer numOfRequested) {
        notNull(sampleId);
        notNull(numOfRequested);

        Sample sampleDB = sampleDao.get(sampleId);
        if (sampleDB == null) {
            return null;
            //TODO: exception
        }
        if (numOfRequested <= 0) {
            return null;
            // TODO: exception
        }

        Request request = new Request();
        request.setSample(sampleDB);
        request.setNumOfRequested(numOfRequested);
        requestDao.create(request);

        return request;
    }

    public Request create(Long sampleId) {
        notNull(sampleId);
        Request request = new Request();
        Sample sampleDB = sampleDao.get(sampleId);
        if (sampleDB == null) {
            return null;
            //TODO: exception
        }

        request.setSample(sampleDB);
        requestDao.create(request);
        return request;
    }

    public void remove(Long id) {
        notNull(id);
        Request requestDB = requestDao.get(id);
        if (requestDB != null) {
            requestDao.remove(requestDB);
        }
    }

    public Request update(Request request) {
        notNull(request);
        Request requestDB = requestDao.get(request.getId());
        if (requestDB == null) {
            return null;
            //TODO: exception
        }
        if (request.getNumOfRequested() != null) {
               /* if(request.getNumOfRequested() < 0){
                TODO: exception
                }  */
            requestDB.setNumOfRequested(request.getNumOfRequested());
        }
        requestDao.update(requestDB);
        return requestDB;
    }

    public List<Request> all() {
        return requestDao.all();
    }

    public Request get(Long id) {
        notNull(id);
        return requestDao.get(id);
    }

    public Integer count() {
        return requestDao.count();
    }
}
