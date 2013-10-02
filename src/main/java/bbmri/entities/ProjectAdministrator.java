package bbmri.entities;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 26.9.13
 * Time: 14:31
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "ProjectAdministrator")
@Entity
public class ProjectAdministrator {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

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
        if (o == null || getClass() != o.getClass()) return false;

        ProjectAdministrator that = (ProjectAdministrator) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
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
