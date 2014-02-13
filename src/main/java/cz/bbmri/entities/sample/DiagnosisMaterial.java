package cz.bbmri.entities.sample;

import cz.bbmri.entities.Sample;
import cz.bbmri.entities.sample.field.Diagnosis;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 10.2.14
 * Time: 18:18
 * To change this template use File | Settings | File Templates.
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

    public String getType(){
        return "DiagnosisMaterial";
    }

}
