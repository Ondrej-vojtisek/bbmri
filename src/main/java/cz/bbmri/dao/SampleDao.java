package cz.bbmri.dao;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.sample.Tissue;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 7.11.12
 * Time: 0:16
 * To change this template use File | Settings | File Templates.
 */
public interface SampleDao extends BasicDao<Sample>{

    List<Sample> getSelected(Sample question, Biobank biobank);

    List<Sample> getSelected(Sample question, Biobank biobank, Patient patient, boolean lts);

    void create(Tissue tissue);

}
