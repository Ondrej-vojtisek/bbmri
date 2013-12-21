package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.ProjectDao;
import cz.bbmri.dao.RequestDao;
import cz.bbmri.dao.SampleDao;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.Request;
import cz.bbmri.entities.Sample;
import cz.bbmri.service.RequestService;
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
@Service("requestService")
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

    public boolean remove(Long id) {
        notNull(id);
        Request requestDB = requestDao.get(id);
        if (requestDB == null) {
            return false;
        }
        requestDao.remove(requestDB);
        return true;
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

    @Transactional(readOnly = true)
    public List<Request> all() {
        return requestDao.all();
    }

    @Transactional(readOnly = true)
    public Request get(Long id) {
        notNull(id);
        return requestDao.get(id);
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return requestDao.count();
    }

    @Transactional(readOnly = true)
    public Request eagerGet(Long id, boolean requestGroup, boolean sample) {
           notNull(id);
           Request requestDB = requestDao.get(id);

           /* Not only comments - this force hibernate to load mentioned relationship from db. Otherwise it wont be accessible from presentational layer of application.*/

           if (requestGroup) {
               logger.debug("" + requestDB.getRequestGroup());
           }

           if (sample) {
               logger.debug("" + requestDB.getSample());
           }
           return requestDB;

       }

    @Transactional(readOnly = true)
    public List<Request> allOrderedBy(String orderByParam, boolean desc){
        return requestDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<Request> nOrderedBy(String orderByParam, boolean desc, int number){
        return requestDao.nOrderedBy(orderByParam, desc, number);
    }
}
