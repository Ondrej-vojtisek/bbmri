package cz.bbmri.entities.infrastructure;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 15.2.14
 * Time: 0:25
 * To change this template use File | Settings | File Templates.
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
