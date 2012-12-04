package bbmri.DAO;

import bbmri.entities.Researcher;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */
public interface ResearcherDAO {

    void create(Researcher researcher, EntityManager em);

    void remove(Researcher researcher, EntityManager em);

    void update(Researcher researcher, EntityManager em);

    List<Researcher> getAll(EntityManager em);

    Researcher get(Long id, EntityManager em);

}
