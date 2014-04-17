package cz.bbmri.entities.sample.field;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Embeddable
public class SampleIdentification implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer year;

    private Integer number;

    @Column(nullable = false, unique=true, length = 32)
    private String sampleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleIdentification)) return false;

        SampleIdentification that = (SampleIdentification) o;

        if (!sampleId.equals(that.sampleId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return sampleId.hashCode();
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    @Override
    public String toString() {
        return "SampleIdentification{" +
                "year='" + year + '\'' +
                ", number='" + number + '\'' +
                ", sampleId='" + sampleId + '\'' +
                '}';
    }

    public int compareTo(SampleIdentification compareSampleIndentification) {

       return this.getSampleId().compareTo(compareSampleIndentification.getSampleId());
    }

}
