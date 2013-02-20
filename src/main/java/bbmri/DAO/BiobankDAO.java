package bbmri.DAO;

import bbmri.entities.Biobank;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankDAO {

    void create(Biobank biobank);

    void remove(Biobank biobank);

    void update(Biobank biobank);

    List<Biobank> getAll();

    Biobank get(Long id);

    Integer getCount();
}
