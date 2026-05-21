package com.app.quantitymeasurement.unit;

public enum WeightUnit implements IMeasurable {

    KILOGRAM(1.0),
    GRAM(1.0 / 1000),
    TONNE(1000.0);

    private final double toKgFactor;

    WeightUnit(double toKgFactor) {
        this.toKgFactor = toKgFactor;
    }

    @Override
    public double getConversionFactor() {
        return toKgFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return value * toKgFactor;
    }

    @Override
    public double convertFromBaseUnit(double value) {
        return value / toKgFactor;
    }
}
