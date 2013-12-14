package cz.bbmri.service;

import cz.bbmri.entities.Request;

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
