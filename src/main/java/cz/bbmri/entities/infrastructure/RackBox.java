package cz.bbmri.entities.infrastructure;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Specific version of Box (extending "basic" Box) describing only boxes located inside rack.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
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
