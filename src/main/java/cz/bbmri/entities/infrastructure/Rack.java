package cz.bbmri.entities.infrastructure;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 11.2.14
 * Time: 15:48
 * To change this template use File | Settings | File Templates.
 */

@Table(name = "Rack")
@Entity
public class Rack implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    private Container container;

    @OneToMany(mappedBy = "rack")
    private List<Box> boxes = new ArrayList<Box>();

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

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
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
                ", container=" + container +
                ", boxes=" + boxes +
                ", capacity=" + capacity +
                '}';
    }
}
