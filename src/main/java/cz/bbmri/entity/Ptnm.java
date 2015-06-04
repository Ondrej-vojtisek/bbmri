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

public class Ptnm implements Serializable {

    public static final String FOLDER = "ptnm";

    public static final String PROP_SAMPLE = "sample";
    public static final String PROP_CLASSIFICATION = "classification";

    private Sample sample;

    private long sampleId;

    public void setSampleId(long value) {
        this.sampleId = value;
    }

    public long getSampleId() {
        return sampleId;
    }

    private String classification;

    public void setClassification(String value) {
        this.classification = value;
    }

    public String getClassification() {
        return classification;
    }

    public void setSample(Sample value) {
        this.sample = value;
        this.sampleId = value.getId();
    }

    public Sample getSample() {
        return sample;
    }

    @Override
    public String toString() {
        return classification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ptnm ptnm = (Ptnm) o;

        if (sampleId != ptnm.sampleId) return false;
        return classification.equals(ptnm.classification);

    }

    @Override
    public int hashCode() {
        int result = (int) (sampleId ^ (sampleId >>> 32));
        result = 31 * result + classification.hashCode();
        return result;
    }
}
