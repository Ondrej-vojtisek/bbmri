package cz.bbmri.dao;

import cz.bbmri.entities.Biobank;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankDao extends BasicDao<Biobank, Long> {

    Biobank getBiobankByAbbreviation(String abbreviation);

}
