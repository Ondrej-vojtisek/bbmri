package bbmri.service;

import bbmri.entities.Biobank;
import bbmri.entities.Researcher;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankService {

    public Biobank create(Biobank biobank, Researcher researcher);

    public void remove(Long id);

    public Biobank update(Biobank biobank);

    public List<Biobank> getAll();

}
