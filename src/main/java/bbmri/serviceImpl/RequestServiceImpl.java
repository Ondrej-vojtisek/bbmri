package bbmri.serviceImpl;

import bbmri.DAO.BiobankDAO;
import bbmri.DAO.ProjectDAO;
import bbmri.DAO.RequestDAO;
import bbmri.DAO.SampleDAO;
import bbmri.entities.*;
import bbmri.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
        try {
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
            if (request.getRequestState() != null) requestDB.setRequestState(request.getRequestState());

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

    public List<Request> getAllByProject(Long projectId) {
        try {
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
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Request> getAllNew() {
        try {
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
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Request> getAllNewByBiobank(Long biobankId) {
        try {
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
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Request changeRequestState(Long requestId, RequestState requestState) {
        try {
            Request requestDB = requestDAO.get(requestId);
            if (requestDB != null) {
                requestDB.setRequestState(requestState);
                requestDAO.update(requestDB);
            }
            return requestDB;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Sample> getAllReleasableSamplesByBiobank(Long biobankId) {
        try {
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
