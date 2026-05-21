package QuantityMeasurementApp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.model.QuantityLength;

class MeasurementApplicationTests {
    /*
     * @Test
     * void testFeetEquality_SameValue(){
     * MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(89.6);
     * MeasurementApplication.Feet f2 = new MeasurementApplication.Feet(89.6);
     * 
     * assertEquals(f1, f2);
     * }
     * 
     * @Test
     * void testFeetEquality_DifferValue(){
     * MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(89.6);
     * MeasurementApplication.Feet f2 = new MeasurementApplication.Feet(34.6);
     * 
     * assertNotEquals(f1,f2);
     * }
     * 
     * @Test
     * void testFeetNullable_NullValue(){
     * MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(55.0);
     * 
     * assertFalse(f1.equals(null));
     * }
     * 
     * @Test
     * void testFeetEquality_ClassComparison(){
     * MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(89.6);
     * 
     * assertFalse(f1.equals("Some String"));
     * }
     * 
     * @Test
     * void testFeetEquality_SameReference(){
     * MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(89.6);
     * 
     * assertTrue(f1.equals(f1));
     * }
     * 
     * @Test
     * void testInchEquality_SameValue(){
     * MeasurementApplication.Inch i1 = new MeasurementApplication.Inch(12.5);
     * MeasurementApplication.Inch i2 = new MeasurementApplication.Inch(12.5);
     * 
     * assertEquals(i1, i2);
     * }
     * 
     * @Test
     * void testInchEquality_DifferValue(){
     * MeasurementApplication.Inch i1 = new MeasurementApplication.Inch(12.5);
     * MeasurementApplication.Inch i2 = new MeasurementApplication.Inch(10.0);
     * 
     * assertNotEquals(i1, i2);
     * }
     * 
     * @Test
     * void testInchNullable_NullValue(){
     * MeasurementApplication.Inch i1 = new MeasurementApplication.Inch(20.0);
     * 
     * assertFalse(i1.equals(null));
     * }
     * 
     * @Test
     * void testInchEquality_ClassComparison(){
     * MeasurementApplication.Inch i1 = new MeasurementApplication.Inch(15.0);
     * 
     * assertFalse(i1.equals("Some String"));
     * }
     * 
     * @Test
     * void testInchEquality_SameReference(){
     * MeasurementApplication.Inch i1 = new MeasurementApplication.Inch(18.0);
     * 
     * assertTrue(i1.equals(i1));
     * }
     */
    // //UC3 TEST CASES

    // @Test
    // void testLengthEquality_SameFeetValue() {
    // QuantityLength l1 = new QuantityLength(10.0, LengthUnit.FEET);
    // QuantityLength l2 = new QuantityLength(10.0, LengthUnit.FEET);

    // assertEquals(l1, l2);
    // }

    // @Test
    // void testLengthEquality_DifferentFeetValue() {
    // QuantityLength l1 = new QuantityLength(10.0, LengthUnit.FEET);
    // QuantityLength l2 = new QuantityLength(5.0, LengthUnit.FEET);

    // assertNotEquals(l1, l2);
    // }

    // @Test
    // void testLengthEquality_NullComparison() {
    // QuantityLength l1 = new QuantityLength(10.0, LengthUnit.FEET);

    // assertFalse(l1.equals(null));
    // }

    // @Test
    // void testLengthEquality_ClassComparison() {
    // QuantityLength l1 = new QuantityLength(10.0, LengthUnit.FEET);

    // assertFalse(l1.equals("Some String"));
    // }

    // @Test
    // void testLengthEquality_SameReference() {
    // QuantityLength l1 = new QuantityLength(10.0, LengthUnit.FEET);

    // assertTrue(l1.equals(l1));
    // }

    // @Test
    // void testLengthEquality_FeetAndInch_Equal() {
    // QuantityLength feet = new QuantityLength(1.0, LengthUnit.FEET);
    // QuantityLength inch = new QuantityLength(12.0, LengthUnit.INCH);

