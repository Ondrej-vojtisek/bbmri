package bbmri.serviceImpl;

import bbmri.DAO.BiobankDAO;
import bbmri.DAO.ProjectDAO;
import bbmri.DAO.RequestDAO;
import bbmri.DAO.SampleDAO;
import bbmri.entities.*;
import bbmri.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Date;
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


    public Request create(Request request, Long projectId, Long sampleId) {
        requestDAO.create(request);

        Sample sampleDB = sampleDAO.get(sampleId);
        Project projectDB = projectDAO.get(projectId);

        if (sampleDB != null) {
            request.setProject(projectDB);
        }
        if (projectDB != null) {
            request.setSample(sampleDB);
        }

        request.setDate(new Date());
        request.setRequestState(RequestState.NEW);
        return request;
    }

    public void remove(Request request) {
        requestDAO.remove(request);
    }

    public void remove(Long id) {
        Request requestDB = requestDAO.get(id);
        if (requestDB != null) {
            requestDAO.remove(requestDB);
        }
    }

    public Request update(Request request) {
        Request requestDB = requestDAO.get(request.getId());
        if (requestDB == null) {
            return null;
        }
        if (request.getRequestState() != null) requestDB.setRequestState(request.getRequestState());

        requestDAO.update(requestDB);
        return requestDB;
    }

    public List<Request> getAll() {
        List<Request> requests = requestDAO.getAll();
        return requests;
    }

    public Request getById(Long id) {
        Request request = requestDAO.get(id);
        return request;
    }

    public List<Request> getAllByProject(Long projectId) {
        Project projectDB = projectDAO.get(projectId);
        List<Request> requests = requestDAO.getAll();

        if (projectDB == null) {
            return null;
        }

        List<Request> result = new ArrayList<Request>();
        if (requests != null) {
            for (Request request : requests) {
                if (request.getProject().equals(projectDB)) {
                    result.add(request);
                }
            }
        }
        return result;
    }

    public List<Request> getAllNew() {
        List<Request> requests = requestDAO.getAll();

        List<Request> result = new ArrayList<Request>();
        if (requests != null) {
            for (Request request : requests) {
                if (request.getRequestState() == RequestState.NEW) {
                    result.add(request);
                }
            }
        }
        return result;
    }

    public List<Request> getAllNewByBiobank(Long biobankId) {
        List<Request> requests = requestDAO.getAll();
        Biobank biobankDB = biobankDAO.get(biobankId);

        if (biobankDB == null) {
            return null;
        }


        List<Request> result = new ArrayList<Request>();
        if (requests != null) {
            for (Request request : requests) {
                Biobank requestBiobank = request.getSample().getBiobank();
                if (requestBiobank == null) {
                    continue;
                }

                if (request.getRequestState() == RequestState.NEW &&
                        requestBiobank.equals(biobankDB)) {
                    result.add(request);
                }
            }
        }
        return result;
    }

    public Request changeRequestState(Long requestId, RequestState requestState) {
        Request requestDB = requestDAO.get(requestId);
        if (requestDB != null) {
            requestDB.setRequestState(requestState);
            requestDAO.update(requestDB);
        }
        return requestDB;
    }

    public List<Sample> getAllReleasableSamplesByBiobank(Long biobankId) {
        Biobank biobankDB = biobankDAO.get(biobankId);

        if (biobankDB == null) {
            return null;
        }

        List<Request> requests = requestDAO.getAll();
        if (requests == null) {
            return null;
        }

        List<Sample> samples = new ArrayList<Sample>();

        for (Request request : requests) {
            Biobank requestBiobank = request.getSample().getBiobank();
            if (requestBiobank == null) {
                continue;
            }
            if (request.getRequestState() == RequestState.APPROVED &&
                    requestBiobank.equals(biobankDB)) {
                samples.add(request.getSample());
            }
        }
        return samples;
    }
}
