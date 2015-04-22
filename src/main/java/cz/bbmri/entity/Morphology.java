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

public class Morphology implements Serializable {

    public static final String PROP_SAMPLE = "sample";
    public static final String PROP_CLASSIFICATION = "classification";
    public static final String PROP_GRADING = "grading";

    private Sample sample;
    private long sampleId;
    private String classification;
    private Short grading;

    public void setSampleId(long value) {
        this.sampleId = value;
    }

    public long getSampleId() {
        return sampleId;
    }

    public void setClassification(String value) {
        this.classification = value;
    }

    public String getClassification() {
        return classification;
    }

    public void setGrading(short value) {
        setGrading(new Short(value));
    }

    public void setGrading(Short value) {
        this.grading = value;
    }

    public Short getGrading() {
        return grading;
    }

    public void setSample(Sample value) {
        this.sample = value;
        if (value != null) {
            this.sampleId = value.getId();
        }
    }

    public Sample getSample() {
        return sample;
    }

    @Override
    public String toString() {
        if (classification != null) {
            return classification;
        } else if (grading != null) {
            return "Grading " + grading;
        }

        return "Unknown";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Morphology that = (Morphology) o;

        if (sampleId != that.sampleId) return false;
        if (classification != null ? !classification.equals(that.classification) : that.classification != null)
            return false;
        return !(grading != null ? !grading.equals(that.grading) : that.grading != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (sampleId ^ (sampleId >>> 32));
        result = 31 * result + (classification != null ? classification.hashCode() : 0);
        result = 31 * result + (grading != null ? grading.hashCode() : 0);
        return result;
    }
}
