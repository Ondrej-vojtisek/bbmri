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

public class Diagnosis implements Serializable {

    public static final String PROP_KEY = "key";
    public static final String PROP_SAMPLE = "sample";

    private String key;
    private Sample sample;
    private long sampleId;

    public void setSampleId(long value) {
        this.sampleId = value;
    }

    public long getSampleId() {
        return sampleId;
    }

    public void setKey(String value) {
        this.key = value;
    }

    public String getKey() {
        return key;
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
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Diagnosis diagnosis = (Diagnosis) o;

        if (sampleId != diagnosis.sampleId) return false;
        return key.equals(diagnosis.key);

    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + (int) (sampleId ^ (sampleId >>> 32));
        return result;
    }
}
