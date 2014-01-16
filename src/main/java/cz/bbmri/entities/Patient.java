package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.Sex;

import javax.persistence.*;
import java.io.Serializable;

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
