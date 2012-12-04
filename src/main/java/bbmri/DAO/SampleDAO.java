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

    void create(Sample sample, EntityManager em);

    void remove(Sample sample, EntityManager em);

    void update(Sample sample, EntityManager em);

    Sample get(Long id, EntityManager em);

    List<Sample> getAll(EntityManager em);

    List<Sample> getSelected(EntityManager em, String query);
}
