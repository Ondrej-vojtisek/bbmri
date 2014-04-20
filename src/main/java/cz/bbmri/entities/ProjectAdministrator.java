package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.Permission;

import javax.persistence.*;

/**
 * Relationship between Project and User
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Table
@Entity
public class ProjectAdministrator {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    /**
     * Level of user authorization to project
     */
    @Enumerated(EnumType.STRING)
    private Permission permission;

    @ManyToOne
    private User user;

    @ManyToOne
    private Project project;


    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(o instanceof ProjectAdministrator)) return false;

        ProjectAdministrator that = (ProjectAdministrator) o;

        if (!project.equals(that.project)) return false;
        if (!user.equals(that.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = project.hashCode();
        result = 31 * result + user.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProjectAdministrator{" +
                "id=" + id +
                ", permission=" + permission +
                ", user=" + user +
                ", project=" + project +
                '}';
    }
}
