package cz.bbmri.entities.infrastructure;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Box is part of biobank infrastructure hierarchy. It can be located inside rack or stand alone in non-structured
 * container. Box has predefined capacity (amount of sample aliquotes which can be stored inside). It is possible to set
 * recomended temperature conditions asociated with it.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Table
@Entity
public class Box implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    /**
     * Unique identification of box from original institution
     */
    private String name;

    /**
     * Each box has specific volume. This volume is defined by capacity. Each position represents one "slot" which
     * can be used as a storage of sample.
     */

    // TODO musim byt nejake omezeni aby box nemel viz definovanych pozic nezli je jeho kapacita

    @OneToMany(mappedBy = "box", fetch = FetchType.EAGER)
    private Set<Position> positions = new HashSet<Position>();

    /**
     * Defined volume of box - number of positions
     */
    private Integer capacity;

    /**
     * Minimal temperature in which the box must be stored. Not required attribute.
     */
    private Float tempMin;

    /**
     * Maximal temperature in which the box must be stored. Not required attribute.
     */
    private Float tempMax;

    public Box() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Float getTempMin() {
        return tempMin;
    }

    public void setTempMin(Float tempMin) {
        this.tempMin = tempMin;
    }

    public Float getTempMax() {
        return tempMax;
    }

    public void setTempMax(Float tempMax) {
        this.tempMax = tempMax;
    }

    public Integer getNumberOfPositions() {
        if (positions == null) {
            return null;
        }
        return positions.size();
    }

//    public String getType(){
//         return "Box";
//     }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Box)) return false;

        Box box = (Box) o;

        if (!id.equals(box.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Box{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                '}';
    }
}
