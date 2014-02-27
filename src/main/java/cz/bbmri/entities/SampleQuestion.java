package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.RequestState;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 24.2.14
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "SampleQuestion")
@Entity
public class SampleQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "SPECIFICATION", columnDefinition = "TEXT")
    private String specification;

    @ManyToOne
    private Biobank biobank;

    @Enumerated(EnumType.STRING)
    private RequestState requestState;

    @Type(type = "timestamp")
    private Date created;

    @Type(type = "timestamp")
    private Date lastModification;

    @OneToMany(mappedBy = "sampleQuestion")
    private Set<Request> requests = new HashSet<Request>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public RequestState getRequestState() {
        return requestState;
    }

    public void setRequestState(RequestState requestState) {
        this.requestState = requestState;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    private boolean checkState(RequestState requestState) {
        if (getRequestState() == null) {
            return false;
        }
        return getRequestState().equals(requestState);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SampleQuestion that = (SampleQuestion) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "SampleQuestion{" +
                "id=" + id +
                ", specification='" + specification + '\'' +
                ", requestState=" + requestState +
                ", created=" + created +
                ", lastModification=" + lastModification +
                '}';
    }
}