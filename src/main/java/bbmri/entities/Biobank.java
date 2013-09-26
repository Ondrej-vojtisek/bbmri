package bbmri.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 10.10.12
 * Time: 21:20
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "Biobank")
@Entity
public class Biobank implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @OneToMany(mappedBy = "biobank", fetch = FetchType.EAGER)
    private List<BiobankAdministrator> biobankAdministrators = new ArrayList<BiobankAdministrator>();


 /*
    @OneToMany(mappedBy = "biobank")
    private List<BiobankAdministrator> biobankAdministratorList = new ArrayList<BiobankAdministrator>();
 */
    /* There won't be many administrators for one biobank*/
   /* @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name="biobank_administrators",
               joinColumns = @JoinColumn( name="biobank_id"),
               inverseJoinColumns = @JoinColumn( name="user_id"))  */
    //@OneToMany(mappedBy = "biobank", fetch = FetchType.EAGER)
    //private List<User> administrators = new ArrayList<User>();

    /*@OneToMany
    @JoinTable(name="biobank_samples",
               joinColumns = @JoinColumn( name="biobank_id"),
               inverseJoinColumns = @JoinColumn( name="sample_id"))*/
    @OneToMany(mappedBy = "biobank")
    private List<Sample> samples = new ArrayList<Sample>();

   /* @OneToMany
    @JoinTable(name="biobank_requestGroups",
               joinColumns = @JoinColumn( name="biobank_id"),
               inverseJoinColumns = @JoinColumn( name="requestGroup_id"))*/
    @OneToMany(mappedBy = "biobank")
    private List<RequestGroup> requestGroups = new ArrayList<RequestGroup>();

    /*@OneToMany
    @JoinTable(name="biobank_sampleQuestions",
                   joinColumns = @JoinColumn( name="biobank_id"),
                   inverseJoinColumns = @JoinColumn( name="sampleQuestion_id"))*/
    @OneToMany(mappedBy = "biobank")
    private List<SampleQuestion> sampleQuestions = new ArrayList<SampleQuestion>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }
  /*
    public List<User> getAdministrators() {
        return administrators;
    }
    */
   /*
    public void setAdministrators(List<User> administrators) {
        this.administrators = administrators;
    }
    */

    public List<RequestGroup> getRequestGroups() {
        return requestGroups;
    }

    public void setRequestGroups(List<RequestGroup> requestGroups) {
        this.requestGroups = requestGroups;
    }

    public List<SampleQuestion> getSampleQuestions() {
        return sampleQuestions;
    }

    public void setSampleQuestions(List<SampleQuestion> sampleQuestions) {
        this.sampleQuestions = sampleQuestions;
    }

    public List<BiobankAdministrator> getBiobankAdministrators() {
        return biobankAdministrators;
    }

    public void setBiobankAdministrators(List<BiobankAdministrator> biobankAdministrators) {
        this.biobankAdministrators = biobankAdministrators;
    }


       @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Biobank)) {
            return false;
        }
        Biobank other = (Biobank) object;
        if (this.id == null || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Biobank{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
