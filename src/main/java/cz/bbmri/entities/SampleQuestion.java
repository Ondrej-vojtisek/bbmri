package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.RequestState;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:22
 * To change this template use File | Settings | File Templates.
 */

@Table(name = "SampleQuestion")
@Entity
public class SampleQuestion {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "SPECIFICATION", columnDefinition = "TEXT")
    private String specification;

    @ManyToOne//(cascade = CascadeType.MERGE)
    private Biobank biobank;

    @ManyToOne//(cascade = CascadeType.MERGE)
    private Project project;

    @Enumerated(EnumType.STRING)
    private RequestState requestState;

    private boolean processed = false;

    @Type(type = "timestamp")
    private Date created;

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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
                '}';
    }
}
