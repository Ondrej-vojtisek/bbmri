package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.Permission;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;


/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Table
@Entity
public class BiobankAdministrator implements Serializable, Comparable<BiobankAdministrator> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Permission permission;

    @ManyToOne
    private Biobank biobank;

    @ManyToOne
    private User user;

    // public BiobankAdministrator(){}

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

    public int compareTo(BiobankAdministrator compareAdministrator) {

        if (this.getId() > compareAdministrator.getId())
            return 1;
        else if (this.getId() < compareAdministrator.getId())
            return -1;
        else
            return 0;
    }

    public static Comparator<BiobankAdministrator> NameComparator
            = new Comparator<BiobankAdministrator>() {

        public int compare(BiobankAdministrator admin1, BiobankAdministrator admin2) {

            User atr1 = admin1.getUser();
            User atr2 = admin2.getUser();

            if (atr1 == null) {
                if (atr2 == null) {
                    return 0;
                } else {
                    return Integer.MIN_VALUE;
                }
            }

            if (atr2 == null) {
                return Integer.MAX_VALUE;
            }

            if (atr1.getSurname() == null) {
                if (atr2.getSurname() == null) {
                    return 0;
                } else {
                    return Integer.MIN_VALUE;
                }
            }

            if (atr2.getSurname() == null) {
                return Integer.MAX_VALUE;
            }

            return atr1.getSurname().compareTo(atr2.getSurname());
        }

    };

    public static Comparator<BiobankAdministrator> PermissionComparator
            = new Comparator<BiobankAdministrator>() {

        public int compare(BiobankAdministrator admin1, BiobankAdministrator admin2) {

            Permission atr1 = admin1.getPermission();
            Permission atr2 = admin2.getPermission();

            if (atr1 == null) {
                if (atr2 == null) {
                    return 0;
                } else {
                    return Integer.MIN_VALUE;
                }
            }

            if (atr2 == null) {
                return Integer.MAX_VALUE;
            }

            return Permission.compare(atr1, atr2);

        }

    };

}
