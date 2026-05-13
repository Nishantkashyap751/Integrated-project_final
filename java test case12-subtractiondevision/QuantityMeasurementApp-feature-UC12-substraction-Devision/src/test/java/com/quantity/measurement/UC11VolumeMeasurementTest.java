package com.quantity.measurement;

import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.enums.VolumeUnit;
import com.quantity.measurement.enums.WeightUnit;
import com.quantity.measurement.model.Quantity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UC11VolumeMeasurementTest {

    private static final double EPSILON = 1e-6;

    @Test
    public void given1LitreAnd1Litre_WhenCompared_ShouldReturnTrue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertEquals(v1, v2);
    }

    @Test
    public void given1LitreAnd1000Millilitre_WhenCompared_ShouldReturnTrue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertEquals(v1, v2);
    }

    @Test
    public void given500MillilitreAnd0_5Litre_WhenCompared_ShouldReturnTrue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(0.5, VolumeUnit.LITRE);
        assertEquals(v1, v2);
    }

    @Test
    public void given3_78541LitreAnd1Gallon_WhenCompared_ShouldReturnTrue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(3.78541, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1.0, VolumeUnit.GALLON);
        assertEquals(v1, v2);
    }

    @Test
    public void given1LitreAndNegative0_264172Gallon_WhenCompared_ShouldReturnFalse() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(-0.264172, VolumeUnit.GALLON);
        assertNotEquals(v1, v2);
    }

    @Test
    public void given1GallonAnd1Gallon_WhenCompared_ShouldReturnTrue() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> v2 = new Quantity<>(1.0, VolumeUnit.GALLON);
        assertEquals(v1, v2);
    }

    @Test
    public void given1Litre_WhenConvertedToMillilitre_ShouldReturn1000Millilitre() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> result = v1.convertTo(VolumeUnit.MILLILITRE);
        assertEquals(1000.0, result.getValue(), EPSILON);
        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
    }

    @Test
    public void given2Gallon_WhenConvertedToLitre_ShouldReturn7_57082Litre() {
        Quantity<VolumeUnit> v1 = new Quantity<>(2.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> result = v1.convertTo(VolumeUnit.LITRE);
        assertEquals(7.57082, result.getValue(), EPSILON);
        assertEquals(VolumeUnit.LITRE, result.getUnit());
    }

    @Test
    public void given1LitreAnd2Litre_WhenAdded_ShouldReturn3Litre() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(2.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> result = v1.add(v2);
        assertEquals(3.0, result.getValue(), EPSILON);
        assertEquals(VolumeUnit.LITRE, result.getUnit());
    }

    @Test
    public void given1LitreAnd1000Millilitre_WhenAdded_ShouldReturn2Litre() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> result = v1.add(v2);
        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(VolumeUnit.LITRE, result.getUnit());
    }

    @Test
    public void given500MillilitreAnd0_5Litre_WhenAdded_ShouldReturn1000Millilitre() {
        Quantity<VolumeUnit> v1 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(0.5, VolumeUnit.LITRE);
        Quantity<VolumeUnit> result = v1.add(v2, VolumeUnit.MILLILITRE);
        assertEquals(1000.0, result.getValue(), EPSILON);
        assertEquals(VolumeUnit.MILLILITRE, result.getUnit());
    }

    @Test
    public void given1LitreAnd1Feet_WhenCompared_ShouldReturnFalse() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
        assertNotEquals(v1, l1);
    }

    @Test
    public void given1LitreAnd1Kilogram_WhenCompared_ShouldReturnFalse() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertNotEquals(v1, w1);
    }
}
