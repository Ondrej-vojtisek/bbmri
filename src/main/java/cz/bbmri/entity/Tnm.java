/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Masaryk University
 * License Type: Academic
 */
package cz.bbmri.entity;

import java.io.Serializable;
public class Tnm implements Serializable {

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
}
