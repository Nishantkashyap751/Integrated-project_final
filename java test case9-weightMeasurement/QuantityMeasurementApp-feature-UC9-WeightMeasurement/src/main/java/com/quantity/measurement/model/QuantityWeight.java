
package com.quantity.measurement.model;

import com.quantity.measurement.enums.WeightUnit;

public class QuantityWeight {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final WeightUnit unit;

    // Constructor
    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit should not be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        this.value = value;
        this.unit = unit;
    }

    // Getters
    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    // ========================
    // CONVERT (UC9)
    // Original -> Kilogram (Base) -> TargetUnit
    // ========================
    public QuantityWeight convertTo(WeightUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit should not be null");
        }
        double inKilogram = this.unit.convertToBaseUnit(this.value);
        double targetValue = targetUnit.convertFromBaseUnit(inKilogram);
        return new QuantityWeight(targetValue, targetUnit);
    }

    // ========================
    // ADD with explicit target unit (UC9 — mirrors UC7)
    // ========================
    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Other quantity and target unit must not be null");
        }
        if (!Double.isFinite(other.value)) {
            throw new IllegalArgumentException("Invalid value in other quantity");
        }
        double sumInKg = this.unit.convertToBaseUnit(this.value)
                + other.unit.convertToBaseUnit(other.value);
        double result = targetUnit.convertFromBaseUnit(sumInKg);
        return new QuantityWeight(result, targetUnit);
    }

    // ========================
    // ADD with implicit target unit (UC9 — mirrors UC6)
    // Result expressed in the unit of the first operand
    // ========================
    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    // ========================
    // EQUALS (UC9)
    // Cross-category safety: rejects any non-QuantityWeight object
    // ========================
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityWeight other = (QuantityWeight) obj;

        double thisInKg  = this.unit.convertToBaseUnit(this.value);
        double otherInKg = other.unit.convertToBaseUnit(other.value);

        return Math.abs(thisInKg - otherInKg) < EPSILON;
    }

    @Override
    public int hashCode() {
        // Normalise to base unit so equal objects produce the same hash
        return Double.hashCode(Math.round(this.unit.convertToBaseUnit(this.value) / EPSILON) * EPSILON);
    }

    @Override
    public String toString() {
        return "QuantityWeight{value=" + value + ", unit=" + unit + "}";
    }
}