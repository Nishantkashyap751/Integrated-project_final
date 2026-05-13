package com.quantity.measurement;

import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.model.QuantityLength;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityLengthTest {

    // UC8 Examples from Specification

    @Test
    void given1Feet_WhenConvertedToInches_ShouldReturn12Inches() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength result = q1.convertTo(LengthUnit.INCHES);
        
        assertEquals(new QuantityLength(12.0, LengthUnit.INCHES), result);
    }

    @Test
    void given1FeetAnd12Inches_WhenAddedWithTargetFeet_ShouldReturn2Feet() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength result = q1.add(q2, LengthUnit.FEET);
        
        assertEquals(new QuantityLength(2.0, LengthUnit.FEET), result);
    }

    @Test
    void given36InchesAnd1Yard_WhenCompared_ShouldBeEqual() {
        QuantityLength q1 = new QuantityLength(36.0, LengthUnit.INCHES);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.YARDS);
        
        assertTrue(q1.equals(q2));
    }

    @Test
    void given1YardAnd3Feet_WhenAddedWithTargetYards_ShouldReturn2Yards() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength result = q1.add(q2, LengthUnit.YARDS);
        
        assertEquals(new QuantityLength(2.0, LengthUnit.YARDS), result);
    }

    @Test
    void given2Point54Cm_WhenConvertedToInches_ShouldReturn1Inch() {
        QuantityLength q1 = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength result = q1.convertTo(LengthUnit.INCHES);
        
        assertEquals(new QuantityLength(1.0, LengthUnit.INCHES), result);
    }

    @Test
    void given5FeetAnd0Inches_WhenAddedWithTargetFeet_ShouldReturn5Feet() {
        QuantityLength q1 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(0.0, LengthUnit.INCHES);
        QuantityLength result = q1.add(q2, LengthUnit.FEET);
        
        assertEquals(new QuantityLength(5.0, LengthUnit.FEET), result);
    }

    // Standalone LengthUnit conversion tests

    @Test
    void LengthUnit_FEET_convertToBaseUnit_12_ShouldReturn_12() {
        assertEquals(12.0, LengthUnit.FEET.convertToBaseUnit(12.0));
    }

    @Test
    void LengthUnit_INCHES_convertToBaseUnit_12_ShouldReturn_1() {
        assertEquals(1.0, LengthUnit.INCHES.convertToBaseUnit(12.0));
    }

    // Additional coverage for backward compatibility and edge cases

    @Test
    void given1FeetAnd12Inches_WhenAddedWithTargetInches_ShouldReturn24Inches() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength result = q1.add(q2, LengthUnit.INCHES);
        
        assertEquals(new QuantityLength(24.0, LengthUnit.INCHES), result);
    }

    @Test
    void givenImplicitAddition_ShouldDefaultToFirstOperandUnit() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);
        
        QuantityLength result = q1.add(q2);
        
        assertEquals(LengthUnit.FEET, result.getUnit());
        assertEquals(new QuantityLength(2.0, LengthUnit.FEET), result);
    }

    @Test
    void givenNullTargetUnit_WhenAdded_ShouldThrowException() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        assertThrows(IllegalArgumentException.class, () -> q1.add(q1, null));
    }

    @Test
    void givenNaNValue_WhenCreated_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new QuantityLength(Double.NaN, LengthUnit.FEET));
    }

    @Test
    void givenInfinityValue_WhenCreated_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new QuantityLength(Double.POSITIVE_INFINITY, LengthUnit.FEET));
    }
}
