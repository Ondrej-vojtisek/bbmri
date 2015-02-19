package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.Sex;
import cz.bbmri.entities.exception.DifferentEntityException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Patient who agreed to use his biological material (rest after his treatment) for research purpose. Data must be anonymized
 * thats why only month and year of birth are stored. Also only institutionId is stored instead of national ID.
 * <p/>
 * Structure:
 * Biobank
 * - Patient
 * - LTS module
 * - Samples
 * - STS module
 * - Samples
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Table
@Entity
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String PROP_ID = "id";
    public static final String PROP_SEX = "sex";
    public static final String PROP_BIRTHYEAR = "birthYear";
    public static final String PROP_BIRTHMONTH = "birthMonth";
    public static final String PROP_CONSENT = "consent";
    public static final String PROP_MODULESTS = "moduleSTS";
    public static final String PROP_MODULELTS = "moduleLTS";
    public static final String PROP_BIOBANK = "biobank";
    public static final String PROP_INSTITUTIONID = "institutionId";


    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    /**
     * ID from hospital, where patient was treated
     */
    @Column(unique = true, length = 10)
    private String institutionId;

    /**
     * Male of female
     */
    @Enumerated(EnumType.STRING)
    private Sex sex;

    /**
     * Year of birth
     */
    private Integer birthYear;

    /**
     * Month of birth
     */
    private Integer birthMonth;

    /**
     * Patient's agreement that he allows to use treatment data (and biological material) for research
     */
    private boolean consent;

    @OneToOne(mappedBy = "patient")
    private ModuleSTS moduleSTS;

    @OneToOne(mappedBy = "patient")
    private ModuleLTS moduleLTS;

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

    public ModuleSTS getModuleSTS() {
        return moduleSTS;
    }

    public void setModuleSTS(ModuleSTS moduleSTS) {
        this.moduleSTS = moduleSTS;
    }

    public ModuleLTS getModuleLTS() {
        return moduleLTS;
    }

    public void setModuleLTS(ModuleLTS moduleLTS) {
        this.moduleLTS = moduleLTS;
    }

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

//    public boolean attributeEquality(Patient patient) throws DifferentEntityException {
//        // ID must match
//        if (!this.equals(patient)) {
//            throw new DifferentEntityException("Compared patients are not the same.");
//        }
//
//        if (consent != patient.consent) return false;
//        if (birthMonth != null ? !birthMonth.equals(patient.birthMonth) : patient.birthMonth != null) return false;
//        if (birthYear != null ? !birthYear.equals(patient.birthYear) : patient.birthYear != null) return false;
//        if (!id.equals(patient.id)) return false;
//        if (institutionId != null ? !institutionId.equals(patient.institutionId) : patient.institutionId != null)
//            return false;
//        if (sex != patient.sex) return false;
//
//        return true;
//
//    }


    public Integer getAge() {
        if (birthMonth == null && birthYear == null) {
            return null;
        }

        int year = 0;
        int month = 0;

        if (birthMonth != null) {
            month = birthMonth;
        }

        if (birthYear != null) {
            year = birthYear;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        /* calendar month is zero based - January is 0, February 1 etc.*/
        int monthNow = cal.get(Calendar.MONTH) - 1;
        int yearNow = cal.get(Calendar.YEAR);

        Integer age = yearNow - year;
        if (monthNow < month) age = age - 1;

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
