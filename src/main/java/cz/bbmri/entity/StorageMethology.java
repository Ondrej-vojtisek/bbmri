package cz.bbmri.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class StorageMethology implements Serializable {

    public static final String PROP_METHOLOGY = "methology";
   	public static final String PROP_TEMPERATURE = "temperature";
   	public static final String PROP_STS = "sts";
   	public static final String PROP_EXPIRATION = "expiration";
   	public static final String PROP_REAGENT = "reagent";
   	public static final String PROP_SAMPLE = "sample";

    private String methology;
   	private Float temperature;
   	private Boolean sts;
   	private Date expiration;
   	private String reagent;
   	private Sample sample;
   	private long sampleId;

    public String getMethology() {
        return methology;
    }

    public void setMethology(String methology) {
        this.methology = methology;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Boolean isSts() {
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

    public String getReagent() {
        return reagent;
    }

    public void setReagent(String reagent) {
        this.reagent = reagent;
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
}
