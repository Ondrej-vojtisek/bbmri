package cz.bbmri.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * SampleRequest - request asociated with existing project
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Entity
public class SampleRequest extends SampleQuestion {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
