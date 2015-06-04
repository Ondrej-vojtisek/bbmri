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

public class Container implements Serializable {

    public static final String FOLDER = "container";

    public static final String PROP_ID = "id";
   	public static final String PROP_BIOBANK = "biobank";
   	public static final String PROP_NAME = "name";
   	public static final String PROP_LOCATION = "location";
   	public static final String PROP_CAPACITY = "capacity";
   	public static final String PROP_TEMP_MIN = "tempMin";
   	public static final String PROP_TEMP_MAX = "tempMax";
   	public static final String PROP_CONTAINER = "container";
   	public static final String PROP_BOX = "box";
   	public static final String PROP_MONITORING = "monitoring";
   	public static final String PROP_CONTAINER_INNER = "containerInner";

    private long id;
    private Biobank biobank;
    private String name;
    private String location;
    private Integer capacity;
    private Float tempMin;
    private Float tempMax;
    private Container container;
    private Set<Box> box = new HashSet<Box>();
    private Set<Monitoring> monitoring = new HashSet<Monitoring>();
    private Set<Container> containerInner = new HashSet<Container>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Float getTempMin() {
        return tempMin;
    }

    public void setTempMin(Float tempMin) {
        this.tempMin = tempMin;
    }

    public Float getTempMax() {
        return tempMax;
    }

    public void setTempMax(Float tempMax) {
        this.tempMax = tempMax;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Set<Box> getBox() {
        return box;
    }

    public void setBox(Set<Box> box) {
        this.box = box;
    }

    public Set<Monitoring> getMonitoring() {
        return monitoring;
    }

    public void setMonitoring(Set<Monitoring> monitoring) {
        this.monitoring = monitoring;
    }

    public Set<Container> getContainerInner() {
        return containerInner;
    }

    public void setContainerInner(Set<Container> containerInner) {
        this.containerInner = containerInner;
    }
}
