package bbmri.entities;

import javax.persistence.*;

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

    @Column(name="SPECIFICATION", columnDefinition="TEXT")
      private String specification;

    @ManyToOne(cascade = CascadeType.ALL)
        private Biobank biobank;

    @ManyToOne(cascade = CascadeType.ALL)
        private Project project;

    private boolean processed = false;

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
