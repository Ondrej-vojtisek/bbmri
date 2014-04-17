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
public class Diagnosis implements Serializable {

    @Column(name="diagnosis", length = 5)
    private
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
        if (!(o instanceof Diagnosis)) return false;

        Diagnosis diagnosis = (Diagnosis) o;

        if (classification != null ? !classification.equals(diagnosis.classification) : diagnosis.classification != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return classification != null ? classification.hashCode() : 0;
    }

    @Override
    public String toString() {
        return classification;
    }
}
