package com.quantity.measurement.model;

import com.quantity.measurement.enums.LengthUnit;

public class QuantityLength {

    private final double value;
    private final LengthUnit unit;
    private final double EPSILON = 1e-6;

    // Constructor
    public QuantityLength(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite");
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

    // STATIC CONVERSION METHOD 
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid Numeric Value");
        }

        if (source == null || target == null) {
            throw new IllegalArgumentException("Units shouldn't be empty!!!");
        }

        // Step 1: Convert to base (inches or base unit defined in enum)
        double baseValue = source.toBase(value);

        // Step 2: Convert base → target
        return baseValue / target.getConversionFactor();
    }

   
    public QuantityLength convertTo(LengthUnit targetUnit) {
        double convertedValue = convert(this.value, this.unit, targetUnit);
        return new QuantityLength(convertedValue, targetUnit);
    }

    // OVERLOADED ADDITION METHODS
    public static QuantityLength add(QuantityLength q1, QuantityLength q2, LengthUnit targetUnit) {
        if (q1 == null || q2 == null) {
            throw new IllegalArgumentException("Quantities cannot be null");
        }
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double base1 = q1.getUnit().toBase(q1.getValue());
        double base2 = q2.getUnit().toBase(q2.getValue());
        double sumInBase = base1 + base2;

        double targetValue = sumInBase / targetUnit.getConversionFactor();
        return new QuantityLength(targetValue, targetUnit);
    }

    public static QuantityLength add(QuantityLength q1, QuantityLength q2) {
        if (q1 == null || q2 == null) {
            throw new IllegalArgumentException("Quantities cannot be null");
        }
        return add(q1, q2, q1.getUnit());
    }

    public static QuantityLength add(double val1, LengthUnit unit1, double val2, LengthUnit unit2, LengthUnit targetUnit) {
        QuantityLength q1 = new QuantityLength(val1, unit1);
        QuantityLength q2 = new QuantityLength(val2, unit2);
        return add(q1, q2, targetUnit);
    }

    public QuantityLength add(QuantityLength other) {
        return add(this, other, this.unit);
    }

    // EQUALITY CHECK 
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityLength other = (QuantityLength) obj;

        double thisBase = this.unit.toBase(this.value);
        double otherBase = other.unit.toBase(other.value);

        // epsilon comparison for precision
        return Math.abs(thisBase - otherBase) < EPSILON;
    }

    @Override
    public String toString() {
        return "QuantityLength{" +
                "value=" + value +
                ", unit=" + unit +
                '}';
    }
}