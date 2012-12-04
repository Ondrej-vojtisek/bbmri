package bbmri.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 9:55
 * To change this template use File | Settings | File Templates.
 */

@Table(name = "Request")
@Entity
public class Request {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Sample sample;

    @ManyToOne(cascade = CascadeType.ALL)
    private Project project;

    /*
    @ManyToOne (cascade=CascadeType.ALL)
    private Researcher reseacher;
     */
    private Date date;

    @Enumerated(EnumType.STRING)
    private RequestState requestState;

    public Request() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public RequestState getRequestState() {
        return requestState;
    }

    public void setRequestState(RequestState requestState) {
        this.requestState = requestState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (id != null ? !id.equals(request.id) : request.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", sample=" + sample +
                ", project=" + project +
                ", date=" + date +
                ", requestState=" + requestState +
                '}';
    }
}
