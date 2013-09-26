package bbmri.entities;

import javax.persistence.*;
import java.io.Serializable;



/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 18.9.13
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "biobankAdministrator")
@Entity
public class BiobankAdministrator implements Serializable {

    private static final long serialVersionUID = 1L;

     @Id
     @GeneratedValue(strategy = GenerationType.TABLE)
     @Column(name = "ID", nullable = false)
     private Long id;

    @Enumerated(EnumType.STRING)
    private AdministratorRole administratorRole;

    @ManyToOne
    private Biobank biobank;

    @OneToOne(mappedBy = "biobankAdministrator")
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

    public AdministratorRole getAdministratorRole() {
        return administratorRole;
    }

    public void setAdministratorRole(AdministratorRole administratorRole) {
        this.administratorRole = administratorRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BiobankAdministrator that = (BiobankAdministrator) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "BiobankAdministrator{" +
                "administratorRole=" + administratorRole +
                ", biobank=" + biobank +
                ", user=" + user +
                '}';
    }

}
