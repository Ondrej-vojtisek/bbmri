package cz.bbmri.service;

import cz.bbmri.entities.Request;
import cz.bbmri.facade.exceptions.InsuficientAmountOfSamplesException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */
public interface RequestService extends BasicService<Request>{

     int createRequests(List<Long> sampleIds, Long sampleRequestId) throws InsuficientAmountOfSamplesException;

}
