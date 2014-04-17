package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.Retrieved;
import cz.bbmri.entities.infrastructure.Position;
import cz.bbmri.entities.sample.field.MaterialType;
import cz.bbmri.entities.sample.field.SampleIdentification;
import cz.bbmri.entities.sample.field.SampleNos;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Table
@Entity
public class Sample implements Serializable, Comparable<Sample> {
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


    /* This field should be in specialized objects in xml import schema. For DiagnosisMaterial it will be be set as 1/1
    *  The reason is to make it like this is to make easier validation of request in RequestService */
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


    public static Comparator<Sample> SampleIdComparator
            = new Comparator<Sample>() {

        public int compare(Sample sample1,
                           Sample sample2) {

            SampleIdentification sampleIdentification1 = sample1.getSampleIdentification();
            SampleIdentification sampleIdentification2 = sample2.getSampleIdentification();

            if (sampleIdentification1 == null) {
                if (sampleIdentification2 == null) {
                    return 0;
                } else {
                    return Integer.MIN_VALUE;
                }
            }

            if (sampleIdentification2 == null) {
                return Integer.MAX_VALUE;
            }

            //ascending order
            return sampleIdentification1.compareTo(sampleIdentification2);
        }

    };

    public static Comparator<Sample> YearComparator
            = new Comparator<Sample>() {


        public int compare(Sample sample1,
                           Sample sample2) {

            SampleIdentification sampleIdentification1 = sample1.getSampleIdentification();
            SampleIdentification sampleIdentification2 = sample2.getSampleIdentification();

            if (sampleIdentification1 == null) {
                if (sampleIdentification2 == null) {
                    return 0;
                } else {
                    return Integer.MIN_VALUE;
                }
            }

            if (sampleIdentification2 == null) {
                return Integer.MAX_VALUE;
            }

            if (sampleIdentification1.getYear() == null) {
                if (sampleIdentification2.getYear() == null) {
                    return 0;
                } else {
                    return Integer.MIN_VALUE;
                }
            }

            if (sampleIdentification2.getYear() == null) {
                return Integer.MAX_VALUE;
            }

            //ascending order
            return sampleIdentification1.getYear().compareTo(sampleIdentification2.getYear());
        }

    };

    public static Comparator<Sample> NumberComparator
             = new Comparator<Sample>() {


         public int compare(Sample sample1,
                            Sample sample2) {

             SampleIdentification sampleIdentification1 = sample1.getSampleIdentification();
             SampleIdentification sampleIdentification2 = sample2.getSampleIdentification();

             if (sampleIdentification1 == null) {
                 if (sampleIdentification2 == null) {
                     return 0;
                 } else {
                     return Integer.MIN_VALUE;
                 }
             }

             if (sampleIdentification2 == null) {
                 return Integer.MAX_VALUE;
             }

             if (sampleIdentification1.getNumber() == null) {
                 if (sampleIdentification2.getNumber() == null) {
                     return 0;
                 } else {
                     return Integer.MIN_VALUE;
                 }
             }

             if (sampleIdentification2.getNumber() == null) {
                 return Integer.MAX_VALUE;
             }

             //ascending order
             return sampleIdentification1.getNumber().compareTo(sampleIdentification2.getNumber());
         }

     };
}

