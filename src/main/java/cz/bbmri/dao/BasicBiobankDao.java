package cz.bbmri.dao;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Container;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.3.14
 * Time: 14:48
 * To change this template use File | Settings | File Templates.
 */
public interface BasicBiobankDao<T> extends BasicDao<T> {

    List<T> getSorted(Biobank biobank, String orderByParam, boolean desc);
}
