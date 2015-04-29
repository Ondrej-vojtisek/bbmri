/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * <p/>
 * This is an automatic generated file. It will be regenerated every time
 * you generate persistence class.
 * <p/>
 * Modifying its content may cause the program not work, or your work may lost.
 * <p/>
 * Licensee: Masaryk University
 * License Type: Academic
 * <p/>
 * Licensee: Masaryk University
 * License Type: Academic
 */

/**
 * Licensee: Masaryk University
 * License Type: Academic
 */
package cz.bbmri.entity;

import java.io.Serializable;

public class Quantity implements Serializable {

    public static final String PROP_SAMPLE = "sample";
    public static final String PROP_AVAILABLE = "available";
    public static final String PROP_TOTAL = "total";
    public static final String PROP_ORIGINAL = "original";

    private Sample sample;
    private long sampleId;
    private short available;
    private short total;
    private short original;

    public void setSampleId(long value) {
        this.sampleId = value;
    }

    public long getSampleId() {
        return sampleId;
    }

    public void setAvailable(short value) {
        setAvailable(new Short(value));
    }

    public void setAvailable(Short value) {
        this.available = value;
    }

    public Short getAvailable() {
        return available;
    }

    public void setTotal(short value) {
        setTotal(new Short(value));
    }

    public void setTotal(Short value) {
        this.total = value;
    }

    public Short getTotal() {
        return total;
    }

    public void setOriginal(short original) {
        this.original = original;
    }

    public Short getOriginal() {
        return original;
    }

    public void setSample(Sample value) {
        this.sample = value;
        this.sampleId = value.getId();
    }

    public Sample getSample() {
        return sample;
    }

    /**
     * Requested amount x must be x > 1 AND x < total number of samples
     *
     * @param requestedAmount - number of requested samples
     * @return
     */
    private boolean checkAmount(short requestedAmount) {
        return requestedAmount >= 1 && requestedAmount <= total;

    }

    // decrease amount of samples, both available and total amount

    /**
     * Decrease amount of samples. If we require more than available amount -> return false.
     *
     * @param requestedAmount - number of requested samples
     * @return
     */
    public boolean decreaseAmount(short requestedAmount) {
        if (!checkAmount(requestedAmount)) return false;

        if (requestedAmount > available) {
            return false;
        }

        available -= requestedAmount;
        total -= requestedAmount;
        return true;
    }

    /**
     * Request of samples was canceled. Alocated samples are increased to previous state.
     *
     * @param requestedAmount
     * @return
     */
    public boolean increaseAmount(short requestedAmount) {
        if (!checkAmount(requestedAmount)) return false;

        available += requestedAmount;
        total += requestedAmount;
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
    public boolean withdrawAmount(short requestedAmount) {
        if (!checkAmount(requestedAmount)) return false;

        if (requestedAmount > total) {
            return false;
        }

        total -= requestedAmount;
        available -=requestedAmount;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quantity quantity = (Quantity) o;

        if (sampleId != quantity.sampleId) return false;
        if (available != quantity.available) return false;
        if (total != quantity.total) return false;
        return original == quantity.original;

    }

    @Override
    public int hashCode() {
        int result = (int) (sampleId ^ (sampleId >>> 32));
        result = 31 * result + (int) available;
        result = 31 * result + (int) total;
        result = 31 * result + (int) original;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(available < 0){
            sb.append(0);
        }else{
            sb.append(available);
        }
        sb.append("/");
        sb.append(total);
        sb.append("/");
        sb.append(original);

        return sb.toString();
    }
}
