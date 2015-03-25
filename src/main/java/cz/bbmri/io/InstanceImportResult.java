package cz.bbmri.io;

import cz.bbmri.entity.enumeration.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * ImportResult describes what happened with given object after import.
 * If it was added, upgraded or nothing was changed.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class InstanceImportResult {

    public InstanceImportResult(String objectName) {
        this.objectName = objectName;
    }

    /**
     * Identifier of object. For instance patient.
     */
    private Long identifier;

    /**
     * List of changes to given object.
     */
    private List<AttributeChange> attributeChanges = new ArrayList<AttributeChange>();

    /**
     * Name of entity
     */
    private String objectName;

    /**
     * Result of action. Was object changed or it is the same?
     * If object is unchanged then propertyName, originalValue and newValue can be NULL
     */
    private Status status;

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public List<AttributeChange> getAttributeChanges() {
        return attributeChanges;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void addChange(String propertyName, Object originalValue, Object newValue) {

        AttributeChange attributeChange;

        if (originalValue == null) {
            attributeChange = new AttributeChange(propertyName, newValue.toString());
        } else {
            attributeChange = new AttributeChange(propertyName, originalValue.toString(), newValue.toString());
        }

        attributeChanges.add(attributeChange);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InstanceImportResult)) return false;

        InstanceImportResult that = (InstanceImportResult) o;

        if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) return false;
        if (objectName != null ? !objectName.equals(that.objectName) : that.objectName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = identifier != null ? identifier.hashCode() : 0;
        result = 31 * result + (objectName != null ? objectName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return objectName + " " + status + " ID: " + identifier +
                " changes=" + attributeChanges;
    }
}
