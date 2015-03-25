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
	}
	
	public Sample getSample() {
		return sample;
	}

}
