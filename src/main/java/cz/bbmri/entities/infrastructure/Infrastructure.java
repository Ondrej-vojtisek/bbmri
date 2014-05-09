package cz.bbmri.entities.infrastructure;

import cz.bbmri.entities.Biobank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Infrastructure represents physical structure of biobank repository. There are basically two types of objects in repository
 * dewar containers and low temperature "fridges". Container (dewar) is structured into racks.
 *
 * Fridges contain boxes of samples - these boxes are (in context of application) called standalone boxes.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Table
@Entity
public class Infrastructure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @OneToOne
    private Biobank biobank;

    /**
     * Representing dewar container which can be found in repository
     */
    @OneToMany(mappedBy = "infrastructure")
    private Set<Container> containers = new HashSet<Container>();

    /**
     * Representing any boxes that are not situated in dewar containers. It is expected that these boxes are unique
     * identified by its name.
     */
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
