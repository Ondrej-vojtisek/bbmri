package bbmri.service;

import bbmri.entities.Request;
import bbmri.entities.RequestGroup;
import bbmri.entities.RequestState;

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

    List<RequestGroup> getByProject(Long projectId);

    List<RequestGroup> getByBiobank(Long biobankId);

    List<RequestGroup> getByBiobankAndState(Long biobankId, RequestState requestState);

    void changeRequestState(Long requestGroupId, RequestState requestState);

    List<Request> getRequestsByRequestGroup(Long requestGroupId);
}
