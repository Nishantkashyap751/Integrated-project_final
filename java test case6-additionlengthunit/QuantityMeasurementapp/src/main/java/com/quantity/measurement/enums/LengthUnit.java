package com.quantity.measurement.enums;

public enum LengthUnit {
FEET(1.0),
INCH(1.0 / 12),
YARDS(3.0),
CENTIMETERS(0.0328084); // because 1 cm = 0.0328084 feet
     private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double toBase(double value) {
        return value * conversionFactor;
    }

     public double getConversionFactor() {
        return conversionFactor;
    }
}
