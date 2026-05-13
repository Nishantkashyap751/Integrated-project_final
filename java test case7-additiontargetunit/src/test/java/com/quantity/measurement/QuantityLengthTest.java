package com.quantity.measurement;

import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.model.QuantityLength;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityLengthTest {

    @Test
    void given1FeetAnd12Inches_WhenAddedWithTargetFeet_ShouldReturn2Feet() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength result = q1.add(q2, LengthUnit.FEET);
        
        assertEquals(new QuantityLength(2.0, LengthUnit.FEET), result);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void given1FeetAnd12Inches_WhenAddedWithTargetInches_ShouldReturn24Inches() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength result = q1.add(q2, LengthUnit.INCHES);
        
        assertEquals(new QuantityLength(24.0, LengthUnit.INCHES), result);
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    @Test
    void given1FeetAnd12Inches_WhenAddedWithTargetYards_ShouldReturnPointsTwoThirdsYards() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength result = q1.add(q2, LengthUnit.YARDS);
        
        // 2 feet = 0.666666... yards
        assertEquals(new QuantityLength(0.6666666666666666, LengthUnit.YARDS), result);
        assertEquals(LengthUnit.YARDS, result.getUnit());
    }

    @Test
    void given36InchesAnd1Yard_WhenAddedWithTargetFeet_ShouldReturn6Feet() {
        QuantityLength q1 = new QuantityLength(36.0, LengthUnit.INCHES);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength result = q1.add(q2, LengthUnit.FEET);
        
        assertEquals(new QuantityLength(6.0, LengthUnit.FEET), result);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void given2Point54CmAnd1Inch_WhenAddedWithTargetCm_ShouldReturn5Point08Cm() {
        QuantityLength q1 = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.INCHES);
        QuantityLength result = q1.add(q2, LengthUnit.CENTIMETERS);
        
        assertEquals(new QuantityLength(5.08, LengthUnit.CENTIMETERS), result);
        assertEquals(LengthUnit.CENTIMETERS, result.getUnit());
    }

    @Test
    void givenTwoQuantities_WhenAdded_ShouldBeCommutative() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);
        
        QuantityLength res1 = q1.add(q2, LengthUnit.YARDS);
        QuantityLength res2 = q2.add(q1, LengthUnit.YARDS);
        
        assertEquals(res1, res2);
    }

    @Test
    void givenNullTargetUnit_WhenAdded_ShouldThrowIllegalArgumentException() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);
        
        assertThrows(IllegalArgumentException.class, () -> q1.add(q2, null));
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
    void givenNegativeValue_WhenAdded_ShouldHandleCorrectly() {
        QuantityLength q1 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(-2.0, LengthUnit.FEET);
        QuantityLength result = q1.add(q2, LengthUnit.INCHES);
        
        // 3 feet = 36 inches
        assertEquals(new QuantityLength(36.0, LengthUnit.INCHES), result);
    }
}
