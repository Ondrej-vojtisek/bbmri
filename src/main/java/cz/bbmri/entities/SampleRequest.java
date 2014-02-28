package cz.bbmri.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:22
 * To change this template use File | Settings | File Templates.
 */

@Entity
//@DiscriminatorValue("SampleRequest")
public class SampleRequest extends SampleQuestion {

    @ManyToOne
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
