package cz.bbmri.entities.sample;

import cz.bbmri.entities.Sample;
import cz.bbmri.entities.enumeration.ProjectState;
import cz.bbmri.entities.enumeration.Retrieved;
import cz.bbmri.entities.sample.field.*;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 10.2.14
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class Tissue extends Sample {

    @Embedded
    @Column(name = "tnm")
    private TNM tnm;

    @Embedded
    @Column(name = "ptnm")
    private PTNM ptnm;

    @Embedded
    @Column(name = "morphology")
    private Morphology morphology;

    @Embedded
    private SampleNos sampleNos;

    private Date freezeDate;

    public TNM getTnm() {
        return tnm;
    }

    public void setTnm(TNM tnm) {
        this.tnm = tnm;
    }

    public PTNM getPtnm() {
        return ptnm;
    }

    public void setPtnm(PTNM ptnm) {
        this.ptnm = ptnm;
    }

    public Morphology getMorphology() {
        return morphology;
    }

    public void setMorphology(Morphology morphology) {
        this.morphology = morphology;
    }

    public SampleNos getSampleNos() {
        return sampleNos;
    }

    public void setSampleNos(SampleNos sampleNos) {
        this.sampleNos = sampleNos;
    }

    public Date getFreezeDate() {
        return freezeDate;
    }

    public void setFreezeDate(Date freezeDate) {
        this.freezeDate = freezeDate;
    }
}
