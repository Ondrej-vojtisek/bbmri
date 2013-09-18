package bbmri.service;

import bbmri.entities.Request;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */
public interface RequestService extends BasicService<Request>{

     Request create(Long sampleId);

     Request create(Long sampleId, Integer numOfRequested);

     Request eagerGet(Long id, boolean requestGroup, boolean sample);
}
