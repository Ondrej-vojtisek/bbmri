package cz.bbmri.entities.enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 19.2.14
 * Time: 15:11
 * To change this template use File | Settings | File Templates.
 */
public enum SampleType {
    DIAGNOSIS_MATERIAL("Diagnosis material"),
    GENOME("Genome"),
    SERUM("Serum"),
    TISSUE("Tissue");

    private final String state;

    private SampleType(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }

}
