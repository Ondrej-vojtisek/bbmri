package bbmri.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.2.13
 * Time: 21:23
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "Attachment")
@Entity
public class Attachment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    private String fileName;
    private Long size;
    private String contentType;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PROJECT_ID")
    Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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

        Attachment that = (Attachment) o;

        if (!contentType.equals(that.contentType)) return false;
        if (!fileName.equals(that.fileName)) return false;
        if (!id.equals(that.id)) return false;
        if (!size.equals(that.size)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + fileName.hashCode();
        result = 31 * result + size.hashCode();
        result = 31 * result + contentType.hashCode();
        return result;
    }
}
