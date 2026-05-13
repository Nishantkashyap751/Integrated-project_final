package com.quantity.measurement.enums;

public enum WeightUnit implements IMeasurable {

    KILOGRAM(1.0, "KILOGRAM"),
    GRAM(0.001, "GRAM"),
    POUND(0.453592, "POUND");

    private final double toKilogramFactor;
    private final String unitName;

    WeightUnit(double toKilogramFactor, String unitName) {
        this.toKilogramFactor = toKilogramFactor;
        this.unitName = unitName;
    }

    @Override
    public double getConversionFactor() {
        return toKilogramFactor;
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
        return value * toKilogramFactor;
    }

    @Override
    public double convertFromBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return value / toKilogramFactor;
    }
}