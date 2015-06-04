package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.CountryDAO;
import cz.bbmri.entity.Country;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("countryDAO")
@Transactional
public class CountryDAOImpl extends GenericDAOImpl<Country> implements CountryDAO {

    public Country get(Integer id) {
                      return (Country) getCurrentSession().get(Country.class, id);
                  }

}
