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
	}
	
	public Sample getSample() {
		return sample;
	}

}
