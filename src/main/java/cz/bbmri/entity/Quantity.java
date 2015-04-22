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
    private Short available;
    private Short total;
    private Short original;

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

    public void setOriginal(short value) {
        setOriginal(new Short(value));
    }

    public void setOriginal(Short value) {
        this.original = value;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quantity quantity = (Quantity) o;

        if (sampleId != quantity.sampleId) return false;
        if (!available.equals(quantity.available)) return false;
        return total.equals(quantity.total);

    }

    @Override
    public int hashCode() {
        int result = (int) (sampleId ^ (sampleId >>> 32));
        result = 31 * result + available.hashCode();
        result = 31 * result + total.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return available + "/" + total + "/" + original;
    }
}
