package cz.bbmri.service.impl;

import cz.bbmri.dao.*;
import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.service.RequestGroupService;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service("requestGroupService")
public class RequestGroupServiceImpl extends BasicServiceImpl implements RequestGroupService {

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private RequestGroupDao requestGroupDao;


    public void create(List<Request> requests, Long projectId) {

    }

    public boolean remove(Long id) {
      return false;
    }

    public RequestGroup update(RequestGroup requestGroup) {
            return null ;

    }

    @Transactional(readOnly = true)
    public List<RequestGroup> all() {
        return requestGroupDao.all();
    }

    @Transactional(readOnly = true)
    public RequestGroup get(Long id) {
        notNull(id);
        return requestGroupDao.get(id);
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return requestGroupDao.count();
    }

    @Transactional(readOnly = true)
    public List<RequestGroup> allOrderedBy(String orderByParam, boolean desc){
        return requestGroupDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<RequestGroup> nOrderedBy(String orderByParam, boolean desc, int number){
        return requestGroupDao.nOrderedBy(orderByParam, desc, number);
    }
}
