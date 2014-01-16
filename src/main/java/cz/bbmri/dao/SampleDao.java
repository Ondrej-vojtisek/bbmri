package cz.bbmri.dao;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Sample;

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

}
