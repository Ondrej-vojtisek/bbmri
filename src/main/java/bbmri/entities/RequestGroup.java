package bbmri.entities;

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

    @Enumerated(EnumType.STRING)
    private RequestState requestState;

    private Date created;

    private Date lastModification;

    @ManyToOne//(cascade = CascadeType.ALL)
    private Project project;

    @ManyToOne
    private Biobank biobank;

    @OneToMany
       @JoinTable(name="requestGroup_requests",
                  joinColumns = @JoinColumn( name="requestGroup_id"),
                  inverseJoinColumns = @JoinColumn( name="request_id"))
    private List<Request> requests = new ArrayList<Request>();

    public RequestGroup() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
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
                ", requestState=" + requestState +
                ", created=" + created +
                ", lastModification=" + lastModification +
                ", project=" + project +
                ", biobank=" + biobank +
                ", requests=" + requests +
                '}';
    }
}
