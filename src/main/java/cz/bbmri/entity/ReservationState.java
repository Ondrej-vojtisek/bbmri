package cz.bbmri.entity;

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

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ReservationState implements Serializable {
	public ReservationState() {
	}

    public ReservationState(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //    Must match the predefined table records from visual paradigm project

    public static final ReservationState NEW = new ReservationState(1, "new");
    public static final ReservationState APPROVED = new ReservationState(2, "approved");
    public static final ReservationState DENIED = new ReservationState(3, "denied");
    public static final ReservationState EXPIRED = new ReservationState(4, "expired");

    public static final String PROP_ID = "id";
   	public static final String PROP_NAME = "name";
   	public static final String PROP_RESERVATION = "reservation";

	private int id;
    private String name;
	
	private Set<Reservation> reservation = new HashSet<Reservation>();
	
	public void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setReservation(Set<Reservation> value) {
		this.reservation = value;
	}
	
	public Set<Reservation> getReservation() {
		return reservation;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationState)) return false;

        ReservationState that = (ReservationState) o;

        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }
}
