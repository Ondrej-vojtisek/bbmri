package bbmri.serviceImpl;

import bbmri.dao.BiobankDao;
import bbmri.dao.ProjectDao;
import bbmri.dao.RequestDao;
import bbmri.dao.SampleDao;
import bbmri.entities.Request;
import bbmri.entities.Sample;
import bbmri.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private SampleDao sampleDao;

    @Autowired
    private BiobankDao biobankDao;

   /*
    public Request create(Request request) {
          try {
              requestDao.create(request);
              return request;
          } catch (DataAccessException ex) {
              throw ex;
          }
      }
      */


    public Request create(Long sampleId, Integer numOfRequested) {
        try {
            Request request = new Request();
            Sample sampleDB = sampleDao.get(sampleId);

            if (sampleDB != null) {
                request.setSample(sampleDB);
            }
            if (numOfRequested > 0) {
                request.setNumOfRequested(numOfRequested);
            }
            requestDao.create(request);

            //requestDao.update(request);
            return request;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Request create(Long sampleId) {
        try {
            Request request = new Request();

            Sample sampleDB = sampleDao.get(sampleId);

            if (sampleDB != null) {
                request.setSample(sampleDB);
            }
            requestDao.create(request);
            //update
            return request;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    /*
    public void remove(Request request) {
        try {
            requestDao.remove(request);
        } catch (DataAccessException ex) {
            throw ex;
        }
    }
    */

    public void remove(Long id) {
        try {
            Request requestDB = requestDao.get(id);
            if (requestDB != null) {
                requestDao.remove(requestDB);
            }
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Request update(Request request) {
        try {
            Request requestDB = requestDao.get(request.getId());
            if (requestDB == null) {
                return null;
            }
            if(request.getNumOfRequested() != null){
                requestDB.setNumOfRequested(request.getNumOfRequested());
            }
            requestDao.update(requestDB);
            return requestDB;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Request> all() {
        try {
            List<Request> requests = requestDao.all();
            return requests;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Request get(Long id) {
        try {
            Request request = requestDao.get(id);
            return request;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Integer count() {
        try {
            return requestDao.count();
        } catch (DataAccessException ex) {
            throw ex;
        }
    }
}
