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

    void create(Sample sample);

    void remove(Sample sample);

    void update(Sample sample);

    Sample get(Long id);

    List<Sample> getAll();

    List<Sample> getSelected(String query);
}
