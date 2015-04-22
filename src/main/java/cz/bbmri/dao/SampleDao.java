package cz.bbmri.dao;

import cz.bbmri.entity.Sample;
import cz.bbmri.io.InstanceImportResult;

import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface SampleDAO extends AbstractDAO<Sample, Long> {

//    /**
//     * Return all samples similar to given parameters.
//     *
//     * @param question - search parameters based on sample classification
//     * @param biobank  - in which biobank is sample located
//     * @param patient  - specify patient (age, sex, ...)
//     * @param lts      - Long term or short term - true means LTS, false STS
//     * @return list of samples by given criteria, sorted by relevance
//     */
//    List<Sample> getSelected(Sample question, Biobank biobank, Patient patient, boolean lts);
//

    /**
     * Search sample by its institutional id.
     *
     * @param id - institutional id
     * @return sample with given id or null
     */
    Sample getByInstitutionalId(String id);

    InstanceImportResult updateWithResult(Sample sample);

}
