package com.quantity.measurement.model;

import com.quantity.measurement.enums.IMeasurable;

public class QuantityModel<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public QuantityModel(double value, U unit) {
        if (unit == null) throw new NullPointerException("Unit must not be null");
        this.value = value;
        this.unit = unit;
    }

    public double getValue() { return value; }
    public U getUnit() { return unit; }

    @Override
    public String toString() {
        return "QuantityModel(" + value + ", " + unit + ")";
    }
}