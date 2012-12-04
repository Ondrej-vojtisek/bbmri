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
    @Column(columnDefinition = "boolean default false")
    private boolean online;
    //naive temporal prosthesis
    private String password;
    @ManyToMany(mappedBy = "researchers", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Project> projects = new ArrayList<Project>();
    @OneToOne(mappedBy = "administrator", cascade = CascadeType.ALL)
    Biobank biobank;
    @Column(columnDefinition = "boolean default false")
    private boolean administrator;
    @OneToOne(mappedBy = "ethicalCommittee", cascade = CascadeType.ALL)
    Biobank ethicalCommitteeOfBiobank;


    public Researcher() {
    }

    public Researcher(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Biobank getEthicalCommitteeOfBiobank() {
        return ethicalCommitteeOfBiobank;
    }

    public void setEthicalCommitteeOfBiobank(Biobank ethicalCommitteeOfBiobank) {
        this.ethicalCommitteeOfBiobank = ethicalCommitteeOfBiobank;
    }

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

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
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

    public boolean isOperator() {
        return biobank != null;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
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
        return "" + id + " " + name + " " + surname;
    }


}


