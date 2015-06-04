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
import java.sql.Time;

public class Record implements Serializable {

	public static final String FOLDER = "record";

    public static final String PROP_MONITORING = "monitoring";
   	public static final String PROP_VALUE = "value";
   	public static final String PROP_TIME = "time";
	
	private Monitoring monitoring;
	
	private long monitoringId;
	
	public void setMonitoringId(long value) {
		this.monitoringId = value;
	}
	
	public long getMonitoringId() {
		return monitoringId;
	}
	
	private Double value;
	
	private Time time;
	
	public void setValue(double value) {
		setValue(new Double(value));
	}
	
	public void setValue(Double value) {
		this.value = value;
	}
	
	public Double getValue() {
		return value;
	}

	public void setTime(Time value) {
		this.time = value;
	}

	public Time getTime() {
		return time;
	}

	public void setMonitoring(Monitoring value) {
		this.monitoring = value;
	}
	
	public Monitoring getMonitoring() {
		return monitoring;
	}

}
