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

public class Box implements Serializable {

	public static final String FOLDER = "box";

    public static final String PROP_ID = "id";
   	public static final String PROP_CAPACITY = "capacity";
   	public static final String PROP_NAME = "name";
   	public static final String PROP_TEMP_MIN = "tempMin";
   	public static final String PROP_TEMP_MAX = "tempMax";
   	public static final String PROP_CONTAINER = "container";
   	public static final String PROP_POSITION = "position";

	private long id;
	private Integer capacity;
	private String name;
	private Float tempMin;
	private Float tempMax;
	private Container container;
	
	private Set<Position> position = new HashSet<Position>();
	
	public void setId(long value) {
		this.id = value;
	}
	
	public long getId() {
		return id;
	}
	
	public void setCapacity(int value) {
		setCapacity(new Integer(value));
	}
	
	public void setCapacity(Integer value) {
		this.capacity = value;
	}
	
	public Integer getCapacity() {
		return capacity;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setTempMin(float value) {
		setTempMin(new Float(value));
	}
	
	public void setTempMin(Float value) {
		this.tempMin = value;
	}
	
	public Float getTempMin() {
		return tempMin;
	}
	
	public void setTempMax(float value) {
		setTempMax(new Float(value));
	}
	
	public void setTempMax(Float value) {
		this.tempMax = value;
	}
	
	public Float getTempMax() {
		return tempMax;
	}
	
	public void setContainer(Container value) {
		this.container = value;
	}
	
	public Container getContainer() {
		return container;
	}
	
	public void setPosition(Set<Position> value) {
		this.position = value;
	}
	
	public Set<Position> getPosition() {
		return position;
	}
	

}
