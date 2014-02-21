package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.Retrieved;
import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Position;
import cz.bbmri.entities.sample.field.MaterialType;
import cz.bbmri.entities.sample.field.SampleIdentification;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 10.10.12
 * Time: 21:21
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "Sample")
@Entity
public class Sample implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Embedded
    private SampleIdentification sampleIdentificator;

    @Enumerated(EnumType.STRING)
    private Retrieved retrieved;

    /* Cut time in case of tissue, in other cases it is taking date */
    private Date takingDate;

    @Embedded
    private MaterialType materialType;

    @ManyToOne
    private Module module;

    @OneToMany(mappedBy = "sample")
    private List<Request> requests = new ArrayList<Request>();

    @OneToMany(mappedBy = "sample")
    private Set<Position> positions = new HashSet<Position>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SampleIdentification getSampleIdentificator() {
        return sampleIdentificator;
    }

    public void setSampleIdentificator(SampleIdentification sampleIdentificator) {
        this.sampleIdentificator = sampleIdentificator;
    }

    public Retrieved getRetrieved() {
        return retrieved;
    }

    public void setRetrieved(Retrieved retrieved) {
        this.retrieved = retrieved;
    }

    public Date getTakingDate() {
        return takingDate;
    }

    public void setTakingDate(Date takingDate) {
        this.takingDate = takingDate;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sample)) return false;

        Sample sample = (Sample) o;

        if (!id.equals(sample.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Sample{" +
                "id=" + id +
                ", sampleIdentificator=" + sampleIdentificator +
                ", retrieved=" + retrieved +
                ", takingDate=" + takingDate +
                ", materialType=" + materialType +
                ", module=" + module +
                ", requests=" + requests +
                '}';
    }
}

