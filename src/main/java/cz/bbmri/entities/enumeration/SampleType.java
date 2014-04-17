package cz.bbmri.entities.enumeration;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
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
