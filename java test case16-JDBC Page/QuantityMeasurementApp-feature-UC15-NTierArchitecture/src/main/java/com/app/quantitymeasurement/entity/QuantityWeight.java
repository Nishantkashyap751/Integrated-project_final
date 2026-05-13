package com.app.quantitymeasurement.entity;

import com.app.quantitymeasurement.unit.WeightUnit;

public class QuantityWeight {
    private final Quantity<WeightUnit> quantity;

    public QuantityWeight(double value, WeightUnit unit) {
        this.quantity = new Quantity<>(value, unit);
    }

    public double getValue() {
        return quantity.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof QuantityWeight)) return false;
        return this.quantity.equals(((QuantityWeight) o).quantity);
    }
}
