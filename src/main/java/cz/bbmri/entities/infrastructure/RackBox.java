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
public class RackBox extends Box {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Rack rack;

    public RackBox() {
    }

    public Rack getRack() {
        return rack;
    }

    public void setRack(Rack rack) {
        this.rack = rack;
    }

//    public String getType() {
//        return "RackBox";
//    }
}
