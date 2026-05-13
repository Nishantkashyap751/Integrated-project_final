package com.quantity.measurement.enums;

public enum LengthUnit implements IMeasurable {

    FEET(1.0, "FEET"),
    INCH(1.0 / 12, "INCH"),
    YARDS(3.0, "YARDS"),
    CENTIMETERS(1.0 / 30.48, "CENTIMETERS");

    private final double toFeetFactor;
    private final String unitName;

    LengthUnit(double toFeetFactor, String unitName) {
        this.toFeetFactor = toFeetFactor;
        this.unitName = unitName;
    }

    @Override
    public double getConversionFactor() {
        return toFeetFactor;
    }

    @Override
    public String getUnitName() {
        return unitName;
    }

    @Override
    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return value * toFeetFactor;
    }

    @Override
    public double convertFromBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return value / toFeetFactor;
    }
}