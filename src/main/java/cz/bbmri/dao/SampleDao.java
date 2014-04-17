package cz.bbmri.dao;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.sample.Tissue;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */
public interface SampleDao extends BasicBiobankDao<Sample>{

    List<Sample> getSelected(Sample question, Biobank biobank, Patient patient, boolean lts);

    void create(Tissue tissue);

    Sample getByInstitutionalId(String id);

}
