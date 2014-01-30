package cz.bbmri.service;

import cz.bbmri.entities.SampleRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:44
 * To change this template use File | Settings | File Templates.
 */
public interface SampleRequestService extends BasicService<SampleRequest>{

    SampleRequest create(SampleRequest sampleRequest, Long biobankId, Long projectId);

    SampleRequest withdraw(SampleRequest sampleRequest, Long biobankId);

}
