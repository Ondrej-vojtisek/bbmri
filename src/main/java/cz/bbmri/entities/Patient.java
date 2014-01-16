package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.Sex;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 8.1.14
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */

@Table(name = "Patient")
@Entity
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

    private String institutionId;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private Integer birthYear;

    private Integer birthMonth;

    private boolean consent;

    @OneToMany(mappedBy = "patient")
    private List<Sample> samples = new ArrayList<Sample>();

    @ManyToOne
    private Biobank biobank;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(Integer birthMonth) {
        this.birthMonth = birthMonth;
    }

    public boolean isConsent() {
        return consent;
    }

    public void setConsent(boolean consent) {
        this.consent = consent;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public Integer getAge() {
        if (birthMonth == null && birthYear == null) {
            return null;
        }

        int year = 0;
        int month = 0;
        int day = 1;

        Calendar calendar = Calendar.getInstance();

        if (birthMonth != null) {
            month = birthMonth;
        }

        if (birthYear != null) {
            year = birthYear;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        /* calendar month is zero based - January is 0, February 1 etc.*/
        int monthNow = cal.get(Calendar.MONTH) -1;
        int yearNow = cal.get(Calendar.YEAR);

        Integer age = yearNow - year;
        if(monthNow < month) age = age -1;

        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;

        Patient patient = (Patient) o;

        if (!id.equals(patient.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", institutionId='" + institutionId + '\'' +
                ", sex=" + sex +
                ", birthYear=" + birthYear +
                ", birthMonth=" + birthMonth +
                ", consent=" + consent +
                '}';
    }
}
