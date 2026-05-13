package com.quantity.measurement;

import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.enums.VolumeUnit;
import com.quantity.measurement.enums.WeightUnit;
import com.quantity.measurement.model.Quantity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UC12SubtractionDivisionTest {

    @Test
    public void givenTenFeetAndFiveFeet_WhenSubtracted_ShouldReturnFiveFeet() {
        Quantity<LengthUnit> tenFeet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> fiveFeet = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = tenFeet.subtract(fiveFeet);
        assertEquals(new Quantity<>(5.0, LengthUnit.FEET), result);
    }

    @Test
    public void givenTenFeetAndSixInches_WhenSubtracted_ShouldReturnNinePointFiveFeet() {
        Quantity<LengthUnit> tenFeet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> sixInches = new Quantity<>(6.0, LengthUnit.INCH);
        Quantity<LengthUnit> result = tenFeet.subtract(sixInches);
        assertEquals(new Quantity<>(9.5, LengthUnit.FEET), result);
    }

    @Test
    public void givenHundredTwentyInchesAndFiveFeet_WhenSubtracted_ShouldReturnSixtyInches() {
        Quantity<LengthUnit> hundredTwentyInches = new Quantity<>(120.0, LengthUnit.INCH);
        Quantity<LengthUnit> fiveFeet = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = hundredTwentyInches.subtract(fiveFeet);
        assertEquals(new Quantity<>(60.0, LengthUnit.INCH), result);
    }

    @Test
    public void givenTenFeetAndFiveFeet_WhenDivided_ShouldReturnTwo() {
        Quantity<LengthUnit> tenFeet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> fiveFeet = new Quantity<>(5.0, LengthUnit.FEET);
        double result = tenFeet.divide(fiveFeet);
        assertEquals(2.0, result, 1e-6);
    }

    @Test
    public void givenOneGallonAndOneLitre_WhenDivided_ShouldReturnCorrectRatio() {
        Quantity<VolumeUnit> oneGallon = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> oneLitre = new Quantity<>(1.0, VolumeUnit.LITRE);
        double result = oneGallon.divide(oneLitre);
        assertEquals(3.78541, result, 1e-6);
    }

    @Test
    public void givenQuantityDividedByZero_ShouldThrowArithmeticException() {
        Quantity<LengthUnit> tenFeet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> zeroFeet = new Quantity<>(0.0, LengthUnit.FEET);
        assertThrows(ArithmeticException.class, () -> tenFeet.divide(zeroFeet));
    }

    @Test
    public void givenCrossCategoryQuantities_WhenSubtracted_ShouldThrowIllegalArgumentException() {
        Quantity<LengthUnit> tenFeet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity qWeight = new Quantity<>(5.0, WeightUnit.KILOGRAM);
        assertThrows(IllegalArgumentException.class, () -> tenFeet.subtract(qWeight));
    }

    @Test
    public void givenCrossCategoryQuantities_WhenDivided_ShouldThrowIllegalArgumentException() {
        Quantity<LengthUnit> tenFeet = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity qVolume = new Quantity<>(1.0, VolumeUnit.LITRE);
        assertThrows(IllegalArgumentException.class, () -> tenFeet.divide(qVolume));
    }

    @Test
    public void givenSubtractedValues_ShouldBeRoundedToTwoDecimalPlaces() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(0.333333, LengthUnit.FEET); // 1/3
        Quantity<LengthUnit> result = q1.subtract(q2);
        assertEquals(0.67, result.getValue());
    }
}
