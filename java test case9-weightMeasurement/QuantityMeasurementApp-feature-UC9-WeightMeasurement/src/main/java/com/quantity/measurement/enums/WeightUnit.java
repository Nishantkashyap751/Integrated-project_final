package com.quantity.measurement.enums;

public enum WeightUnit {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double toKilogramFactor;

    WeightUnit(double toKilogramFactor) {
        this.toKilogramFactor = toKilogramFactor;
    }

    public double getConversionFactor() {
        return toKilogramFactor;
    }

    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return value * toKilogramFactor;
    }

    public double convertFromBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return value / toKilogramFactor;
    }
}