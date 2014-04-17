package cz.bbmri.entities;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Entity
public class SampleReservation extends SampleQuestion {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private User user;

    @Type(type = "timestamp")
    private Date validity;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }
}