    // assertEquals(feet, inch);
    // }

    // @Test
    // void testLengthEquality_FeetAndInch_NotEqual() {
    // QuantityLength feet = new QuantityLength(2.0, LengthUnit.FEET);
    // QuantityLength inch = new QuantityLength(12.0, LengthUnit.INCH);

    // assertNotEquals(feet, inch);
    // }

    // @Test
    // void testLengthEquality_ZeroValues() {
    // QuantityLength l1 = new QuantityLength(0.0, LengthUnit.FEET);
    // QuantityLength l2 = new QuantityLength(0.0, LengthUnit.INCH);

    // assertEquals(l1, l2);
    // }

    // @Test
    // void testLengthEquality_NegativeValues() {
    // QuantityLength l1 = new QuantityLength(-1.0, LengthUnit.FEET);
    // QuantityLength l2 = new QuantityLength(-12.0, LengthUnit.INCH);

    // assertEquals(l1, l2);
    // }

    // @Test
    // void testConstructor_NullUnit_ShouldThrowException() {
    // assertThrows(IllegalArgumentException.class, () -> {
    // new QuantityLength(10.0, null);
    // });
    // }

    // UC4 TEST CASES
    @Test
    void testEquality_YardToYard_SameValue() {
        assertEquals(
                new QuantityLength(1.0, LengthUnit.YARDS),
                new QuantityLength(1.0, LengthUnit.YARDS));
    }

    // 2
    @Test
    void testEquality_YardToYard_DifferentValue() {
        assertNotEquals(
                new QuantityLength(1.0, LengthUnit.YARDS),
                new QuantityLength(2.0, LengthUnit.YARDS));
    }

    // 3
    @Test
    void testEquality_YardToFeet_EquivalentValue() {
        assertEquals(
                new QuantityLength(1.0, LengthUnit.YARDS),
                new QuantityLength(3.0, LengthUnit.FEET));
    }

    // 4
    @Test
    void testEquality_FeetToYard_EquivalentValue() {
        assertEquals(
                new QuantityLength(3.0, LengthUnit.FEET),
                new QuantityLength(1.0, LengthUnit.YARDS));
    }

    // 5
    @Test
    void testEquality_YardToInches_EquivalentValue() {
        assertEquals(
                new QuantityLength(1.0, LengthUnit.YARDS),
                new QuantityLength(36.0, LengthUnit.INCH));
    }

    // 6
    @Test
    void testEquality_InchesToYard_EquivalentValue() {
        assertEquals(
                new QuantityLength(36.0, LengthUnit.INCH),
                new QuantityLength(1.0, LengthUnit.YARDS));
    }

    // 7
    @Test
    void testEquality_YardToFeet_NonEquivalentValue() {
        assertNotEquals(
                new QuantityLength(1.0, LengthUnit.YARDS),
                new QuantityLength(2.0, LengthUnit.FEET));
    }

    // 8
    @Test
    void testEquality_CentimetersToInches_EquivalentValue() {
        assertEquals(
                new QuantityLength(1.0, LengthUnit.CENTIMETERS),
                new QuantityLength(0.393701, LengthUnit.INCH));
    }

    // 9
    @Test
    void testEquality_CentimetersToFeet_NonEquivalentValue() {
        assertNotEquals(
                new QuantityLength(1.0, LengthUnit.CENTIMETERS),
                new QuantityLength(1.0, LengthUnit.FEET));
    }

    // 10 (Transitive property)
    @Test
    void testEquality_MultiUnit_TransitiveProperty() {
        QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength inch = new QuantityLength(36.0, LengthUnit.INCH);

        assertEquals(yard, feet);
        assertEquals(feet, inch);
        assertEquals(yard, inch);
    }

