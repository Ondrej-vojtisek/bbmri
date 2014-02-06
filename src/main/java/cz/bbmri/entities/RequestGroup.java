package cz.bbmri.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 6.2.13
 * Time: 11:43
 * <p/>
 * This object represents set of request associated with specific project and biobank. e.g. We want 2 pieces od sample A
 * and 2 pieces of sample B - both from biobank 1.
 * We link this requests together into request group so biobank administrator can approve/deny them at one time.
 */
@Table(name = "RequestGroup")
@Entity
public class RequestGroup {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "requestGroup")
    private List<Request> requests = new ArrayList<Request>();

    @ManyToOne//(cascade = CascadeType.MERGE)
    private SampleRequest sampleRequest;

    @Type(type = "timestamp")
    private Date lastModification;

    public RequestGroup() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public SampleRequest getSampleRequest() {
        return sampleRequest;
    }

    public void setSampleRequest(SampleRequest sampleRequest) {
        this.sampleRequest = sampleRequest;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    public Integer getAmountOfSamples() {
        if (requests != null) {
            return requests.size();
        }
        return 0;
    }

    public Integer getTotalAmountOfAliquotes() {
        if (requests != null) {
            Integer suma = 0;
            for(Request request : requests){
                suma += request.getNumOfRequested();
            }
            return suma;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestGroup that = (RequestGroup) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RequestGroup{" +
                "id=" + id +
                ", requests=" + requests +
                '}';
    }
}
