package cz.bbmri.dao;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.RequestGroup;
import cz.bbmri.entities.enumeration.RequestState;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 6.2.13
 * Time: 12:16
 * To change this template use File | Settings | File Templates.
 */
public interface RequestGroupDao extends BasicDao<RequestGroup> {

    List<RequestGroup> getByBiobankAndState(Biobank biobank, RequestState requestState);

}