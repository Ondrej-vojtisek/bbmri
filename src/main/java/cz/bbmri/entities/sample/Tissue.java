package cz.bbmri.entities.sample;

import cz.bbmri.entities.sample.field.Morphology;
import cz.bbmri.entities.sample.field.PTNM;
import cz.bbmri.entities.sample.field.TNM;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Specialized type of sample - tissue
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Entity
public class Tissue extends Sample {

    private static final long serialVersionUID = 1L;

    public static final String PROP_TNM = "tnm";
    public static final String PROP_PTNM = "ptnm";
    public static final String PROP_MORPHOLOGY = "morphology";
    public static final String PROP_FREEZEDATE = "freezeDate";

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
