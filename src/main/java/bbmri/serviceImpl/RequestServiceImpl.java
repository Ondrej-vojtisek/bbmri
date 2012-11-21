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
public class RequestServiceImpl implements RequestService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
    RequestDAO requestDAO;
    ProjectDAO projectDAO;
    SampleDAO sampleDAO;
    BiobankDAO biobankDAO;

    private RequestDAO getRequestDAO() {
        if (requestDAO == null) {
            requestDAO = new RequestDAOImpl();
        }
        return requestDAO;
    }

    private ProjectDAO getProjectDAO() {
        if (projectDAO == null) {
            projectDAO = new ProjectDAOImpl();
        }
        return projectDAO;
    }

    private SampleDAO getSampleDAO() {
        if (sampleDAO == null) {
            sampleDAO = new SampleDAOImpl();
        }
        return sampleDAO;
    }

    private BiobankDAO getBiobankDAO() {
        if (biobankDAO == null) {
            biobankDAO = new BiobankDAOImpl();
        }
        return biobankDAO;
    }


    public Request create(Request request, Long projectId, Long sampleId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        getRequestDAO().create(request, em);

        Sample sampleDB = getSampleDAO().get(sampleId, em);
        Project projectDB = getProjectDAO().get(projectId, em);

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
        getRequestDAO().remove(request, em);
        em.getTransaction().commit();
        em.close();
    }

    public void remove(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Request requestDB = getRequestDAO().get(id, em);
        if (requestDB != null) {
            getRequestDAO().remove(requestDB, em);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Request update(Request request) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Request requestDB = getRequestDAO().get(request.getId(), em);
        if (requestDB == null) {
            em.close();
            return null;
        }
        if (request.getRequestState() != null) requestDB.setRequestState(request.getRequestState());

        getRequestDAO().update(requestDB, em);
        em.getTransaction().commit();
        em.close();
        return requestDB;
    }

    public List<Request> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Request> requests = getRequestDAO().getAll(em);
        em.getTransaction().commit();
        em.close();
        return requests;
    }

    public Request getById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Request request = getRequestDAO().get(id, em);
        em.getTransaction().commit();
        em.close();
        return request;
    }

    public List<Request> getAllByProject(Long projectId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Project projectDB = getProjectDAO().get(projectId, em);
        List<Request> requests = getRequestDAO().getAll(em);
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

        List<Request> requests = getRequestDAO().getAll(em);
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

        List<Request> requests = getRequestDAO().getAll(em);
        Biobank biobankDB = getBiobankDAO().get(biobankId, em);
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
        Request requestDB = getRequestDAO().get(requestId, em);
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
        Biobank biobankDB = getBiobankDAO().get(biobankId, em);

        if (biobankDB == null) {
            em.close();
            return null;
        }

        List<Request> requests = getRequestDAO().getAll(em);
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
