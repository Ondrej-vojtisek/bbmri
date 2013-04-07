package bbmri.DAO;

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
public interface SampleQuestionDAO {
    
    void create(SampleQuestion sampleQuestion);
    
    void remove(SampleQuestion sampleQuestion);
    
    void update(SampleQuestion sampleQuestion);
    
    List<SampleQuestion> getAll();
    
    SampleQuestion get(Long id);
    
    Integer getCount();

    List<SampleQuestion> getSelected(String query);
    
}
