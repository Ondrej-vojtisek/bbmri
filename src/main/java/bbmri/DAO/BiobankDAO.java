package bbmri.dao;

import bbmri.entities.Biobank;
import bbmri.entities.User;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankDao extends BasicDao<Biobank> {

    Biobank getBiobankByName(String name);

}
