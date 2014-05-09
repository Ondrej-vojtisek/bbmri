package cz.bbmri.entities.sample;

import cz.bbmri.entities.sample.field.Diagnosis;

import javax.persistence.Embedded;
import javax.persistence.Entity;

/**
 * Specialized type of sample with diagnosis data.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Entity
public class DiagnosisMaterial extends Sample {

    @Embedded
    private Diagnosis diagnosis;

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public String toString() {
        return "DiagnosisMaterial{" +
                "diagnosis=" + diagnosis +
                '}';
    }
}
