package bbmri.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String sampleID;
    private Integer numOfSamples;
    private Integer numOfAvailable;
    private String tissueType;
    private String TNM;
    private String pTNM;
    private Integer grading;
    private Date removalTime; /*cutTime or blood take*/
    private Date freezingTime;
    private String diagnosis;

    @ManyToOne(cascade = CascadeType.ALL)
    private Biobank biobank;

    @OneToMany(mappedBy = "sample", cascade = CascadeType.ALL)
    List<Request> requests = new ArrayList<Request>();

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
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

    public String getSampleID() {
        return sampleID;
    }

    public void setSampleID(String sampleID) {
        this.sampleID = sampleID;
    }

    public Integer getNumOfSamples() {
        return numOfSamples;
    }

    public void setNumOfSamples(Integer numOfSamples) {
        this.numOfSamples = numOfSamples;
    }

    public Integer getNumOfAvailable() {
        return numOfAvailable;
    }

    public void setNumOfAvailable(Integer numOfAvailable) {
        this.numOfAvailable = numOfAvailable;
    }

    public String getTissueType() {
        return tissueType;
    }

    public void setTissueType(String tissueType) {
        this.tissueType = tissueType;
    }

    public String getTNM() {
        return TNM;
    }

    public void setTNM(String TNM) {
        this.TNM = TNM;
    }

    public String getpTNM() {
        return pTNM;
    }

    public void setpTNM(String pTNM) {
        this.pTNM = pTNM;
    }

    public Integer getGrading() {
        return grading;
    }

    public void setGrading(Integer grading) {
        this.grading = grading;
    }

    public Date getRemovalTime() {
        return removalTime;
    }

    public void setRemovalTime(Date removalTime) {
        this.removalTime = removalTime;
    }

    public Date getFreezingTime() {
        return freezingTime;
    }

    public void setFreezingTime(Date freezingTime) {
        this.freezingTime = freezingTime;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
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
        return "Sample{" +
                "id=" + id +
                ", sampleID='" + sampleID + '\'' +
                ", numOfSamples=" + numOfSamples +
                ", numOfAvailable=" + numOfAvailable +
                ", tissueType='" + tissueType + '\'' +
                ", TNM='" + TNM + '\'' +
                ", pTNM='" + pTNM + '\'' +
                ", grading=" + grading +
                /*            ", removalTime=" + removalTime +
       ", freezingTime=" + freezingTime +     */
                ", diagnosis='" + diagnosis + '\'' +
                '}';
    }


}

