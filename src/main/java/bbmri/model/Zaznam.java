package bbmri.model;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "Zaznam")
@Entity
public class Zaznam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String jmeno;
    private int pocet;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getJmeno() { return jmeno;  }
    public void setJmeno(String jmeno) { this.jmeno = jmeno; }
    public int getPocet() { return pocet; }
    public void setPocet(int pocet) { this.pocet = pocet; }

      @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Zaznam)) {
            return false;
        }
        Zaznam other = (Zaznam) object;
        if (this.id == null || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Zaznam - id=" + id;
    }

}
