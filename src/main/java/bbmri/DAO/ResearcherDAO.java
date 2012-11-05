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

    public void create(Researcher researcher, EntityManager em);

    public void remove(Researcher researcher, EntityManager em);

    public void update(Researcher researcher, EntityManager em);

    public List<Researcher> getAll(EntityManager em);

    public Researcher get(Long id, EntityManager em);

}
