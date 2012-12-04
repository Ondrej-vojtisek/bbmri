package bbmri.DAO;

import bbmri.entities.Sample;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 7.11.12
 * Time: 0:16
 * To change this template use File | Settings | File Templates.
 */
public interface SampleDAO {

    public void create(Sample sample, EntityManager em);

    public void remove(Sample sample, EntityManager em);

    public void update(Sample sample, EntityManager em);

    public Sample get(Long id, EntityManager em);

    public List<Sample> getAll(EntityManager em);

    public List<Sample> getSelected(EntityManager em, String query);
}
