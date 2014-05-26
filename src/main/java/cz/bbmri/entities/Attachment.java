package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.ProjectAttachmentType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Attachment of project
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Table
@Entity
abstract public class Attachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    /**
     * File name
     */
    protected String fileName;

    /**
     *  File size
     */
    protected Long size;

    /**
     * Type of content
     */
    protected String contentType;

    /**
     * Path to data storage on server
     */
    protected String absolutePath;

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

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attachment)) return false;

        Attachment that = (Attachment) o;

        if (!absolutePath.equals(that.absolutePath)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return absolutePath.hashCode();
    }
}
