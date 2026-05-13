package com.quantity.measurement.model;

import com.quantity.measurement.enums.IMeasurable;

public class Quantity<U extends IMeasurable> {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit should not be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Other quantity and target unit must not be null");
        }
        
        double thisInBase = this.unit.convertToBaseUnit(this.value);
        double otherInBase = other.unit.convertToBaseUnit(other.value);
        
        double sumInBase = thisInBase + otherInBase;
        double resultValue = targetUnit.convertFromBaseUnit(sumInBase);
        
        return new Quantity<>(resultValue, targetUnit);
    }

    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit should not be null");
        }
        
        double valueInBase = this.unit.convertToBaseUnit(this.value);
        double convertedValue = targetUnit.convertFromBaseUnit(valueInBase);
        
        return new Quantity<>(convertedValue, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Quantity<?> other = (Quantity<?>) obj;
        
        // If units are from different categories (e.g. Length vs Weight), they are not equal
        if (!this.unit.getClass().equals(other.unit.getClass())) {
            return false;
        }
        
        double thisInBase = this.unit.convertToBaseUnit(this.value);
        double otherInBase = ((IMeasurable)other.unit).convertToBaseUnit(other.value);
        
        return Math.abs(thisInBase - otherInBase) < EPSILON;
    }

    @Override
    public String toString() {
        return String.format("Quantity(%.1f, %s)", value, unit.getUnitName());
    }
}
