package cz.bbmri.entities.infrastructure;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Specific version of Box (extending "basic" Box) describing only boxes located in nonstructured container
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Entity
public class StandaloneBox extends Box {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Infrastructure infrastructure;

    public Infrastructure getInfrastructure() {
        return infrastructure;
    }

    public void setInfrastructure(Infrastructure infrastructure) {
        this.infrastructure = infrastructure;
    }

//    public String getType(){
//         return "StandaloneBox";
//     }
}
