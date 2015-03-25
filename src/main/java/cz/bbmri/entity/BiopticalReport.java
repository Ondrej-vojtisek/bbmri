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

public class BiopticalReport implements Serializable {

    public static final String PROP_SAMPLE = "sample";
   	public static final String PROP_YEAR = "year";
   	public static final String PROP_NUMBER = "number";
	
	private Sample sample;
	
	private long sampleId;
	
	public void setSampleId(long value) {
		this.sampleId = value;
	}
	
	public long getSampleId() {
		return sampleId;
	}
	
	private Short year;
	
	private Long number;
	
	public void setYear(short value) {
		setYear(new Short(value));
	}
	
	public void setYear(Short value) {
		this.year = value;
	}
	
	public Short getYear() {
		return year;
	}
	
	public void setNumber(long value) {
		setNumber(new Long(value));
	}
	
	public void setNumber(Long value) {
		this.number = value;
	}
	
	public Long getNumber() {
		return number;
	}
	
	public void setSample(Sample value) {
		this.sample = value;
	}
	
	public Sample getSample() {
		return sample;
	}

	

}
