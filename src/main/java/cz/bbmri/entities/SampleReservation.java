package cz.bbmri.entities;

import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 24.2.14
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
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
