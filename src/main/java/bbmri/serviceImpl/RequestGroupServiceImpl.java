package bbmri.serviceImpl;

import bbmri.DAO.*;
import bbmri.entities.*;
import bbmri.service.RequestGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 6.2.13
 * Time: 12:03
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service
public class RequestGroupServiceImpl implements RequestGroupService {

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private SampleDAO sampleDAO;

    @Autowired
    private BiobankDAO biobankDAO;

    @Autowired
    private RequestGroupDAO requestGroupDAO;

    public RequestGroup create(RequestGroup requestGroup) {
        try {
            requestGroupDAO.create(requestGroup);
            return requestGroup;

        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void create(List<Request> requests, Long projectId) {
        try {
            Project projectDB = projectDAO.get(projectId);
            if (projectDB == null) {
                return;
            }
            if (requests == null) {
                return;
            }
            if (requests.isEmpty()) {
                return;
            }
            Date created = new Date();
            RequestGroup requestGroup = new RequestGroup();

            Request firstRequestDB = requestDAO.get(requests.get(0).getId());
            Biobank biobankDB = biobankDAO.get(firstRequestDB.getSample().getBiobank().getId());

            requestGroup.getRequests().add(firstRequestDB);
            requestGroup.setProject(projectDB);
            requestGroup.setBiobank(biobankDB);
            requestGroup.setCreated(created);
            requestGroup.setLastModification(created);
            requestGroup.setRequestState(RequestState.NEW);
            requestGroupDAO.create(requestGroup);

            /* The point is to create one RequestGroup for each biobank. So if requests contains samples from more
            biobanks than it creates equal amount of RequestGroup using HashMap with biobank as key
            */
            Map<Long, RequestGroup> rgMap = new HashMap<Long, RequestGroup>();

            rgMap.put(requestGroup.getBiobank().getId(), requestGroup);

            for (int i = 1; i < requests.size(); i++) {
                Request actualRequest = requests.get(i);

                if (rgMap.containsKey(actualRequest.getSample().getBiobank().getId())) {
                    rgMap.get(actualRequest.getSample().getBiobank().getId()).getRequests().add(requests.get(i));
                } else {
                    RequestGroup requestGroupNew = new RequestGroup();
                    requestGroupNew.setProject(projectDB);
                    Biobank biobankDBnew = biobankDAO.get(actualRequest.getSample().getBiobank().getId());
                    requestGroupNew.setBiobank(biobankDBnew);
                    requestGroupNew.setCreated(created);
                    requestGroupNew.setLastModification(created);
                    requestGroupNew.setRequestState(RequestState.NEW);

                    Request requestDBnew = requestDAO.get(actualRequest.getId());
                    requestGroupNew.getRequests().add(requestDBnew);

                    requestGroupDAO.create(requestGroupNew);
                    rgMap.put(requestGroupNew.getBiobank().getId(), requestGroupNew);
                }
            }

            Iterator it = rgMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                RequestGroup requestGroupItem = (RequestGroup) entry.getValue();

                RequestGroup requestGroupDB = requestGroupDAO.get(requestGroupItem.getId());
                for (int i = 0; i < requestGroupItem.getRequests().size(); i++) {
                    Request request = requestGroupItem.getRequests().get(i);
                    request.setRequestGroup(requestGroupDB);
                    requestDAO.update(request);
                }
                requestGroupDAO.update(requestGroupDB);
                it.remove(); // avoids a ConcurrentModificationException
            }

        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void remove(RequestGroup requestGroup) {
        try {
            requestGroupDAO.remove(requestGroup);
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void remove(Long id) {
        try {
            RequestGroup requestGroupDB = requestGroupDAO.get(id);
            if (requestGroupDB != null) {
                requestGroupDAO.remove(requestGroupDB);
            }
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public RequestGroup update(RequestGroup requestGroup) {
        try {
            RequestGroup requestGroupDB = requestGroupDAO.get(requestGroup.getId());
            if (requestGroupDB == null) {
                return null;
            }
            requestGroupDAO.update(requestGroupDB);
            return requestGroupDB;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<RequestGroup> getAll() {
        try {
            return requestGroupDAO.getAll();
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public RequestGroup getById(Long id) {
        try {
            return requestGroupDAO.get(id);
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Integer getCount() {
        try {
            return requestGroupDAO.getCount();
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<RequestGroup> getByProject(Long projectId) {
        try {
            Project projectDB = projectDAO.get(projectId);
            if (projectDB == null) {
                return null;
            }

            List<RequestGroup> allRequestGroups = requestGroupDAO.getAll();
            List<RequestGroup> results = new ArrayList<RequestGroup>();
            for (int i = 0; i < allRequestGroups.size(); i++) {
                if (allRequestGroups.get(i).getProject().equals(projectDB)) {
                    results.add(allRequestGroups.get(i));
                }
            }
            return results;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<RequestGroup> getByBiobank(Long biobankId) {
        try {
            List<RequestGroup> allRequestGroups = requestGroupDAO.getAll();
            List<RequestGroup> results = new ArrayList<RequestGroup>();
            for (int i = 0; i < allRequestGroups.size(); i++) {
                if (allRequestGroups.get(i).getBiobank().getId().equals(biobankId)) {
                    results.add(allRequestGroups.get(i));
                }
            }
            return results;

        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<RequestGroup> getByBiobankAndState(Long biobankId, RequestState requestState) {
        try {
            List<RequestGroup> allRequestGroups = requestGroupDAO.getAll();
            List<RequestGroup> results = new ArrayList<RequestGroup>();
            for (int i = 0; i < allRequestGroups.size(); i++) {
                if (allRequestGroups.get(i).getBiobank().getId().equals(biobankId) &&
                        allRequestGroups.get(i).getRequestState().equals(requestState)) {
                    results.add(allRequestGroups.get(i));
                }
            }
            return results;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void changeRequestState(Long requestGroupId, RequestState requestState) {
        try {
            RequestGroup requestGroupDB = requestGroupDAO.get(requestGroupId);
            requestGroupDB.setRequestState(requestState);
            requestGroupDAO.update(requestGroupDB);
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Request> getRequestsByRequestGroup(Long id){
        RequestGroup requestGroupDB = requestGroupDAO.get(id);
        if(requestGroupDB == null){
            return null;
        }
        List<Request> results = new ArrayList<Request>();
        List<Request> requests = requestDAO.getAll();


        for(int i = 0; i < requests.size(); i++){
            if(requests.get(i).getRequestGroup().equals(requestGroupDB)){
                results.add(requests.get(i));
            }
        }
        return results;
    }
}
