package cz.bbmri.entities.sample;

import cz.bbmri.entities.AttributeEquality;
import cz.bbmri.entities.Module;
import cz.bbmri.entities.Request;
import cz.bbmri.entities.enumeration.Retrieved;
import cz.bbmri.entities.exception.DifferentEntityException;
import cz.bbmri.entities.infrastructure.Position;
import cz.bbmri.entities.sample.field.MaterialType;
import cz.bbmri.entities.sample.field.SampleIdentification;
import cz.bbmri.entities.sample.field.SampleNos;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Description of one sample stored in any biobank involved in project.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Table
@Entity
public class Sample implements Serializable, Comparable<Sample>, AttributeEquality<Sample> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;


    @Embedded
    private SampleIdentification sampleIdentification;

    @Enumerated(EnumType.STRING)
    private Retrieved retrieved;

    /* Cut time in case of tissue, in other cases it is taking date */
    @Type(type = "timestamp")
    private Date takingDate;

    @Embedded
    private MaterialType materialType;

    @ManyToOne
    private Module module;

    @OneToMany(mappedBy = "sample")
    private List<Request> requests = new ArrayList<Request>();

    @OneToMany(mappedBy = "sample")
    private Set<Position> positions = new HashSet<Position>();

    @Embedded
    private SampleNos sampleNos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SampleIdentification getSampleIdentification() {
        return sampleIdentification;
    }

    public void setSampleIdentification(SampleIdentification sampleIdentification) {
        this.sampleIdentification = sampleIdentification;
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

    public SampleNos getSampleNos() {
        return sampleNos;
    }

    public void setSampleNos(SampleNos sampleNos) {
        this.sampleNos = sampleNos;
    }

    public boolean attributeEquality(Sample sample) throws DifferentEntityException {
        // ID must match
        if (!this.equals(sample)) {
            throw new DifferentEntityException("Compared samples are not the same.");
        }

        if (materialType != null ? !materialType.equals(sample.materialType) : sample.materialType != null) {
           //TODO debug only - delete
            System.err.println("MaterialType not equal");
            return false;
        }

        if (retrieved != sample.retrieved) {
            //TODO debug only - delete
            System.err.println("Retrieved not equal");
            return false;
        }

        if (sampleIdentification != null ? !sampleIdentification.equals(sample.sampleIdentification) : sample.sampleIdentification != null) {
            //TODO debug only - delete
            System.err.println("SampleIdentification not equal");
            return false;
        }

        if (sampleNos != null ? !sampleNos.equals(sample.sampleNos) : sample.sampleNos != null) {
            //TODO debug only - delete
            System.err.println("SampleNos not equal");
            return false;
        }
        if (takingDate != null ? !takingDate.equals(sample.takingDate) : sample.takingDate != null) {
            //TODO debug only - delete
            System.err.println("TakingDate");
            return false;
        }

        return true;

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
                ", sampleIdentification=" + sampleIdentification +
                ", retrieved=" + retrieved +
                ", takingDate=" + takingDate +
                ", materialType=" + materialType +
                ", module=" + module +
                ", requests=" + requests +
                '}';
    }

    public int compareTo(Sample compareSample) {

        if (this.getId() > compareSample.getId())
            return 1;
        else if (this.getId() < compareSample.getId())
            return -1;
        else
            return 0;
    }

}

