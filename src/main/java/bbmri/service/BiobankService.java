package bbmri.service;

import bbmri.entities.Biobank;
import bbmri.entities.Sample;
import bbmri.entities.User;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankService {

    Biobank create(Biobank biobank, Long administratorId);

    void remove(Long id);

    Biobank update(Biobank biobank);

    List<Biobank> getAll();

    List<Sample> getAllSamples(Long biobankId);

    Integer getCount();

    User removeAdministratorFromBiobank(Long userId, Long biobankId);

    List<User> getAllAdministrators(Long biobankId);

    Biobank changeOwnership(Long biobankId, Long newOwnerId);

    User assignAdministrator(Long userId, Long biobankId);

    Biobank getById(Long id);

}
