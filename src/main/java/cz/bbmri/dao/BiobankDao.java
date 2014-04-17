package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Biobank;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */
public interface BiobankDao extends BasicDao<Biobank> {

    Biobank getBiobankByAbbreviation(String abbreviation);

}
