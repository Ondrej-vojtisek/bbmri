package cz.bbmri.service;

import cz.bbmri.entities.Biobank;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankService {


    List<Biobank> all();

    Integer count();

    Biobank get(Long id);

    public List<Biobank> allOrderedBy(String orderByParam, boolean desc);

    Biobank getBiobankByAbbreviation(String abbreviation);

    boolean create(Biobank biobank, ValidationErrors errors);

    boolean update(Biobank biobank, ValidationErrors errors, Long loggedUserId);

    boolean remove(Long biobankId, ValidationErrors errors, Long loggedUserId);

}
