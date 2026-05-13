package com.app.quantitymeasurement.unit;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS(1.0, 0.0),
    FAHRENHEIT(5.0 / 9.0, 32.0);

    private final double factor;
    private final double offset;

    TemperatureUnit(double factor, double offset) {
        this.factor = factor;
        this.offset = offset;
    }

    @Override
    public double getConversionFactor() {
        return factor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return (value - offset) * factor;
    }

    @Override
    public double convertFromBaseUnit(double value) {
        return (value / factor) + offset;
    }

    @Override
    public boolean supportsArithmetic() {
        return false;
    }

    @Override
    public void validateOperationSupport(String operation) {
        if ("ADD".equals(operation) || "SUBTRACT".equals(operation)) {
            throw new IllegalArgumentException("Arithmetic operations are not supported for Temperature");
        }
    }
}
