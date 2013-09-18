package bbmri.dao;

import bbmri.entities.Biobank;
import bbmri.entities.Project;
import bbmri.entities.SampleQuestion;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:33
 * To change this template use File | Settings | File Templates.
 */
public interface SampleQuestionDao extends BasicDao<SampleQuestion> {

    List<SampleQuestion> getSelected(String query);

    List<SampleQuestion> getByBiobank(Biobank biobank);

    // TODO: napsat test
    List<SampleQuestion> getByBiobankAndProcessed(Biobank biobank, boolean processed);

    // TODO: napsat test
    public List<SampleQuestion> getByProject(Project project);
    
}
