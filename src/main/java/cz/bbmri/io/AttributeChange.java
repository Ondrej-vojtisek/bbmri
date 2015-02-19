package cz.bbmri.io;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class AttributeChange {

    public AttributeChange(String propertyName, String newValue) {
        this.propertyName = propertyName;
        this.newValue = newValue;
    }

    public AttributeChange(String propertyName, String originalValue, String newValue) {
        this.propertyName = propertyName;
        this.originalValue = originalValue;
        this.newValue = newValue;
    }

    /**
     * Name of attribute which was changed. e.g. Patient.PROP_ID
     */
    private String propertyName;

    /**
     * Value of property before operation
     */
    private String originalValue;

    /**
     * Value of property after operation
     */
    private String newValue;

    @Override
    public String toString() {
        return propertyName + ": " + originalValue + " -> " + newValue + "\n";
    }
}