    // 11
    @Test
    void testEquality_YardWithNullUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityLength(1.0, null);
        });
    }

    // 12 (Reflexive)
    @Test
    void testEquality_YardSameReference() {
        QuantityLength q = new QuantityLength(1.0, LengthUnit.YARDS);
        assertEquals(q, q);
    }

    // 13
    @Test
    void testEquality_YardNullComparison() {
        QuantityLength q = new QuantityLength(1.0, LengthUnit.YARDS);
        assertNotEquals(q, null);
    }

    // 14
    @Test
    void testEquality_CentimetersWithNullUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityLength(1.0, null);
        });
    }

    // 15 (Reflexive)
    @Test
    void testEquality_CentimetersSameReference() {
        QuantityLength q = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        assertEquals(q, q);
    }

    // 16
    @Test
    void testEquality_CentimetersNullComparison() {
        QuantityLength q = new QuantityLength(1.0, LengthUnit.CENTIMETERS);
        assertNotEquals(q, null);
    }

    // 17 (Complex multi-unit)
    @Test
    void testEquality_AllUnits_ComplexScenario() {
        QuantityLength yard = new QuantityLength(2.0, LengthUnit.YARDS);
        QuantityLength feet = new QuantityLength(6.0, LengthUnit.FEET);
        QuantityLength inch = new QuantityLength(72.0, LengthUnit.INCH);

        assertEquals(yard, feet);
        assertEquals(feet, inch);
        assertEquals(yard, inch);
    }

    // 18
    @Test
    void testConversion_FeetToInches() {
        double result = QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCH);
        assertEquals(12.0, result, 1e-6);
    }

    // 19
    @Test
    void testConversion_InchesToFeet() {
        double result = QuantityLength.convert(24.0, LengthUnit.INCH, LengthUnit.FEET);
        assertEquals(2.0, result, 1e-6);
    }

    // 20
    @Test
    void testConversion_YardsToInches() {
        double result = QuantityLength.convert(1.0, LengthUnit.YARDS, LengthUnit.INCH);
        assertEquals(36.0, result, 1e-6);
    }

    // 21
    @Test
    void testConversion_InchesToYards() {
        double result = QuantityLength.convert(72.0, LengthUnit.INCH, LengthUnit.YARDS);
        assertEquals(2.0, result, 1e-6);
    }
    // 22

    @Test
    void testConversion_CentimetersToInches() {
        double result = QuantityLength.convert(2.54, LengthUnit.CENTIMETERS, LengthUnit.INCH);
        assertEquals(1.0, result, 1e-6);
    }

    // 23
    @Test
    void testConversion_FeetToYards() {
        double result = QuantityLength.convert(6.0, LengthUnit.FEET, LengthUnit.YARDS);
        assertEquals(2.0, result, 1e-6);
    }

    // 24
    @Test
    void testConversion_ZeroValue() {
        double result = QuantityLength.convert(0.0, LengthUnit.FEET, LengthUnit.INCH);
        assertEquals(0.0, result, 1e-6);
    }

    // 25
    @Test
    void testConversion_NegativeValue() {
        double result = QuantityLength.convert(-1.0, LengthUnit.FEET, LengthUnit.INCH);
        assertEquals(-12.0, result, 1e-6);
    }

    // 26
    @Test
    void testConversion_RoundTrip() {
        double inches = QuantityLength.convert(5.0, LengthUnit.FEET, LengthUnit.INCH);
        double feet = QuantityLength.convert(inches, LengthUnit.INCH, LengthUnit.FEET);
        assertEquals(5.0, feet, 1e-6);
    }

    // 27
    @Test
    void testConversion_SameUnit() {
        double result = QuantityLength.convert(5.0, LengthUnit.FEET, LengthUnit.FEET);
        assertEquals(5.0, result, 1e-6);
    }

    // 28
    @Test
    void testConversion_NullSource_Throws() {
        assertThrows(IllegalArgumentException.class, () -> QuantityLength.convert(1.0, null, LengthUnit.FEET));
    }

    // 29
    @Test
    void testConversion_NullTarget_Throws() {
        assertThrows(IllegalArgumentException.class, () -> QuantityLength.convert(1.0, LengthUnit.FEET, null));
    }

    // 30
    @Test
    void testConversion_NaN_Throws() {
        assertThrows(IllegalArgumentException.class,
                () -> QuantityLength.convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCH));
    }

    // 31
    @Test
    void testConversion_Infinite_Throws() {
        assertThrows(IllegalArgumentException.class,
                () -> QuantityLength.convert(Double.POSITIVE_INFINITY, LengthUnit.FEET, LengthUnit.INCH));
    }

    // 32
    @Test
    void testInstanceMethod_ConvertTo() {
        QuantityLength length = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength converted = length.convertTo(LengthUnit.INCH);

        assertEquals(36.0, converted.getValue(), 1e-6);
        assertEquals(LengthUnit.INCH, converted.getUnit());
    }

    // ================================================================================================
    // UC6 TEST CASES
    @Test
    void testAddition_SameUnit_FeetPlusFeet() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);
        QuantityLength result = QuantityLength.add(q1, q2);
        assertEquals(new QuantityLength(3.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_SameUnit_InchPlusInch() {
        QuantityLength q1 = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength result = q1.add(q2);
        assertEquals(new QuantityLength(24.0, LengthUnit.INCH), result);
    }

    @Test
    void testAddition_CrossUnit_FeetPlusInches() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength result = QuantityLength.add(q1, q2);
        assertEquals(new QuantityLength(2.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_CrossUnit_InchPlusFeet() {
        QuantityLength q1 = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength result = QuantityLength.add(q1, q2);
        assertEquals(new QuantityLength(24.0, LengthUnit.INCH), result);
    }

    @Test
    void testAddition_CrossUnit_YardPlusFeet() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);
        QuantityLength result = QuantityLength.add(q1, q2);
        assertEquals(new QuantityLength(2.0, LengthUnit.YARDS), result);
    }

    @Test
    void testAddition_CrossUnit_CentimeterPlusInch() {
        QuantityLength q1 = new QuantityLength(2.54, LengthUnit.CENTIMETERS);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.INCH);
        QuantityLength result = QuantityLength.add(q1, q2);
        assertEquals(new QuantityLength(5.08, LengthUnit.CENTIMETERS), result);
    }

    @Test
    void testAddition_Commutativity() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength result1 = QuantityLength.add(q1, q2, LengthUnit.INCH);
        QuantityLength result2 = QuantityLength.add(q2, q1, LengthUnit.INCH);
        assertEquals(result1, result2);
    }

    @Test
    void testAddition_WithZero() {
        QuantityLength q1 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(0.0, LengthUnit.INCH);
        QuantityLength result = QuantityLength.add(q1, q2);
        assertEquals(new QuantityLength(5.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_NegativeValues() {
        QuantityLength q1 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(-2.0, LengthUnit.FEET);
        QuantityLength result = QuantityLength.add(q1, q2);
        assertEquals(new QuantityLength(3.0, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_NullSecondOperand() {
        QuantityLength q1 = new QuantityLength(5.0, LengthUnit.FEET);
        assertThrows(IllegalArgumentException.class, () -> q1.add(null));
    }

    @Test
    void testAddition_LargeValues() {
        QuantityLength q1 = new QuantityLength(1e6, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(1e6, LengthUnit.FEET);
        QuantityLength result = QuantityLength.add(q1, q2);
        assertEquals(new QuantityLength(2e6, LengthUnit.FEET), result);
    }

    @Test
    void testAddition_SmallValues() {
        QuantityLength q1 = new QuantityLength(0.001, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(0.002, LengthUnit.FEET);
        QuantityLength result = QuantityLength.add(q1, q2);
        assertEquals(new QuantityLength(0.003, LengthUnit.FEET), result);
    }
}
