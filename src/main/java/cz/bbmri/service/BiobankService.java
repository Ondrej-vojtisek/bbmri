package cz.bbmri.service;

import cz.bbmri.entities.Biobank;
import cz.bbmri.service.simpleService.All;
import cz.bbmri.service.simpleService.AllOrderedBy;
import cz.bbmri.service.simpleService.Count;
import cz.bbmri.service.simpleService.Get;
import net.sourceforge.stripes.validation.ValidationErrors;

/**
 * API for handling Biobanks
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface BiobankService extends All<Biobank>, AllOrderedBy<Biobank>, Get<Biobank>, Count {

    /**
     * Return biobank by its abbreviation
     *
     * @param abbreviation
     * @return instance of biobank with given abbreviation or null
     */
    Biobank getBiobankByAbbreviation(String abbreviation);

    /**
     * Store new instance of biobank into DB
     *
     * @param biobank - new instance which will be stored in DB
     * @param errors  - in case of error, error messages will be stored into errors
     * @return true/false
     */
    boolean create(Biobank biobank, ValidationErrors errors);

    /**
     * Update biobank record. There must an instance with the same ID (as given biobank) stored in DB. All non-null
     * fields of given param will be updated. Only changeable attributes are being changed.
     *
     * @param biobank      - instance of biobank to be changed
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - ID of event initiator. Notification about new file will be sent to project team except
     *                     initiator (BROADCAST)
     * @return true/false
     */
    boolean update(Biobank biobank, ValidationErrors errors, Long loggedUserId);

    /**
     * Remove biobank identified by given ID. Everything associated with the biobank (files, relationship, samples, ...)
     * will be also deleted.
     *
     * @param biobankId    - ID of biobank which will be removed
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - ID of event initiator. Notification about new file will be sent to project team except
     *                     initiator (BROADCAST)
     * @return true/false
     */
    boolean remove(Long biobankId, ValidationErrors errors, Long loggedUserId);

}
