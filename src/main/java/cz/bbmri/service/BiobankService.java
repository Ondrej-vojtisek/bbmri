package cz.bbmri.service;

import cz.bbmri.entities.Biobank;
import cz.bbmri.service.simpleService.All;
import cz.bbmri.service.simpleService.Count;
import cz.bbmri.service.simpleService.Get;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface BiobankService extends All<Biobank>, Get<Biobank>, Count {

    public List<Biobank> allOrderedBy(String orderByParam, boolean desc);

    Biobank getBiobankByAbbreviation(String abbreviation);

    boolean create(Biobank biobank, ValidationErrors errors);

    boolean update(Biobank biobank, ValidationErrors errors, Long loggedUserId);

    boolean remove(Long biobankId, ValidationErrors errors, Long loggedUserId);

}
