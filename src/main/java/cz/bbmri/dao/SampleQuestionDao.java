package cz.bbmri.dao;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.SampleQuestion;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:33
 * To change this template use File | Settings | File Templates.
 */
public interface SampleQuestionDao extends BasicDao<SampleQuestion> {

    // TODO: napsat test
    List<SampleQuestion> getByBiobankAndProcessed(Biobank biobank, boolean processed);

}
