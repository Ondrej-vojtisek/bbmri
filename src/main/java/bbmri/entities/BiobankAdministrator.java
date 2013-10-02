package bbmri.entities;

import bbmri.entities.enumeration.Permission;

import javax.persistence.*;
import java.io.Serializable;



/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 18.9.13
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "BiobankAdministrator")
@Entity
public class BiobankAdministrator implements Serializable {

    private static final long serialVersionUID = 1L;

     @Id
     @GeneratedValue(strategy = GenerationType.TABLE)
     @Column(name = "ID", nullable = false)
     private Long id;

    @Enumerated(EnumType.STRING)
    private Permission permission;

    @ManyToOne
    private Biobank biobank;

    @ManyToOne
    private User user;

    public BiobankAdministrator(){}

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BiobankAdministrator that = (BiobankAdministrator) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "BiobankAdministrator{" +
                "permission=" + permission +
                ", biobank=" + biobank +
                ", user=" + user +
                '}';
    }

}
