package bbmri.service;

import bbmri.entities.Request;
import bbmri.entities.RequestState;
import bbmri.entities.Sample;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */
public interface RequestService {

    public Request create(Request request, Long projectId, Long sampleId);

    public void remove(Request request);

    public void remove(Long id);

    public Request update(Request request);

    public List<Request> getAll();

    public Request getById(Long id);

    public List<Request> getAllNew();

    public Request changeRequestState(Long requestId, RequestState requestState);

    public List<Request> getAllNewByBiobank(Long biobankId);

    public List<Sample> getAllReleasableSamplesByBiobank(Long biobankId);
}
