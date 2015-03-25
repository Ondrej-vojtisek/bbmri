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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Reservation implements Serializable {

    public static final String PROP_PROJECT = "project";
   	public static final String PROP_ID = "id";
   	public static final String PROP_VALIDATION = "validation";
   	public static final String PROP_SPECIFICATION = "specification";
   	public static final String PROP_CREATED = "created";
   	public static final String PROP_LAST_MODIFICATION = "lastModification";
   	public static final String PROP_USER = "user";
   	public static final String PROP_REQUEST = "request";
    public static final String PROP_RESERVATION_STATE = "reservationState";

	private Project project;
    private long id;
    private Date validation;
    private String specification;
    private Date created;
    private Date lastModification;
    private User user;
    private Set<Request> request = new HashSet<Request>();
    private ReservationState reservationState;
	
	public void setId(long value) {
		this.id = value;
	}
	
	public long getId() {
		return id;
	}
	
	public void setValidation(Date value) {
		this.validation = value;
	}
	
	public Date getValidation() {
		return validation;
	}
	
	public void setSpecification(String value) {
		this.specification = value;
	}
	
	public String getSpecification() {
		return specification;
	}

	public void setCreated(Date value) {
		this.created = value;
	}

	public Date getCreated() {
		return created;
	}

	public void setLastModification(Date value) {
		this.lastModification = value;
	}

	public Date getLastModification() {
		return lastModification;
	}

	public void setProject(Project value) {
		this.project = value;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setUser(User value) {
		this.user = value;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setRequest(Set<Request> value) {
		this.request = value;
	}
	
	public Set<Request> getRequest() {
		return request;
	}

    public ReservationState getReservationState() {
        return reservationState;
    }

    public void setReservationState(ReservationState reservationState) {
        this.reservationState = reservationState;
    }
}
