package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.AttachmentType;

import javax.persistence.*;
import java.io.File;
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

    public final static String PROJECT_FOLDER = File.separator + "project_files";
    public final static String PROJECT_FOLDER_PATH = PROJECT_FOLDER + File.separator;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AttachmentType attachmentType;

    @ManyToOne
    private Project project;

    private String fileName;
    private Long size;
    private String contentType;
    private String absolutePath;



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

    public AttachmentType getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getParentFolder(){
        if(absolutePath == null || fileName == null){
            return null;
        }
        String s = absolutePath;
        return s.substring(0, s.lastIndexOf(fileName));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attachment that = (Attachment) o;

        if (!contentType.equals(that.contentType)) return false;
        if (!fileName.equals(that.fileName)) return false;
        if (!project.equals(that.project)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fileName.hashCode();
        result = 31 * result + contentType.hashCode();
        result = 31 * result + project.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "attachmentType=" + attachmentType +
                ", contentType='" + contentType + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
