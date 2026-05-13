package com.quantity.measurement.enums;

public interface IMeasurable {
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double value);
    double getConversionFactor();
    String getUnitName();
}
