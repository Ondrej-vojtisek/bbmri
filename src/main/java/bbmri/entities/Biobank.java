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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADMIN_ID")
    User administrator;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "COMMITTEE_ID")
    User ethicalCommittee;

    @OneToMany(mappedBy = "biobank", cascade = CascadeType.ALL)
    private List<Sample> samples = new ArrayList<Sample>();

    @OneToMany(mappedBy = "biobank", cascade = CascadeType.ALL)
    private List<RequestGroup> requestGroups = new ArrayList<RequestGroup>();

    public User getEthicalCommittee() {
        return ethicalCommittee;
    }

    public void setEthicalCommittee(User ethicalCommittee) {
        this.ethicalCommittee = ethicalCommittee;
    }

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

    public User getAdministrator() {
        return administrator;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
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
                ", administrator=" + administrator +
                ", ethicalCommittee=" + ethicalCommittee +
                ", samples=" + samples +
                '}';
    }
}
