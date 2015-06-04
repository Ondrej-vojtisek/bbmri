package cz.bbmri.dao;

import cz.bbmri.entity.Diagnosis;

import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface DiagnosisDAO extends AbstractCompositeDAO<Diagnosis> {

    List<String> getUniqueDiagnosis();
}
