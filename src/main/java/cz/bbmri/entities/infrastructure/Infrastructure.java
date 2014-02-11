package cz.bbmri.entities.infrastructure;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.SampleRequest;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private List<Container> containers = new ArrayList<Container>();

    @OneToMany(mappedBy = "infrastructure")
    private List<Box> boxes = new ArrayList<Box>();

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

    public List<Container> getContainers() {
        return containers;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
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
                ", biobank=" + biobank +
                ", containers=" + containers +
                ", boxes=" + boxes +
                '}';
    }
}
