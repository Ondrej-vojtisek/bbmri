package cz.bbmri.entities.sample.field;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 11.2.14
 * Time: 11:40
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class SampleNos implements Serializable {

    private Integer samplesNo;

    private Integer availableSamplesNo;

    public Integer getSamplesNo() {
        return samplesNo;
    }

    public void setSamplesNo(Integer samplesNo) {
        this.samplesNo = samplesNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleNos)) return false;

        SampleNos sampleNos = (SampleNos) o;

        if (!availableSamplesNo.equals(sampleNos.availableSamplesNo)) return false;
        if (!samplesNo.equals(sampleNos.samplesNo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = samplesNo.hashCode();
        result = 31 * result + availableSamplesNo.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SampleNos{" +
                "samplesNo=" + samplesNo +
                ", availableSamplesNo=" + availableSamplesNo +
                '}';
    }
}
