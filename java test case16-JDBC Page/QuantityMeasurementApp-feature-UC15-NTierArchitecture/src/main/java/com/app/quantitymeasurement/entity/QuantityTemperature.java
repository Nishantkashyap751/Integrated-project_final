package com.app.quantitymeasurement.entity;

import com.app.quantitymeasurement.unit.TemperatureUnit;

public class QuantityTemperature {
    private final Quantity<TemperatureUnit> quantity;

    public QuantityTemperature(double value, TemperatureUnit unit) {
        this.quantity = new Quantity<>(value, unit);
    }

    public double getValue() {
        return quantity.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof QuantityTemperature)) return false;
        return this.quantity.equals(((QuantityTemperature) o).quantity);
    }
}
