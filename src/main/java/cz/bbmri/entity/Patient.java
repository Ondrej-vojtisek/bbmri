package cz.bbmri.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String PROP_ID = "id";
   	public static final String PROP_INSTITUTIONAL_ID = "institutionalId";
   	public static final String PROP_SEX = "sex";
   	public static final String PROP_BIOBANK = "biobank";
   	public static final String PROP_BIRTH_YEAR = "birthYear";
   	public static final String PROP_BIRTH_MONTH = "birthMonth";
   	public static final String PROP_CONSENT = "consent";
   	public static final String PROP_SAMPLE = "sample";


    private long id;
    private String institutionalId;
    private Sex sex;
    private Biobank biobank;
    private short birthYear;
    private Short birthMonth;
    private boolean consent;
    private Set<Sample> sample = new HashSet<Sample>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInstitutionalId() {
        return institutionalId;
    }

    public void setInstitutionalId(String institutionalId) {
        this.institutionalId = institutionalId;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public short getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(short birthYear) {
        this.birthYear = birthYear;
    }

    public Short getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(Short birthMonth) {
        this.birthMonth = birthMonth;
    }

    public boolean isConsent() {
        return consent;
    }

    public void setConsent(boolean consent) {
        this.consent = consent;
    }

    public Set<Sample> getSample() {
        return sample;
    }

    public void setSample(Set<Sample> sample) {
        this.sample = sample;
    }

    public int getAge() {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        /* calendar month is zero based - January is 0, February 1 etc.*/
        int monthNow = cal.get(Calendar.MONTH) - 1;
        int yearNow = cal.get(Calendar.YEAR);

        int age = yearNow - birthYear;
        if (monthNow < birthMonth) age = age - 1;

        return age;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", institutionalId='" + institutionalId + '\'' +
                ", sex=" + sex +
                ", birthYear=" + birthYear +
                ", birthMonth=" + birthMonth +
                ", consent=" + consent +
                '}';
    }
}
