package bbmri.serviceImpl;

import bbmri.DAO.BiobankDAO;
import bbmri.DAO.ProjectDAO;
import bbmri.DAO.RequestDAO;
import bbmri.DAO.SampleDAO;
import bbmri.DAOimpl.BiobankDAOImpl;
import bbmri.DAOimpl.ProjectDAOImpl;
import bbmri.DAOimpl.RequestDAOImpl;
import bbmri.DAOimpl.SampleDAOImpl;
import bbmri.entities.*;
import bbmri.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class RequestServiceImpl implements RequestService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private SampleDAO sampleDAO;

    @Autowired
    private BiobankDAO biobankDAO;


    public Request create(Request request, Long projectId, Long sampleId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        requestDAO.create(request, em);

        Sample sampleDB = sampleDAO.get(sampleId, em);
        Project projectDB = projectDAO.get(projectId, em);

        if (sampleDB != null) {
            request.setProject(projectDB);
        }
        if (projectDB != null) {
            request.setSample(sampleDB);
        }

        request.setDate(new Date());
        request.setRequestState(RequestState.NEW);

        em.getTransaction().commit();
        em.close();
        return request;
    }

    public void remove(Request request) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        requestDAO.remove(request, em);
        em.getTransaction().commit();
        em.close();
    }

    public void remove(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Request requestDB = requestDAO.get(id, em);
        if (requestDB != null) {
            requestDAO.remove(requestDB, em);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Request update(Request request) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Request requestDB = requestDAO.get(request.getId(), em);
        if (requestDB == null) {
            em.close();
            return null;
        }
        if (request.getRequestState() != null) requestDB.setRequestState(request.getRequestState());

        requestDAO.update(requestDB, em);
        em.getTransaction().commit();
        em.close();
        return requestDB;
    }

    public List<Request> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Request> requests = requestDAO.getAll(em);
        em.getTransaction().commit();
        em.close();
        return requests;
    }

    public Request getById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Request request = requestDAO.get(id, em);
        em.getTransaction().commit();
        em.close();
        return request;
    }

    public List<Request> getAllByProject(Long projectId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Project projectDB = projectDAO.get(projectId, em);
        List<Request> requests = requestDAO.getAll(em);
        em.getTransaction().commit();
        em.close();

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
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<Request> requests = requestDAO.getAll(em);
        em.getTransaction().commit();
        em.close();

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
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<Request> requests = requestDAO.getAll(em);
        Biobank biobankDB = biobankDAO.get(biobankId, em);
        em.getTransaction().commit();
        em.close();


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
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Request requestDB = requestDAO.get(requestId, em);
        if (requestDB != null) {
            requestDB.setRequestState(requestState);
            requestDAO.update(requestDB, em);
        }
        em.getTransaction().commit();
        em.close();
        return requestDB;
    }

    public List<Sample> getAllReleasableSamplesByBiobank(Long biobankId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Biobank biobankDB = biobankDAO.get(biobankId, em);

        if (biobankDB == null) {
            em.close();
            return null;
        }

        List<Request> requests = requestDAO.getAll(em);
        if (requests == null) {
            em.close();
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
        em.close();
        return samples;
    }
}
