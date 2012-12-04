package bbmri.DAO;

import bbmri.entities.Biobank;
import bbmri.entities.Researcher;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankDAO {

    void create(Biobank biobank, EntityManager em);

    void remove(Biobank biobank, EntityManager em);

    void update(Biobank biobank, EntityManager em);

    List<Biobank> getAll(EntityManager em);

    Biobank get(Long id, EntityManager em);
}
