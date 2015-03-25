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

public class MeasurementType implements Serializable {

    public MeasurementType() {
    }

    public MeasurementType(int id, String physicalQuantity, String name, String unit) {
        this.id = id;
        this.physicalQuantity = physicalQuantity;
        this.name = name;
        this.unit = unit;
    }

    //    Must match the predefined table records from visual paradigm project

    public static final MeasurementType TEMPERATURE_CELSIUS = new MeasurementType(1, "Temperature", "Celsius", "°C");
    public static final MeasurementType TEMPERATURE_KELVIN = new MeasurementType(2, "Temperature", "Kelvin", "K");

    public static final String PROP_ID = "id";
   	public static final String PROP_PHYSICAL_QUANTITY = "physicalQuantity";
   	public static final String PROP_NAME = "name";
   	public static final String PROP_UNIT = "unit";
   	public static final String PROP_MONITORING = "monitoring";

	private int id;
    private String physicalQuantity;
    private String name;
    private String unit;
	
	private Set<Monitoring> monitoring = new HashSet<Monitoring>();
	
	public void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	
	public void setPhysicalQuantity(String value) {
		this.physicalQuantity = value;
	}
	
	public String getPhysicalQuantity() {
		return physicalQuantity;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setUnit(String value) {
		this.unit = value;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setMonitoring(Set<Monitoring> value) {
		this.monitoring = value;
	}
	
	public Set<Monitoring> getMonitoring() {
		return monitoring;
	}
}
