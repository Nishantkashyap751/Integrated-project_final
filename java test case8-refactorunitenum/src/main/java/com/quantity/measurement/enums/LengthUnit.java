package com.quantity.measurement.enums;

/**
 * LengthUnit Enum defines supported length units and their conversion factors
 * relative to a consistent base unit (FEET).
 * UC8: Extracted to a standalone top-level enum with full conversion responsibility.
 */
public enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    /**
     * Converts a value from this unit to the base unit (FEET).
     * @param value The value in this unit.
     * @return The value in the base unit.
     */
    public double convertToBaseUnit(double value) {
        return value * conversionFactor;
    }

    /**
     * Converts a value from the base unit (FEET) to this unit.
     * @param valueInBase The value in the base unit.
     * @return The value in this unit.
     */
    public double convertFromBaseUnit(double valueInBase) {
        return valueInBase / conversionFactor;
    }
}
