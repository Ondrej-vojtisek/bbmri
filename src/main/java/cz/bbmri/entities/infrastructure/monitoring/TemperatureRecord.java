package cz.bbmri.entities.infrastructure.monitoring;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 19.3.14
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "TemperatureRecord")
public class TemperatureRecord implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    private Monitoring monitoring;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Monitoring getMonitoring() {
        return monitoring;
    }

    public void setMonitoring(Monitoring monitoring) {
        this.monitoring = monitoring;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemperatureRecord that = (TemperatureRecord) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
