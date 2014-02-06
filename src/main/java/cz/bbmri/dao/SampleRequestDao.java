package cz.bbmri.dao;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.entities.enumeration.RequestState;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:33
 * To change this template use File | Settings | File Templates.
 */
public interface SampleRequestDao extends BasicDao<SampleRequest> {

    List<SampleRequest> getByBiobankAndState(Biobank biobank, RequestState requestState);
}
