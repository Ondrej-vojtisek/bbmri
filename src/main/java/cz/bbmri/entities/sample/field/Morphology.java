package cz.bbmri.entities.sample.field;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 11.2.14
 * Time: 11:39
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class Morphology implements Serializable {

    @Column(name="morphology")
    String classification;

    Integer grading;

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Integer getGrading() {
        return grading;
    }

    public void setGrading(Integer grading) {
        this.grading = grading;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Morphology)) return false;

        Morphology that = (Morphology) o;

        if (classification != null ? !classification.equals(that.classification) : that.classification != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return classification != null ? classification.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Morphology{" +
                "classification='" + classification + '\'' +
                ", grading=" + grading +
                '}';
    }
}
