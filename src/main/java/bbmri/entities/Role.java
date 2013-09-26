package bbmri.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Table(name = "Role")
@Entity
public class Role implements Serializable{
    private static final long serialVersionUID = 1L;

      @Id
      @GeneratedValue(strategy = GenerationType.TABLE)
      @Column(name = "ID", nullable = false)
      private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToMany
    @JoinTable(name = "role_users", joinColumns = @JoinColumn(name = "role_id"),
              inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<User>();

	public Role(){
        // Nothing to be done
    }

    public Role(String name){
        this.name = name;
	}

	private void setId(Long value){
		this.id = value;
	}

	public Long getId(){
		return id;
	}

	public void setName(String value){
		this.name = value;
	}

	public String getName(){
		return name;
	}

	public void setUser(Set<User> value){
		this.users = value;
	}

	public Set<User> getUser(){
		return users;
	}

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }

        if(obj instanceof Role){
            Role role = (Role) obj;
            return getName().equals(role.getName());
        }
        return false;

    }

    @Override
    public int hashCode(){
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user=" + users +
                '}';
    }
}

