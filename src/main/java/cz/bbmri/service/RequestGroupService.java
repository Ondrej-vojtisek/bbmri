package cz.bbmri.service;

import cz.bbmri.entities.Request;
import cz.bbmri.entities.RequestGroup;
import cz.bbmri.entities.enumeration.RequestState;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 6.2.13
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
public interface RequestGroupService  extends BasicService<RequestGroup>{

    void create(List<Request> requests, Long projectId);

    List<RequestGroup> getByBiobank(Long biobankId);

    List<RequestGroup> getByBiobankAndState(Long biobankId, RequestState requestState);

    void approveRequestGroup(Long requestGroupId);

    void denyRequestGroup(Long requestGroupId);

    RequestGroup eagerGet(Long id, boolean requests);
}
