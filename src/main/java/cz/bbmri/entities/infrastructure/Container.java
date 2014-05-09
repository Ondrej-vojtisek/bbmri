package cz.bbmri.entities.infrastructure;


import cz.bbmri.entities.infrastructure.monitoring.Monitoring;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Instance of Container represents one dewar container from real world. It has limited capacity (number of racks),
 * recommended temperature and information about location.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Table
@Entity
public class Container implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    /**
     * Unique identification
     */
    private String name;

    @ManyToOne
    private Infrastructure infrastructure;

    /**
     * Racks located inside container
     */
    @OneToMany(mappedBy = "container")
    private Set<Rack> racks = new HashSet<Rack>();

    /**
     * Monitoring of physical conditions
     */
    @OneToMany(mappedBy = "container")
    private List<Monitoring> monitorings  = new ArrayList<Monitoring>();

    /**
     * Description where the container is situated. Not required.
     */
    private String location;

    /**
     * How many racks is inside
     */
    // TODO kontrola aby stojanu nebylo vice nez je kapacita
    private Integer capacity;

    /**
     * Recommended minimal temperature for samples storaged in the container. Not required.
     */
    private Float tempMin;

    /**
     * Recommended maximal temperature for samples storaged in the container. Not required.
     */
    private Float tempMax;

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

    public Infrastructure getInfrastructure() {
        return infrastructure;
    }

    public void setInfrastructure(Infrastructure infrastructure) {
        this.infrastructure = infrastructure;
    }

    public Set<Rack> getRacks() {
        return racks;
    }

    public void setRacks(Set<Rack> racks) {
        this.racks = racks;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Integer getNumberOfRacks(){
        if(racks == null){
            return null;
        }
        return racks.size();
    }

    public List<Monitoring> getMonitorings() {
        return monitorings;
    }

    public void setMonitorings(List<Monitoring> monitorings) {
        this.monitorings = monitorings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Container)) return false;

        Container container = (Container) o;

        if (!id.equals(container.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Container{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", capacity=" + capacity +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                '}';
    }
}
