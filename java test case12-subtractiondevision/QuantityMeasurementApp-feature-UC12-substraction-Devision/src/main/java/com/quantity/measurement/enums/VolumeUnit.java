package com.quantity.measurement.enums;

public enum VolumeUnit implements IMeasurable {

    LITRE(1.0, "LITRE"),
    MILLILITRE(0.001, "MILLILITRE"),
    GALLON(3.78541, "GALLON");

    private final double toLitreFactor;
    private final String unitName;

    VolumeUnit(double toLitreFactor, String unitName) {
        this.toLitreFactor = toLitreFactor;
        this.unitName = unitName;
    }

    @Override
    public double getConversionFactor() {
        return toLitreFactor;
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
        return value * toLitreFactor;
    }

    @Override
    public double convertFromBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return value / toLitreFactor;
    }
}
