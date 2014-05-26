package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.ProjectAttachmentType;

import javax.persistence.*;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Entity
@Table
public class ProjectAttachment extends Attachment {

    /**
     * Type of attachment - flag to make attachment list more clear
     */
    @Enumerated(EnumType.STRING)
    private ProjectAttachmentType projectAttachmentType;

    @ManyToOne
    private Project project;

    public ProjectAttachmentType getProjectAttachmentType() {
        return projectAttachmentType;
    }

    public void setProjectAttachmentType(ProjectAttachmentType projectAttachmentType) {
        this.projectAttachmentType = projectAttachmentType;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    }
