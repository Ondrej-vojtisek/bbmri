package cz.bbmri.entities.infrastructure;

import cz.bbmri.entities.Biobank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 11.2.14
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "Infrastructure")
@Entity
public class Infrastructure implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @OneToOne
    private Biobank biobank;

    @OneToMany(mappedBy = "infrastructure")
    private Set<Container> containers = new HashSet<Container>();

    @OneToMany(mappedBy = "infrastructure")
    private Set<StandaloneBox> standaloneBoxes = new HashSet<StandaloneBox>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public Set<Container> getContainers() {
        return containers;
    }

    public void setContainers(Set<Container> containers) {
        this.containers = containers;
    }

    public Set<StandaloneBox> getStandaloneBoxes() {
        return standaloneBoxes;
    }

    public void setStandaloneBoxes(Set<StandaloneBox> standaloneBoxes) {
        this.standaloneBoxes = standaloneBoxes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Infrastructure)) return false;

        Infrastructure that = (Infrastructure) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Infrastructure{" +
                "id=" + id +
                '}';
    }
}
