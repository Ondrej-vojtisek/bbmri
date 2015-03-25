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
public class Position implements Serializable {

    public static final String PROP_ID = "id";
   	public static final String PROP_ROW = "row";
   	public static final String PROP_COLUMN = "column";
   	public static final String PROP_SEQUENTIAL_POSITION = "sequentialPosition";
   	public static final String PROP_BOX = "box";
   	public static final String PROP_SAMPLE = "sample";

	private long id;
    private Integer row;
    private Integer column;
    private Integer sequentialPosition;
    private Box box;
	private Sample sample;
	
	public void setId(long value) {
		this.id = value;
	}
	
	public long getId() {
		return id;
	}
	
	public void setRow(int value) {
		setRow(new Integer(value));
	}
	
	public void setRow(Integer value) {
		this.row = value;
	}
	
	public Integer getRow() {
		return row;
	}
	
	public void setColumn(int value) {
		setColumn(new Integer(value));
	}
	
	public void setColumn(Integer value) {
		this.column = value;
	}
	
	public Integer getColumn() {
		return column;
	}
	
	public void setSequentialPosition(int value) {
		setSequentialPosition(new Integer(value));
	}
	
	public void setSequentialPosition(Integer value) {
		this.sequentialPosition = value;
	}
	
	public Integer getSequentialPosition() {
		return sequentialPosition;
	}
	
	public void setBox(Box value) {
		this.box = value;
	}
	
	public Box getBox() {
		return box;
	}
	
	public void setSample(Sample value) {
		this.sample = value;
	}
	
	public Sample getSample() {
		return sample;
	}

}
