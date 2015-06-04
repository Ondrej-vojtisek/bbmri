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

public class Tnm implements Serializable {

    public static final String FOLDER = "tnm";

    public static final String PROP_SAMPLE = "sample";
    public static final String PROP_CLASSIFICATION = "classification";

    private Sample sample;
    private long sampleId;
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

    public long getSampleId() {
        return sampleId;
    }

    public void setSampleId(long sampleId) {
        this.sampleId = sampleId;
    }

    @Override
    public String toString() {
        return classification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tnm tnm = (Tnm) o;

        if (sampleId != tnm.sampleId) return false;
        return classification.equals(tnm.classification);

    }

    @Override
    public int hashCode() {
        int result = (int) (sampleId ^ (sampleId >>> 32));
        result = 31 * result + classification.hashCode();
        return result;
    }
}


