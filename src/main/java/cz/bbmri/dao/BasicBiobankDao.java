package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Biobank;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */
public interface BasicBiobankDao<T> extends BasicDao<T> {

    List<T> getSorted(Biobank biobank, String orderByParam, boolean desc);
}
