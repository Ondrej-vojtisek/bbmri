package cz.bbmri.entities.webEntities;

import cz.bbmri.entities.enumeration.Permission;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.10.13
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
public class RoleDTO {

    String subject;

    Permission permission;

    Class type;

    Long referenceId;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleDTO roleDTO = (RoleDTO) o;

        if (!subject.equals(roleDTO.subject)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return subject.hashCode();
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "subject='" + subject + '\'' +
                ", permission=" + permission +
                ", type=" + type +
                ", id=" + referenceId +
                '}';
    }
}
