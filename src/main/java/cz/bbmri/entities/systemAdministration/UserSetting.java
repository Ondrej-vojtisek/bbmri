package cz.bbmri.entities.systemAdministration;

import cz.bbmri.entities.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 19.3.14
 * Time: 11:42
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class UserSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @OneToOne
    private User user;

    private int numberOfRecordsPerPage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumberOfRecordsPerPage() {
        return numberOfRecordsPerPage;
    }

    public void setNumberOfRecordsPerPage(int numberOfRecordsPerPage) {
        this.numberOfRecordsPerPage = numberOfRecordsPerPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSetting that = (UserSetting) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "UserSetting{" +
                "id=" + id +
                ", user=" + user +
                ", numberOfRecordsPerPage=" + numberOfRecordsPerPage +
                '}';
    }
}
