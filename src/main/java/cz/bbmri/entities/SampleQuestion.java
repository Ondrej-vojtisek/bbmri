package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.RequestState;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Universal object for sample requesting. User specify which samples he need for research. Biobank administrator then
 * add set of samples which fullfill requirements.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Entity
@Table
public abstract class SampleQuestion extends Withdraw {

    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
//    @Column(nullable = false)
//    private Long id;

    /**
     * Description of required samples
     */
    @Column(columnDefinition = "TEXT")
    private String specification;

//    @ManyToOne
//    private Biobank biobank;

    /**
     * State of request/reservation
     */
    @Enumerated(EnumType.STRING)
    private RequestState requestState;

//    @Type(type = "timestamp")
//    private Date created;
//
//    /**
//     * Date and time of last change
//     */
//    @Type(type = "timestamp")
//    private Date lastModification;


//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

//    public Biobank getBiobank() {
//        return biobank;
//    }
//
//    public void setBiobank(Biobank biobank) {
//        this.biobank = biobank;
//    }

    public RequestState getRequestState() {
        return requestState;
    }

    public void setRequestState(RequestState requestState) {
        this.requestState = requestState;
    }

//    public Date getCreated() {
//        return created;
//    }
//
//    public void setCreated(Date created) {
//        this.created = created;
//    }
//
//    public Date getLastModification() {
//        return lastModification;
//    }
//
//    public void setLastModification(Date lastModification) {
//        this.lastModification = lastModification;
//    }

//    public Set<Request> getRequests() {
//        return requests;
//    }
//
//    public void setRequests(Set<Request> requests) {
//        this.requests = requests;
//    }

    private boolean checkState(RequestState requestState) {
        return getRequestState() != null && getRequestState().equals(requestState);
    }

    public boolean isApproved() {
        return checkState(RequestState.APPROVED);
    }

    public boolean isNew() {
        return checkState(RequestState.NEW);
    }

    public boolean isDenied() {
        return checkState(RequestState.DENIED);
    }

    public boolean isClosed() {
        return checkState(RequestState.CLOSED);
    }

    public boolean isAgreed() {
        return checkState(RequestState.AGREED);
    }

    public boolean isDelivered() {
        return checkState(RequestState.DELIVERED);
    }



    @Override
    public String toString() {
        return "SampleQuestion{" +
                ", specification='" + specification + '\'' +
                ", requestState=" + requestState +
                '}';
    }
}
