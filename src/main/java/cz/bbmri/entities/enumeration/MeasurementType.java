package cz.bbmri.entities.enumeration;

/**
 * Defines which physical quantity is measured and which units are used.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public enum MeasurementType {

    TEMPERATURE_C("Temperature", "°C", "Celsius"),
    TEMPERATURE_K("Temperature", "K", "Kelvin");

    private final String physicalQuantity;
    private final String unit;
    private final String name;

    private MeasurementType(String physicalQuantity, String unit, String name) {
        this.physicalQuantity = physicalQuantity;
        this.unit = unit;
        this.name = name;
    }

    public String getPhysicalQuantity() {
        return physicalQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return physicalQuantity + " in " + name;
    }
}
