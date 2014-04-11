package cz.bbmri.entities;

import cz.bbmri.entities.infrastructure.Infrastructure;

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
    private final static String BIOBANK_FOLDER_PATH = BIOBANK_FOLDER + File.separator;
    private final static String PATIENT_DATA_FOLDER = "patient_data";
    private final static String MONITORING_DATA_FOLDER = "monitoring_data";
    private final static String TEMPERATURE_FOLDER = "temperature_data";
    private final static String PATIENT_DATA_ARCHIVE_FOLDER = "patient_data_archive";
    private final static String MONITORING_DATA_ARCHIVE_FOLDER = "monitoring_data_archive";
    private final static String TEMPERATURE_ARCHIVE_FOLDER = "temperature_data_archive";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(unique=true, length = 6)
    private String abbreviation;

    private String name;

    private String street;

    private String city;

    @OneToMany(mappedBy = "biobank", fetch = FetchType.EAGER)
    private Set<BiobankAdministrator> biobankAdministrators = new HashSet<BiobankAdministrator>();

    @OneToMany(mappedBy = "biobank")
    private List<SampleQuestion> sampleQuestions = new ArrayList<SampleQuestion>();

    @OneToMany(mappedBy = "biobank")
    private List<Patient> patients = new ArrayList<Patient>();

    @OneToOne(mappedBy = "biobank")
    private Infrastructure infrastructure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SampleQuestion> getSampleQuestions() {
        return sampleQuestions;
    }

    public void setSampleQuestions(List<SampleQuestion> sampleQuestions) {
        this.sampleQuestions = sampleQuestions;
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

    public Infrastructure getInfrastructure() {
        return infrastructure;
    }

    public void setInfrastructure(Infrastructure infrastructure) {
        this.infrastructure = infrastructure;
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
                "abbreviation='" + abbreviation + '\'' +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", id=" + id +
                '}';
    }

    public String getBiobankFolderPath() {
        if (this.getId() == null) {
            return null;
        }

        return Biobank.BIOBANK_FOLDER_PATH + this.getId().toString();
    }

    public String getBiobankMonitoringFolder() {
        if (this.getId() == null) {
            return null;
        }
        return getBiobankFolderPath() + File.separator + Biobank.MONITORING_DATA_FOLDER;
    }

    public String getBiobankMonitoringArchiveFolder() {
        if (this.getId() == null) {
            return null;
        }
        return getBiobankFolderPath() + File.separator + Biobank.MONITORING_DATA_ARCHIVE_FOLDER;
    }

    public String getBiobankPatientDataFolder() {
        if (this.getId() == null) {
            return null;
        }
        return getBiobankFolderPath() + File.separator + Biobank.PATIENT_DATA_FOLDER;
    }

    public String getBiobankPatientArchiveDataFolder() {
        if (this.getId() == null) {
            return null;
        }
        return getBiobankFolderPath() + File.separator + Biobank.PATIENT_DATA_ARCHIVE_FOLDER;
    }

    public String getBiobankTemperatureFolder() {
        if (this.getId() == null) {
            return null;
        }
        return getBiobankFolderPath() + File.separator + Biobank.TEMPERATURE_FOLDER;
    }

    public String getBiobankTemperatureArchiveFolder() {
        if (this.getId() == null) {
            return null;
        }
        return getBiobankFolderPath() + File.separator + Biobank.TEMPERATURE_ARCHIVE_FOLDER;
    }

//    public int compareTo(Biobank compareBiobank) {
//
//        if (this.getId() > compareBiobank.getId())
//            return 1;
//        else if (this.getId() < compareBiobank.getId())
//            return -1;
//        else
//            return 0;
//    }

//    public static Comparator<Biobank> BiobankIdComparator
//            = new Comparator<Biobank>() {
//
//        public int compare(Biobank biobank1, Biobank biobank2) {
//
//            Long biobankAtr1 = biobank1.getId();
//            Long biobankAtr2 = biobank2.getId();
//
//            //ascending order
//            return biobankAtr1.compareTo(biobankAtr2);
//
//        }
//
//    };
//
//    public static Comparator<Biobank> BiobankAddressComparator
//            = new Comparator<Biobank>() {
//
//        public int compare(Biobank biobank1, Biobank biobank2) {
//
//            String biobankAtr1 = biobank1.getAddress();
//            String biobankAtr2 = biobank2.getAddress();
//
//            //ascending order
//            return biobankAtr1.compareTo(biobankAtr2);
//
//        }
//
//    };
//
//    public static Comparator<Biobank> BiobankNameComparator
//            = new Comparator<Biobank>() {
//
//        public int compare(Biobank biobank1, Biobank biobank2) {
//
//            String biobankAtr1 = biobank1.getName();
//            String biobankAtr2 = biobank2.getName();
//
//            //ascending order
//            return biobankAtr1.compareTo(biobankAtr2);
//
//        }
//
//    };
}
