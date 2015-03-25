package cz.bbmri.entity;


import java.io.Serializable;

/**
 * Attachment of project
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class Attachment implements Serializable {

    public static final String PROP_ID = "id";
   	public static final String PROP_FILE_NAME = "fileName";
   	public static final String PROP_SIZE = "size";
   	public static final String PROP_CONTENT_TYPE = "contentType";
   	public static final String PROP_ABSOLUTE_PATH = "absolutePath";
   	public static final String PROP_BIOBANK = "biobank";
   	public static final String PROP_ATTACHMENT_TYPE = "attachmentType";
   	public static final String PROP_PROJECT = "project";

    private long id;
    private String fileName;
    private Long size;
    private String contentType;
    private String absolutePath;

    private Biobank biobank;
    private Project project;
    private AttachmentType attachmentType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public AttachmentType getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
    }

}
