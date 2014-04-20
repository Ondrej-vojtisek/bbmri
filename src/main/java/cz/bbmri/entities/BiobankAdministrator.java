package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.Permission;

import javax.persistence.*;
import java.io.Serializable;


/**
 * Relationship between biobank and user
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Table
@Entity
public class BiobankAdministrator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    /**
     * Permission of user to biobank. Defines what he is authorized to do.
     */
    @Enumerated(EnumType.STRING)
    private Permission permission;

    @ManyToOne
    private Biobank biobank;

    @ManyToOne
    private User user;

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


    Long getId() {
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
                "permission=" + permission +
                ", biobank=" + biobank +
                ", user=" + user +
                '}';
    }


}
