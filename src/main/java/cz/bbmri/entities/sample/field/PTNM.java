package cz.bbmri.entities.sample.field;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 11.2.14
 * Time: 11:38
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class PTNM implements Serializable {

    @Column(name="ptnm")
    String classification;

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PTNM)) return false;

        PTNM ptnm = (PTNM) o;

        if (classification != null ? !classification.equals(ptnm.classification) : ptnm.classification != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return classification != null ? classification.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PTNM{" +
                "classification='" + classification + '\'' +
                '}';
    }
}
