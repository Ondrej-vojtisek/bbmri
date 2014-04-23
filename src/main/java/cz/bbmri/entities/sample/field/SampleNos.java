package cz.bbmri.entities.sample.field;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * How many pieces (aliquotes) of sample are present in biobank repository?
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Embeddable
public class SampleNos implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Total number of aliquotes in repository
     */
    private Integer samplesNo;

    /**
     * Number of aliquotes which can be provided for research. Must be less than samplesNo
     */
    private Integer availableSamplesNo;

    /**
     * Original total count of aliquotes before any sample request was applied
     */
    private Integer originalSamplesNo;

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

    public Integer getOriginalSamplesNo() {
        return originalSamplesNo;
    }

    public void setOriginalSamplesNo(Integer originalSamplesNo) {
        this.originalSamplesNo = originalSamplesNo;
    }

    /**
     * Requested amount must be > 1 AND < total number of samples
     *
     * @param requestedAmount
     * @return
     */
    private boolean checkAmount(int requestedAmount) {
        return requestedAmount >= 1 && requestedAmount <= samplesNo;

    }

    // decrease amount of samples, both available and total amount

    /**
     * Decrease amount of samples. If we require more than present number -> return false.
     *
     * @param requestedAmount
     * @return
     */
    public boolean decreaseAmount(int requestedAmount) {
        if (!checkAmount(requestedAmount)) return false;

        if (requestedAmount > availableSamplesNo) {
            return false;
        }

        availableSamplesNo -= requestedAmount;
        samplesNo -= requestedAmount;
        return true;
    }

    /**
     * Request of samples was canceled. Alocated samples are increased to previous state.
     *
     * @param requestedAmount
     * @return
     */
    public boolean increaseAmount(int requestedAmount) {
        if (!checkAmount(requestedAmount)) return false;

        availableSamplesNo += requestedAmount;
        samplesNo += requestedAmount;
        return true;

    }

    /**
     * For internal usage inside institution it is possible to create request for all samples (both available and not
     * available samples).
     *
     * @param requestedAmount
     * @return
     */
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
