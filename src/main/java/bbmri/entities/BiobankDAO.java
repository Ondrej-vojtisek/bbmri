package bbmri.entities;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankDAO {

    public void addBiobank(Biobank biobank);

    public void removeBiobank(Biobank biobank);

    public void updateBiobank(Biobank biobank);

    public List<Biobank> getAllBiobanks();

}
