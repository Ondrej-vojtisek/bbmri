package bbmri.serviceImpl;

import bbmri.dao.*;
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
    private RequestDao requestDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private SampleDao sampleDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private RequestGroupDao requestGroupDao;

    public RequestGroup create(RequestGroup requestGroup) {
        try {
            requestGroupDao.create(requestGroup);
            return requestGroup;

        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public RequestGroup create(List<Request> requests, Long projectId) {
        try {
            Project projectDB = projectDao.get(projectId);
            if (projectDB == null) {
                return null;
            }
            Date created = new Date();
            RequestGroup requestGroup = new RequestGroup();
            requestGroup.setCreated(created);
            requestGroup.setLastModification(created);
            requestGroup.setRequestState(RequestState.NEW);

            if (requests == null) {
                requestGroupDao.create(requestGroup);
                return requestGroup;
            }
            if (requests.isEmpty()) {
                requestGroupDao.create(requestGroup);
                return requestGroup;
            }


            Request firstRequestDB = requestDao.get(requests.get(0).getId());
            Biobank biobankDB = biobankDao.get(firstRequestDB.getSample().getBiobank().getId());

            requestGroup.getRequests().add(firstRequestDB);
            requestGroup.setProject(projectDB);
            requestGroup.setBiobank(biobankDB);
            requestGroup.setCreated(created);
            requestGroup.setLastModification(created);
            requestGroup.setRequestState(RequestState.NEW);
            requestGroupDao.create(requestGroup);



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
                    Biobank biobankDBnew = biobankDao.get(actualRequest.getSample().getBiobank().getId());
                    requestGroupNew.setBiobank(biobankDBnew);
                    requestGroupNew.setCreated(created);
                    requestGroupNew.setLastModification(created);
                    requestGroupNew.setRequestState(RequestState.NEW);

                    Request requestDBnew = requestDao.get(actualRequest.getId());
                    requestGroupNew.getRequests().add(requestDBnew);

                    requestGroupDao.create(requestGroupNew);
                    rgMap.put(requestGroupNew.getBiobank().getId(), requestGroupNew);
                }
            }

            Iterator it = rgMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                RequestGroup requestGroupItem = (RequestGroup) entry.getValue();

                RequestGroup requestGroupDB = requestGroupDao.get(requestGroupItem.getId());
                for (int i = 0; i < requestGroupItem.getRequests().size(); i++) {
                    Request request = requestGroupItem.getRequests().get(i);
                    request.setRequestGroup(requestGroupDB);
                    requestDao.update(request);
                }
                requestGroupDao.update(requestGroupDB);
                it.remove(); // avoids a ConcurrentModificationException
                return requestGroup;
            }

        } catch (DataAccessException ex) {
            throw ex;
        }
        return null;
    }

    public void remove(RequestGroup requestGroup) {
        try {
            requestGroupDao.remove(requestGroup);
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void remove(Long id) {
        try {
            RequestGroup requestGroupDB = requestGroupDao.get(id);
            if (requestGroupDB != null) {
                requestGroupDao.remove(requestGroupDB);
            }
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public RequestGroup update(RequestGroup requestGroup) {
        try {
            RequestGroup requestGroupDB = requestGroupDao.get(requestGroup.getId());
            if (requestGroupDB == null) {
                return null;
            }
            if (requestGroup.getBiobank() != null) {
                requestGroupDB.setBiobank(requestGroup.getBiobank());
            }
            if (requestGroup.getCreated() != null) {
                requestGroupDB.setCreated(requestGroup.getCreated());
            }
            if (requestGroup.getLastModification() != null) {
                requestGroupDB.setLastModification(requestGroup.getLastModification());
            }
            if (requestGroup.getProject() != null) {
                requestGroupDB.setProject(requestGroup.getProject());
            }
            if (requestGroup.getRequests() != null) {
                requestGroupDB.setRequests(requestGroup.getRequests());
            }
            if (requestGroup.getRequestState() != null) {
                requestGroupDB.setRequestState(requestGroup.getRequestState());
            }
            requestGroupDao.update(requestGroupDB);
            return requestGroupDB;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<RequestGroup> all() {
        try {
            return requestGroupDao.all();
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public RequestGroup get(Long id) {
        try {
            return requestGroupDao.get(id);
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public Integer count() {
        try {
            return requestGroupDao.count();
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<RequestGroup> getByProject(Long projectId) {
        try {
            Project projectDB = projectDao.get(projectId);
            if (projectDB == null) {
                return null;
            }

            List<RequestGroup> allRequestGroups = requestGroupDao.all();
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
            if(biobankId == null){
                return null;
            }
            Biobank biobankDB = biobankDao.get(biobankId);
            if (biobankDB == null) {
                return null;
            }

            List<RequestGroup> allRequestGroups = requestGroupDao.all();
            List<RequestGroup> results = new ArrayList<RequestGroup>();


            for (RequestGroup requestGroup : allRequestGroups) {
                if (requestGroup.getBiobank() == null) {
                    continue;
                } else if (requestGroup.getBiobank().equals(biobankDB)) {
                    results.add(requestGroup);
                }
            }

            return results;

        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<RequestGroup> getByBiobankAndState(Long biobankId, RequestState requestState) {
        try {
            List<RequestGroup> allRequestGroups = requestGroupDao.all();
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
            RequestGroup requestGroupDB = requestGroupDao.get(requestGroupId);
            requestGroupDB.setRequestState(requestState);
            requestGroupDao.update(requestGroupDB);
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Request> getRequestsByRequestGroup(Long id) {
        RequestGroup requestGroupDB = requestGroupDao.get(id);
        if (requestGroupDB == null) {
            return null;
        }
        List<Request> results = new ArrayList<Request>();
        List<Request> requests = requestDao.all();


        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getRequestGroup() != null) {
                if (requests.get(i).getRequestGroup().equals(requestGroupDB)) {
                    results.add(requests.get(i));
                }
            }
        }
        return results;
    }
}
