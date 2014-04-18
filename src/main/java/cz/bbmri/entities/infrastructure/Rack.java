package cz.bbmri.entities.infrastructure;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Rack is part of container - typically one stand in dewar container. It consist of boxes (RackBox).
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Table
@Entity
public class Rack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    /**
     * Rack identification from original institution
     */
    private String name;

    /**
     * Where is rack situated - in which container
     */
    @ManyToOne
    private Container container;

    /**
     * Boxes stores inside the rack
     */
    @OneToMany(mappedBy = "rack")
    private Set<RackBox> rackBoxes = new HashSet<RackBox>();

    /**
     * maximal number of boxes which can be located inside the rack
     */
    private Integer capacity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Set<RackBox> getRackBoxes() {
        return rackBoxes;
    }

    public void setRackBoxes(Set<RackBox> rackBoxes) {
        this.rackBoxes = rackBoxes;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rack)) return false;

        Rack rack = (Rack) o;

        if (!id.equals(rack.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Rack{" +
                "id=" + id +
                ", capacity=" + capacity +
                '}';
    }
}
