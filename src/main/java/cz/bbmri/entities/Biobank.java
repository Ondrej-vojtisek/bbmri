package cz.bbmri.entities;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public final static String BIOBANK_FOLDER = File.separator + "biobank_files";
    public final static String BIOBANK_FOLDER_PATH = BIOBANK_FOLDER + File.separator;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    private String name;

    private String address;

    @OneToMany(mappedBy = "biobank", fetch = FetchType.EAGER)
    private Set<BiobankAdministrator> biobankAdministrators = new HashSet<BiobankAdministrator>();

    @OneToMany(mappedBy = "biobank")
    private List<SampleRequest> sampleRequests = new ArrayList<SampleRequest>();

    @OneToMany(mappedBy = "biobank")
    private List<Patient> patients = new ArrayList<Patient>();

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

    public List<SampleRequest> getSampleRequests() {
        return sampleRequests;
    }

    public void setSampleRequests(List<SampleRequest> sampleRequests) {
        this.sampleRequests = sampleRequests;
    }

    public Set<BiobankAdministrator> getBiobankAdministrators() {
        return biobankAdministrators;
    }

    public void setBiobankAdministrators(Set<BiobankAdministrator> biobankAdministrators) {
        this.biobankAdministrators = biobankAdministrators;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
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
