package cz.bbmri.entities.sample;

import cz.bbmri.entities.Sample;
import cz.bbmri.entities.sample.field.SampleNos;

import javax.persistence.Embedded;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 10.2.14
 * Time: 18:16
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Genome extends Sample {

    @Embedded
    private SampleNos sampleNos;

    public SampleNos getSampleNos() {
        return sampleNos;
    }

    public void setSampleNos(SampleNos sampleNos) {
        this.sampleNos = sampleNos;
    }
}
