package bbmri.service;

import bbmri.entities.Project;
import bbmri.entities.Researcher;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public interface ResearcherService {

    public Researcher create(Researcher researcher);

    public void remove(Researcher researcher);

    public void remove(Long id);

    public Researcher update(Researcher researcher);

    public List<Researcher> getAll();

}
