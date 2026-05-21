package com.app.quantitymeasurement.unit;

public enum VolumeUnit implements IMeasurable {

    LITRE(1.0),
    MILLILITRE(1.0 / 1000),
    GALLON(3.78);

    private final double toLitreFactor;

    VolumeUnit(double toLitreFactor) {
        this.toLitreFactor = toLitreFactor;
    }

    @Override
    public double getConversionFactor() {
        return toLitreFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return value * toLitreFactor;
    }

    @Override
    public double convertFromBaseUnit(double value) {
        return value / toLitreFactor;
    }
}
