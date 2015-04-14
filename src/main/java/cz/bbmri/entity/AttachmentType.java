/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 *
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 *
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Masaryk University
 * License Type: Academic
 */
package cz.bbmri.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttachmentType implements Serializable {

    public AttachmentType() {
    }

    public AttachmentType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //    Must match the predefined table records from visual paradigm project

    public static final AttachmentType CALIBRATION_PROTOCOL = new AttachmentType(1, "calibration");
    public static final AttachmentType ETHICAL_AGREEMENT = new AttachmentType(2, "ethical");
    public static final AttachmentType MATERIAL_TRANSFER_AGREEMENT = new AttachmentType(3, "mta");
    public static final AttachmentType OTHER = new AttachmentType(4, "other");
    public static final AttachmentType CONSENT = new AttachmentType(5, "consent");

    public static final String PROP_ID = "id";
    public static final String PROP_NAME = "name";
    public static final String PROP_ATTACHMENT = "attachment";

    private int id;
    private String name;

    private Set<Attachment> attachment = new HashSet<Attachment>();

    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return name;
    }

    public void setAttachment(Set<Attachment> value) {
        this.attachment = value;
    }

    public Set<Attachment> getAttachment() {
        return attachment;
    }

    public static List<AttachmentType> getBiobankAttachmentType() {
        List<AttachmentType> biobankAttachmentType = new ArrayList<AttachmentType>();

        biobankAttachmentType.add(CALIBRATION_PROTOCOL);
        biobankAttachmentType.add(CONSENT);
        biobankAttachmentType.add(OTHER);

        return biobankAttachmentType;
    }

    public static List<AttachmentType> getProjectAttachmentType() {
        List<AttachmentType> projectAttachmentType = new ArrayList<AttachmentType>();

        projectAttachmentType.add(ETHICAL_AGREEMENT);
        projectAttachmentType.add(MATERIAL_TRANSFER_AGREEMENT);
        projectAttachmentType.add(OTHER);

        return projectAttachmentType;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttachmentType)) return false;

        AttachmentType that = (AttachmentType) o;

        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }
}
