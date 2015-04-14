/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * <p/>
 * This is an automatic generated file. It will be regenerated every time
 * you generate persistence class.
 * <p/>
 * Modifying its content may cause the program not work, or your work may lost.
 * <p/>
 * Licensee: Masaryk University
 * License Type: Academic
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

    public static final String PROP_ID = "id";
    public static final String PROP_VALIDATION = "validation";
    public static final String PROP_SPECIFICATION = "specification";
    public static final String PROP_CREATED = "created";
    public static final String PROP_LAST_MODIFICATION = "lastModification";
    public static final String PROP_USER = "user";
    public static final String PROP_REQUEST = "request";
    public static final String PROP_RESERVATION_STATE = "reservationState";
    public static final String PROP_BIOBANK = "biobank";

    private long id;
    private Date validation;
    private String specification;
    private Date created = new Date();
    private Date lastModification = new Date();
    private User user;
    private Set<Request> request = new HashSet<Request>();
    // Initial value
    private ReservationState reservationState = ReservationState.NEW;
    private Biobank biobank;

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

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public boolean getIsNew() {
        return reservationState.equals(ReservationState.NEW);
    }

    public boolean getIsApproved() {
        return reservationState.equals(ReservationState.APPROVED);
    }

    public boolean getIsDenied() {
        return reservationState.equals(ReservationState.DENIED);
    }

    public boolean getIsExpired() {
        return reservationState.equals(ReservationState.EXPIRED);
    }


    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", validation=" + validation +
                ", specification='" + specification + '\'' +
                ", created=" + created +
                ", lastModification=" + lastModification +
                ", user=" + user +
                ", reservationState=" + reservationState +
                '}';
    }
}
