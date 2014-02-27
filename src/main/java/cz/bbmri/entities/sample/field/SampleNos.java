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

    public Integer getAvailableSamplesNo() {
        return availableSamplesNo;
    }

    public void setAvailableSamplesNo(Integer availableSamplesNo) {
        this.availableSamplesNo = availableSamplesNo;
    }

    private boolean checkAmount(int requestedAmount) {
        if (requestedAmount < 1) {
            return false;
        }
        if (requestedAmount > samplesNo) {
            return false;
        }
        return true;
    }

    // decrease amount of samples, both available and total amount
    public boolean decreaseAmount(int requestedAmount) {
        if (!checkAmount(requestedAmount)) return false;

        if (requestedAmount > availableSamplesNo) {
            return false;
        }

        availableSamplesNo -= requestedAmount;
        samplesNo -= requestedAmount;
        return true;
    }

    // increase amount of samples, both available and total amount
    public boolean increaseAmount(int requestedAmount) {
        if (!checkAmount(requestedAmount)) return false;

        availableSamplesNo += requestedAmount;
        samplesNo += requestedAmount;
        return true;

    }

    // decrease amount - also not available samples can be withdrawn
    public boolean withdrawAmount(int requestedAmount) {
        if (!checkAmount(requestedAmount)) return false;

        if (requestedAmount > samplesNo) {
            return false;
        }
        if(requestedAmount > availableSamplesNo){
            availableSamplesNo = 0;
        }

        samplesNo -= requestedAmount;
        return true;
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
