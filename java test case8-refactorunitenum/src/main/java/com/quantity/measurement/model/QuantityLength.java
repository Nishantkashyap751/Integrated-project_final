package com.quantity.measurement.model;

import com.quantity.measurement.enums.LengthUnit;
import java.util.Objects;

/**
 * QuantityLength represents a length measurement value with its unit.
 * UC8: Simplified to focus on comparison and arithmetic, delegating conversion to LengthUnit.
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
     * UC8: Converts this quantity to a target unit.
     * @param targetUnit The unit to convert to.
     * @return A new QuantityLength in the target unit.
     */
    public QuantityLength convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
        double valueInBase = this.unit.convertToBaseUnit(this.value);
        double convertedValue = targetUnit.convertFromBaseUnit(valueInBase);
        return new QuantityLength(convertedValue, targetUnit);
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
     * UC7/UC8: Addition with explicit target unit specification.
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

        double sumInBase = this.unit.convertToBaseUnit(this.value) + other.unit.convertToBaseUnit(other.value);
        double resultValue = targetUnit.convertFromBaseUnit(sumInBase);
        
        return new QuantityLength(resultValue, targetUnit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuantityLength that = (QuantityLength) o;
        
        // Convert both to base unit for comparison
        double thisInBase = this.unit.convertToBaseUnit(this.value);
        double thatInBase = that.unit.convertToBaseUnit(that.value);
        
        return Math.abs(thisInBase - thatInBase) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.unit.convertToBaseUnit(this.value));
    }

    @Override
    public String toString() {
        return String.format("Quantity(%.3f, %s)", value, unit);
    }
}
