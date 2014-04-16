package cz.bbmri.entities.sample;

import cz.bbmri.entities.Sample;
import cz.bbmri.entities.sample.field.Morphology;
import cz.bbmri.entities.sample.field.PTNM;
import cz.bbmri.entities.sample.field.TNM;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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

    private static final long serialVersionUID = 1L;

    @Embedded
    @Column
    private TNM tnm;

    @Embedded
    @Column
    private PTNM ptnm;

    @Embedded
    @Column
    private Morphology morphology;

    @Type(type = "timestamp")
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


    public Date getFreezeDate() {
        return freezeDate;
    }

    public void setFreezeDate(Date freezeDate) {
        this.freezeDate = freezeDate;
    }

    @Override
    public String toString() {
        return "Tissue{" +
                "tnm=" + tnm +
                ", ptnm=" + ptnm +
                ", morphology=" + morphology +
                ", freezeDate=" + freezeDate +
                ", retrieved=" + getRetrieved() +
                ", takingDate=" + getTakingDate() +
                ", materialType=" + getMaterialType() +
                '}';
    }
}
