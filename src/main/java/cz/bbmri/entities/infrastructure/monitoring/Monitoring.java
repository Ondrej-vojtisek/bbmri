package cz.bbmri.entities.infrastructure.monitoring;

import cz.bbmri.entities.infrastructure.Container;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 19.3.14
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "Monitoring")
@Entity
public class Monitoring implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @OneToOne
    private Container container;

    @OneToMany(mappedBy = "monitoring")
    private List<TemperatureRecord> temperatureRecords = new ArrayList<TemperatureRecord>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TemperatureRecord> getTemperatureRecords() {
        return temperatureRecords;
    }

    public void setTemperatureRecords(List<TemperatureRecord> temperatureRecords) {
        this.temperatureRecords = temperatureRecords;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Monitoring that = (Monitoring) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
