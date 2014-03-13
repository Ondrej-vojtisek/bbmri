package cz.bbmri.entities.sample.field;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 11.2.14
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class SampleIdentification implements Serializable {

    private String year;

    private String number;

    private String sampleId;

    public String getYear() {
        return year;
    }

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

    public void setYear(String year) {
        this.year = year;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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
