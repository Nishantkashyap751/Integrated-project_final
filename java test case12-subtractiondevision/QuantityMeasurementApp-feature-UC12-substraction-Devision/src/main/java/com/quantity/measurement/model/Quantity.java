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
        validateOperands(other);
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit must not be null");
        }
        
        double thisInBase = this.unit.convertToBaseUnit(this.value);
        double otherInBase = other.unit.convertToBaseUnit(other.value);
        
        double sumInBase = thisInBase + otherInBase;
        double resultValue = targetUnit.convertFromBaseUnit(sumInBase);
        
        return new Quantity<>(round(resultValue), targetUnit);
    }

    /**
     * Subtracts another quantity from this quantity.
     * Result is in the same unit as this quantity.
     */
    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    /**
     * Subtracts another quantity from this quantity and converts to target unit.
     */
    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateOperands(other);
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit must not be null");
        }
        
        double thisInBase = this.unit.convertToBaseUnit(this.value);
        double otherInBase = other.unit.convertToBaseUnit(other.value);
        
        double diffInBase = thisInBase - otherInBase;
        double resultValue = targetUnit.convertFromBaseUnit(diffInBase);
        
        return new Quantity<>(round(resultValue), targetUnit);
    }

    /**
     * Divides this quantity by another quantity to return a dimensionless ratio.
     * Throws ArithmeticException if divisor is zero.
     */
    public double divide(Quantity<U> other) {
        validateOperands(other);
        
        double thisInBase = this.unit.convertToBaseUnit(this.value);
        double otherInBase = other.unit.convertToBaseUnit(other.value);
        
        if (Math.abs(otherInBase) < 1e-9) {
            throw new ArithmeticException("Division by zero");
        }
        
        return thisInBase / otherInBase;
    }

    private void validateOperands(Quantity<U> other) {
        if (other == null) {
            throw new IllegalArgumentException("Other quantity must not be null");
        }
        // UC12: Confirm both quantities share the same category
        if (!this.unit.getClass().equals(other.unit.getClass())) {
            throw new IllegalArgumentException("Cross-category operations are not allowed");
        }
        if (!Double.isFinite(other.value)) {
            throw new IllegalArgumentException("Invalid value in other quantity");
        }
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit should not be null");
        }
        
        double valueInBase = this.unit.convertToBaseUnit(this.value);
        double convertedValue = targetUnit.convertFromBaseUnit(valueInBase);
        
        return new Quantity<>(round(convertedValue), targetUnit);
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
        return String.format("Quantity(%.2f, %s)", value, unit.getUnitName());
    }
}

