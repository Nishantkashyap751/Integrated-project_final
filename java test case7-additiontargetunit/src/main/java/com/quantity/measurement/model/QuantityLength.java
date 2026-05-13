package com.quantity.measurement.model;

import com.quantity.measurement.enums.LengthUnit;
import java.util.Objects;

/**
 * QuantityLength represents a length measurement value with its unit.
 * It is immutable and provides arithmetic operations.
 */
public class QuantityLength {
    private final double value;
    private final LengthUnit unit;
    private static final double EPSILON = 1e-6;

    public QuantityLength(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    /**
     * UC6: Addition with implicit target unit (defaults to first operand's unit).
     * @param other The second operand.
     * @return A new QuantityLength object in the unit of the first operand.
     */
    public QuantityLength add(QuantityLength other) {
        return add(other, this.unit);
    }

    /**
     * UC7: Addition with explicit target unit specification.
     * @param other The second operand.
     * @param targetUnit The desired unit for the result.
     * @return A new QuantityLength object in the specified target unit.
     */
    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
        if (other == null) {
            throw new IllegalArgumentException("Second operand cannot be null");
        }
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double sumInBase = this.unit.toBaseUnit(this.value) + other.unit.toBaseUnit(other.value);
        double resultValue = targetUnit.fromBaseUnit(sumInBase);
        
        return new QuantityLength(resultValue, targetUnit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuantityLength that = (QuantityLength) o;
        
        // Convert both to base unit for comparison
        double thisInBase = this.unit.toBaseUnit(this.value);
        double thatInBase = that.unit.toBaseUnit(that.value);
        
        return Math.abs(thisInBase - thatInBase) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.unit.toBaseUnit(this.value));
    }

    @Override
    public String toString() {
        return String.format("Quantity(%.3f, %s)", value, unit);
    }
}
