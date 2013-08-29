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
public class RequestGroupServiceImpl extends BasicServiceImpl implements RequestGroupService {

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

    public RequestGroup createTEST(Long projectId) {
        RequestGroup requestGroup = null;
        if (projectId == null) {
            requestGroup = initiate(null);
        } else {
            Project projectDB = projectDao.get(projectId);
        }

        Project projectDB = projectDao.get(projectId);
        if (projectDB == null) {
            return null;
            // TODO: exception
        }
        requestGroupDao.create(requestGroup);
        return requestGroup;
    }

    private RequestGroup initiate(Date created) {
        RequestGroup requestGroup = new RequestGroup();
        if (created == null) {
            created = new Date();
        }
        requestGroup.setCreated(created);
        requestGroup.setLastModification(created);
        requestGroup.setRequestState(RequestState.NEW);
        return requestGroup;
    }

    public void create(List<Request> requests, Long projectId) {
        notNull(projectId);
        notNull(requests);

        if (requests.isEmpty()) {
            return;
            // TODO: exception
        }

        Project projectDB = projectDao.get(projectId);
        if (projectDB == null) {
            return;
            // TODO: exception
        }
        RequestGroup requestGroup = initiate(null);

        Request firstRequestDB = requestDao.get(requests.get(0).getId());
        Biobank biobankDB = biobankDao.get(firstRequestDB.getSample().getBiobank().getId());

        requestGroup.getRequests().add(firstRequestDB);
        requestGroup.setProject(projectDB);
        requestGroup.setBiobank(biobankDB);
        requestGroupDao.create(requestGroup);

         /* The point is to create one RequestGroup for each biobank. So if requests contains samples from more
            biobanks than it creates equal amount of RequestGroup using HashMap with biobank as key
         */

        Map<Long, RequestGroup> rgMap = new HashMap<Long, RequestGroup>();

        rgMap.put(requestGroup.getBiobank().getId(), requestGroup);

        List<Request> subList = requests.subList(1, requests.size());

        for (Request request : subList) {

            /* The biobank exists in map so we can easily add this actual request to requestGroup */
            if (rgMap.containsKey(request.getSample().getBiobank().getId())) {
                rgMap.get(request.getSample().getBiobank().getId()).getRequests().add(request);
            } else {
                RequestGroup requestGroupNew = initiate(requestGroup.getCreated());
                requestGroupNew.setProject(projectDB);

                Biobank biobankDBnew = biobankDao.get(request.getSample().getBiobank().getId());
                requestGroupNew.setBiobank(biobankDBnew);

                Request requestDBnew = requestDao.get(request.getId());
                requestGroupNew.getRequests().add(requestDBnew);

                requestGroupDao.create(requestGroupNew);
                rgMap.put(requestGroupNew.getBiobank().getId(), requestGroupNew);
            }
        }

        /* Now we have requestGroup for each biobank */
        Iterator it = rgMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            RequestGroup requestGroupItem = (RequestGroup) entry.getValue();

            /*Now we must set that request belongs to requestGroup - the second side of relationship*/
            RequestGroup requestGroupDB = requestGroupDao.get(requestGroupItem.getId());
            for (Request request : requestGroupItem.getRequests()) {
                request.setRequestGroup(requestGroupDB);
                requestDao.update(request);
            }
            requestGroupDao.update(requestGroupDB);

        }
        it.remove(); // avoids a ConcurrentModificationException
    }

    public void remove(Long id) {
        notNull(id);

        RequestGroup requestGroupDB = requestGroupDao.get(id);
        if (requestGroupDB == null) {
            return;
            //TODO: exception
        }

        Biobank biobankDB = biobankDao.get(requestGroupDB.getBiobank().getId());
        if(biobankDB == null){
                    return;
                    // TODO: exception
                }

            biobankDB.getRequestGroups().remove(requestGroupDB);
            biobankDao.update(biobankDB);
            requestGroupDB.setBiobank(null);

        List<Request> requests = requestGroupDB.getRequests();

        if(requests == null){
            return;
            // TODO: exception
        }
        for(Request request : requests){
            requestDao.remove(request);
        }

        Project projectDB = projectDao.get(requestGroupDB.getProject().getId());
        if(projectDB == null){
            return;
            // TODO: exception
        }
        projectDB.getRequestGroups().remove(requestGroupDB);

        requestGroupDao.remove(requestGroupDB);


    }

    public RequestGroup update(RequestGroup requestGroup) {
    notNull(requestGroup);
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
    }

    public List<RequestGroup> all() {
            return requestGroupDao.all();

    }

    public RequestGroup get(Long id) {
        notNull(id);
            return requestGroupDao.get(id);
    }

    public Integer count() {
            return requestGroupDao.count();
    }

    public List<RequestGroup> getByProject(Long projectId) {
            notNull(projectId);
            Project projectDB = projectDao.get(projectId);
            if (projectDB == null) {
                return null;
                //TODO: exception
            }

            List<RequestGroup> allRequestGroups = requestGroupDao.all();
            List<RequestGroup> results = new ArrayList<RequestGroup>();
            for (RequestGroup requestGroup : allRequestGroups) {
                if (requestGroup.getProject().equals(projectDB)) {
                    results.add(requestGroup);
                }
            }
            return results;
    }

    public List<RequestGroup> getByBiobank(Long biobankId) {
        notNull(biobankId);

            Biobank biobankDB = biobankDao.get(biobankId);
            if (biobankDB == null) {
                return null;
                // TODO: exception
            }

            List<RequestGroup> allRequestGroups = requestGroupDao.all();
            List<RequestGroup> results = new ArrayList<RequestGroup>();

            for (RequestGroup requestGroup : allRequestGroups) {
               if (requestGroup.getBiobank().equals(biobankDB)) {
                    results.add(requestGroup);
                }
            }

            return results;
    }

    public List<RequestGroup> getByBiobankAndState(Long biobankId, RequestState requestState) {
            List<RequestGroup> allRequestGroups = requestGroupDao.all();
            List<RequestGroup> results = new ArrayList<RequestGroup>();
            for (RequestGroup requestGroup : allRequestGroups) {
                if (requestGroup.getBiobank().getId().equals(biobankId) &&
                        requestGroup.getRequestState().equals(requestState)) {
                    results.add(requestGroup);
                }
            }
            return results;
    }

    public void changeRequestState(Long requestGroupId, RequestState requestState) {
        notNull(requestGroupId);
        notNull(requestState);

            RequestGroup requestGroupDB = requestGroupDao.get(requestGroupId);
            requestGroupDB.setRequestState(requestState);
            requestGroupDao.update(requestGroupDB);
    }

    public List<Request> getRequestsByRequestGroup(Long requestGroupId) {
        notNull(requestGroupId);

        RequestGroup requestGroupDB = requestGroupDao.get(requestGroupId);
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
