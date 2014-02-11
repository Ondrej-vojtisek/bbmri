package cz.bbmri.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 10.10.12
 * Time: 21:21
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "Sample")
@Entity
public class Sample implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID", nullable = false)
    private Long id;

//    @Column(name = "SAMPLE_ID")
//    private String sampleID;
//
//    @Column(name = "NUM_OF_SAMPLES")
//    private Integer numOfSamples;
//
//    @Column(name = "NUM_OF_AVAILABLE_SAMPLES")
//    private Integer numOfAvailable;
//
//    @Column(name = "TISSUE_TYPE")
//    private String tissueType;
//
//    @Column(name = "TNM")
//    private String TNM;
//
//    @Column(name = "PTNM")
//    private String pTNM;

//    Grading is part of morphology
//    @Column(name = "GRADING")
//    private Integer grading;

//    @Column(name = "REMOVAL_DATE")
//    private Date removalDate; /*cutTime or blood take*/
//
//    @Column(name = "FREZING_DATE")
//    private Date freezingDate;
//
//    @Column(name = "DIAGNOSIS")
//    private String diagnosis;
//
//    @Column(name = "MORPHOLOGY")
//    private String morphology;
//
//    @Column(name ="BIOPTICAL_REPORT_YEAR")
//    private String biopticalReportYear;
//
//    @Column(name ="BIOPTICAL_REPORT_NUMBER")
//    private String biopticalReportNumber;
//
//    @Enumerated(EnumType.STRING)
//    private SampleRetrieval retrieved;

    @ManyToOne
    private Module module;

    @OneToMany(mappedBy = "sample")
    List<Request> requests = new ArrayList<Request>();

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getSampleID() {
//        return sampleID;
//    }
//
//    public void setSampleID(String sampleID) {
//        this.sampleID = sampleID;
//    }
//
//    public Integer getNumOfSamples() {
//        return numOfSamples;
//    }
//
//    public void setNumOfSamples(Integer numOfSamples) {
//        this.numOfSamples = numOfSamples;
//    }
//
//    public Integer getNumOfAvailable() {
//        return numOfAvailable;
//    }
//
//    public void setNumOfAvailable(Integer numOfAvailable) {
//        this.numOfAvailable = numOfAvailable;
//    }
//
//    public String getTissueType() {
//        return tissueType;
//    }
//
//    public void setTissueType(String tissueType) {
//        this.tissueType = tissueType;
//    }
//
//    public String getTNM() {
//        return TNM;
//    }
//
//    public void setTNM(String TNM) {
//        this.TNM = TNM;
//    }
//
//    public String getpTNM() {
//        return pTNM;
//    }
//
//    public void setpTNM(String pTNM) {
//        this.pTNM = pTNM;
//    }
//
//    public Date getRemovalTime() {
//        return removalDate;
//    }
//
//    public void setRemovalTime(Date removalTime) {
//        this.removalDate = removalTime;
//    }
//
//    public Date getFreezingTime() {
//        return freezingDate;
//    }
//
//    public void setFreezingTime(Date freezingTime) {
//        this.freezingDate = freezingTime;
//    }
//
//    public String getDiagnosis() {
//        return diagnosis;
//    }
//
//    public void setDiagnosis(String diagnosis) {
//        this.diagnosis = diagnosis;
//    }
//
//    public Date getRemovalDate() {
//        return removalDate;
//    }
//
//    public void setRemovalDate(Date removalDate) {
//        this.removalDate = removalDate;
//    }
//
//    public Date getFreezingDate() {
//        return freezingDate;
//    }
//
//    public void setFreezingDate(Date freezingDate) {
//        this.freezingDate = freezingDate;
//    }
//
//    public String getMorphology() {
//        return morphology;
//    }
//
//    public void setMorphology(String morphology) {
//        this.morphology = morphology;
//    }
//
//    public String getBiopticalReportYear() {
//        return biopticalReportYear;
//    }
//
//    public void setBiopticalReportYear(String biopticalReportYear) {
//        this.biopticalReportYear = biopticalReportYear;
//    }
//
//    public String getBiopticalReportNumber() {
//        return biopticalReportNumber;
//    }
//
//    public void setBiopticalReportNumber(String biopticalReportNumber) {
//        this.biopticalReportNumber = biopticalReportNumber;
//    }
//
//    public SampleRetrieval getRetrieved() {
//        return retrieved;
//    }
//
//    public void setRetrieved(SampleRetrieval retrieved) {
//        this.retrieved = retrieved;
//    }



    /**
     * Return true if all necessary field are filled
     * TODO - tohle zatim nema smysl. Potreba rozlisit STS/LTS apod..
     */

    public boolean getFilled(){
      /* if(id == null || TNM == null || pTNM == null || diagnosis == null
               || grading == null || numOfAvailable == null
               || numOfSamples == null
               || sampleID == null || tissueType == null || biobank == null){
           return false;
       }  */
       return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Sample)) {
            return false;
        }
        Sample other = (Sample) object;
        if (this.id == null || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sample{" +
                "id=" + id +
//                ", sampleID='" + sampleID + '\'' +
//                ", numOfSamples=" + numOfSamples +
//                ", numOfAvailable=" + numOfAvailable +
//                ", tissueType='" + tissueType + '\'' +
//                ", TNM='" + TNM + '\'' +
//                ", pTNM='" + pTNM + '\'' +
//                ", morphology='" + morphology +
                /*            ", removalTime=" + removalTime +
       ", freezingTime=" + freezingTime +     */
//                ", diagnosis='" + diagnosis + '\'' +
                '}';
    }


}

