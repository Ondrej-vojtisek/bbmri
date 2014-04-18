package cz.bbmri.dao;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Sample;

import java.util.List;

/**
 * Interface to handle instances of Sample stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface SampleDao extends BasicBiobankDao<Sample> {

    /**
     * Return all samples similar to given parameters.
     *
     * @param question - search parameters based on sample classification
     * @param biobank  - in which biobank is sample located
     * @param patient  - specify patient (age, sex, ...)
     * @param lts      - Long term or short term - true means LTS, false STS
     * @return list of samples by given criteria, sorted by relevance
     */
    List<Sample> getSelected(Sample question, Biobank biobank, Patient patient, boolean lts);

    /**
     * Search sample by its institutional id.
     *
     * @param id - institutional id
     * @return sample with given id or null
     */
    Sample getByInstitutionalId(String id);

}
