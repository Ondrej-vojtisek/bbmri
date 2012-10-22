package bbmri.entities;

import org.hibernate.annotations.Columns;

import javax.persistence.*;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Column;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 10.10.12
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */

@Table(name = "Researcher")
@Entity
public class Researcher implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private boolean online;
    //naive temporal prosthesis
    private String password;

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(mappedBy = "researchers", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<Project>();

    @ManyToMany(mappedBy = "admins", cascade = CascadeType.ALL)
    private List<Biobank> biobanks = new ArrayList<Biobank>();

     public Researcher(){}

     public Researcher(String name, String surname){
        this.name = name;
        this.surname = surname;
        this.online = false;
    }


    public List<Biobank> getBiobanks() {
        return biobanks;
    }

    public void setBiobanks(List<Biobank> biobanks) {
        this.biobanks = biobanks;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Researcher)) {
            return false;
        }
        Researcher other = (Researcher) object;
        if (this.id == null || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "id=" + id;// + " name: " + name + " surname: " + surname + " online: " + online;
    }
}


