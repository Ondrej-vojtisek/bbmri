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
import java.util.HashSet;
import java.util.Set;

public class Monitoring implements Serializable {

    public static final String PROP_CONTAINER = "container";
   	public static final String PROP_ID = "id";
   	public static final String PROP_NAME = "name";
    public static final String PROP_MEASUREMENT_TYPE = "measurementType";
   	public static final String PROP_RECORD = "record";

	private Container container;
	private long id;
	private String name;
    private MeasurementType measurementType;
	
	private Set<Record> record = new HashSet<Record>();
	
	public void setId(long value) {
		this.id = value;
	}
	
	public long getId() {
		return id;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setContainer(Container value) {
		this.container = value;
	}
	
	public Container getContainer() {
		return container;
	}
	
	public void setRecord(Set<Record> value) {
		this.record = value;
	}
	
	public Set<Record> getRecord() {
		return record;
	}

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }
}
