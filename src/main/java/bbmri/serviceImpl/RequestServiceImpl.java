package bbmri.serviceImpl;

import bbmri.DAO.BiobankDAO;
import bbmri.DAO.ProjectDAO;
import bbmri.DAO.RequestDAO;
import bbmri.DAO.SampleDAO;
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
    private RequestDAO requestDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private SampleDAO sampleDAO;

    @Autowired
    private BiobankDAO biobankDAO;


    public Request create(Long sampleId, Integer numOfRequested) {
        try {
            Request request = new Request();
            Sample sampleDB = sampleDAO.get(sampleId);

            if (sampleDB != null) {
                request.setSample(sampleDB);
            }
            if (numOfRequested > 0) {
                request.setNumOfRequested(numOfRequested);
            }
            requestDAO.create(request);

            //requestDAO.update(request);
            return request;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Request create(Long sampleId) {
        try {
            Request request = new Request();

            Sample sampleDB = sampleDAO.get(sampleId);

            if (sampleDB != null) {
                request.setSample(sampleDB);
            }
            requestDAO.create(request);
            //update
            return request;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void remove(Request request) {
        try {
            requestDAO.remove(request);
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void remove(Long id) {
        try {
            Request requestDB = requestDAO.get(id);
            if (requestDB != null) {
                requestDAO.remove(requestDB);
            }
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Request update(Request request) {
        try {
            Request requestDB = requestDAO.get(request.getId());
            if (requestDB == null) {
                return null;
            }
            requestDAO.update(requestDB);
            return requestDB;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Request> getAll() {
        try {
            List<Request> requests = requestDAO.getAll();
            return requests;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Request getById(Long id) {
        try {
            Request request = requestDAO.get(id);
            return request;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Integer getCount() {
        try {
            return requestDAO.getCount();
        } catch (DataAccessException ex) {
            throw ex;
        }
    }
}
