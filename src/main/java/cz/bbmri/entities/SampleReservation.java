package cz.bbmri.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 24.2.14
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class SampleReservation extends SampleQuestion {

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
