package cz.bbmri.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class StorageMethodology implements Serializable {

    public static final String PROP_METHOLOGY = "methology";
   	public static final String PROP_TEMPERATURE_CELSIUS = "temperatureCelsius";
   	public static final String PROP_STS = "sts";
   	public static final String PROP_EXPIRATION = "expiration";
   	public static final String PROP_MEDIUM = "medium";
   	public static final String PROP_SAMPLE = "sample";

    private String methology;
   	private Float temperatureCelsius;
   	private Boolean sts;
   	private Date expiration;
   	private String medium;
   	private Sample sample;
   	private long sampleId;

    public String getMethology() {
        return methology;
    }

    public void setMethology(String methology) {
        this.methology = methology;
    }

    public Float getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(Float temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public Boolean getSts() {
        return sts;
    }

    public void setSts(Boolean sts) {
        this.sts = sts;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        if(sample != null){
            sampleId = sample.getId();
        }
        this.sample = sample;
    }

    public long getSampleId() {
        return sampleId;
    }

    public void setSampleId(long sampleId) {
        this.sampleId = sampleId;
    }

    @Override
    public String toString() {
        return "StorageMethodology{" +
                "methology='" + methology + '\'' +
                ", temperature=" + temperatureCelsius +
                ", sts=" + sts +
                ", expiration=" + expiration +
                ", medium='" + medium + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StorageMethodology that = (StorageMethodology) o;

        if (sampleId != that.sampleId) return false;
        if (methology != null ? !methology.equals(that.methology) : that.methology != null) return false;
        if (temperatureCelsius != null ? !temperatureCelsius.equals(that.temperatureCelsius) : that.temperatureCelsius != null) return false;
        if (sts != null ? !sts.equals(that.sts) : that.sts != null) return false;
        if (expiration != null ? !expiration.equals(that.expiration) : that.expiration != null) return false;
        return !(medium != null ? !medium.equals(that.medium) : that.medium != null);

    }

    @Override
    public int hashCode() {
        int result = methology != null ? methology.hashCode() : 0;
        result = 31 * result + (temperatureCelsius != null ? temperatureCelsius.hashCode() : 0);
        result = 31 * result + (sts != null ? sts.hashCode() : 0);
        result = 31 * result + (expiration != null ? expiration.hashCode() : 0);
        result = 31 * result + (medium != null ? medium.hashCode() : 0);
        result = 31 * result + (int) (sampleId ^ (sampleId >>> 32));
        return result;
    }
}
