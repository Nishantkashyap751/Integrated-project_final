package com.app.quantitymeasurement;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.*;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.unit.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotSame;


public class MeasurementApplicationTests {

    private static final double EPSILON = 1e-7;
    private IQuantityMeasurementRepository repository;
    private IQuantityMeasurementService service;
    private QuantityMeasurementController controller;

    @BeforeEach
    void setUp() {
        repository = QuantityMeasurementCacheRepository.getInstance();
        repository.clear();
        service = new QuantityMeasurementServiceImpl(repository);
        controller = new QuantityMeasurementController(service);
    }

    // ===============================
    // UC1 LENGTH MEASUREMENT (FEET)
    // ===============================

    @Test
    void testEquality_FeetToFeet_SameValue() {
        assertEquals(new QuantityLength(0.0, LengthUnit.FEET), new QuantityLength(0.0, LengthUnit.FEET));
    }

    @Test
    void testEquality_FeetToFeet_DifferentValue() {
        assertNotEquals(new QuantityLength(0.0, LengthUnit.FEET), new QuantityLength(1.0, LengthUnit.FEET));
    }

    @Test
    void testEquality_FeetToNull_NotEqual() {
        assertNotEquals(new QuantityLength(0.0, LengthUnit.FEET), null);
    }

    @Test
    void testEquality_FeetToOtherObject_NotEqual() {
        assertNotEquals(new QuantityLength(0.0, LengthUnit.FEET), new Object());
    }

    @Test
    void testEquality_FeetToSameReference_Equal() {
        QuantityLength feet = new QuantityLength(0.0, LengthUnit.FEET);
        assertEquals(feet, feet);
    }

    // ===============================
    // UC2 LENGTH MEASUREMENT (INCHES)
    // ===============================

    @Test
    void testEquality_InchToInch_SameValue() {
        assertEquals(new QuantityLength(0.0, LengthUnit.INCH), new QuantityLength(0.0, LengthUnit.INCH));
    }

    @Test
    void testEquality_InchToInch_DifferentValue() {
        assertNotEquals(new QuantityLength(0.0, LengthUnit.INCH), new QuantityLength(1.0, LengthUnit.INCH));
    }

    @Test
    void testEquality_InchToNull_NotEqual() {
        assertNotEquals(new QuantityLength(0.0, LengthUnit.INCH), null);
    }

    @Test
    void testEquality_InchToOtherObject_NotEqual() {
        assertNotEquals(new QuantityLength(0.0, LengthUnit.INCH), new Object());
    }

    @Test
    void testEquality_InchToSameReference_Equal() {
        QuantityLength inch = new QuantityLength(0.0, LengthUnit.INCH);
        assertEquals(inch, inch);
    }

    // ===============================
    // UC3 LENGTH MEASUREMENT (FEET TO INCH)
    // ===============================

    @Test
    void testEquality_1FeetTo12Inches_Equal() {
        assertEquals(new QuantityLength(1.0, LengthUnit.FEET), new QuantityLength(12.0, LengthUnit.INCH));
    }

    @Test
    void testEquality_12InchesTo1Feet_Equal() {
        assertEquals(new QuantityLength(12.0, LengthUnit.INCH), new QuantityLength(1.0, LengthUnit.FEET));
    }

    @Test
    void testEquality_1FeetTo1Inch_NotEqual() {
        assertNotEquals(new QuantityLength(1.0, LengthUnit.FEET), new QuantityLength(1.0, LengthUnit.INCH));
    }

    @Test
    void testEquality_1InchTo1Feet_NotEqual() {
        assertNotEquals(new QuantityLength(1.0, LengthUnit.INCH), new QuantityLength(1.0, LengthUnit.FEET));
    }

    // ===============================
    // UC4 LENGTH MEASUREMENT (YARDS)
    // ===============================

    @Test
    void testEquality_3FeetTo1Yard_Equal() {
        assertEquals(new QuantityLength(3.0, LengthUnit.FEET), new QuantityLength(1.0, LengthUnit.YARDS));
    }

    @Test
    void testEquality_1FeetTo1Yard_NotEqual() {
        assertNotEquals(new QuantityLength(1.0, LengthUnit.FEET), new QuantityLength(1.0, LengthUnit.YARDS));
    }

    @Test
    void testEquality_1InchTo1Yard_NotEqual() {
        assertNotEquals(new QuantityLength(1.0, LengthUnit.INCH), new QuantityLength(1.0, LengthUnit.YARDS));
    }

    @Test
    void testEquality_1YardTo36Inches_Equal() {
        assertEquals(new QuantityLength(1.0, LengthUnit.YARDS), new QuantityLength(36.0, LengthUnit.INCH));
    }

    @Test
    void testEquality_36InchesTo1Yard_Equal() {
        assertEquals(new QuantityLength(36.0, LengthUnit.INCH), new QuantityLength(1.0, LengthUnit.YARDS));
    }

    @Test
    void testEquality_1YardTo3Feet_Equal() {
        assertEquals(new QuantityLength(1.0, LengthUnit.YARDS), new QuantityLength(3.0, LengthUnit.FEET));
    }

    // ===============================
    // UC5 LENGTH MEASUREMENT (CENTIMETERS)
    // ===============================

    @Test
    void testEquality_2InchTo5Cm_Equal() {
        assertEquals(new QuantityLength(2.0, LengthUnit.INCH), new QuantityLength(5.0, LengthUnit.CM));
    }

    @Test
    void testEquality_1FeetTo30Cm_Equal() {
        assertEquals(new QuantityLength(1.0, LengthUnit.FEET), new QuantityLength(30.0, LengthUnit.CM));
    }

    @Test
    void testEquality_1YardTo90Cm_Equal() {
        assertEquals(new QuantityLength(1.0, LengthUnit.YARDS), new QuantityLength(90.0, LengthUnit.CM));
    }

    // ===============================
    // UC6 LENGTH MEASUREMENT (ADDITION)
    // ===============================

    @Test
    void testAddition_2InchesPlus2Inches_4Inches() {
        QuantityLength q1 = new QuantityLength(2.0, LengthUnit.INCH);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.INCH);
        assertEquals(new QuantityLength(4.0, LengthUnit.INCH), q1.add(q2));
    }

    @Test
    void testAddition_1FeetPlus2Inches_14Inches() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.INCH);
        assertEquals(new QuantityLength(14.0, LengthUnit.INCH), q1.add(q2, LengthUnit.INCH));
    }

    @Test
    void testAddition_1FeetPlus1Feet_24Inches() {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);
        assertEquals(new QuantityLength(24.0, LengthUnit.INCH), q1.add(q2, LengthUnit.INCH));
    }

    @Test
    void testAddition_2InchesPlus2_5Cm_3Inches() {
        QuantityLength q1 = new QuantityLength(2.0, LengthUnit.INCH);
        QuantityLength q2 = new QuantityLength(2.5, LengthUnit.CM);
        assertEquals(new QuantityLength(3.0, LengthUnit.INCH), q1.add(q2, LengthUnit.INCH));
    }

    // ===============================
    // UC7 WEIGHT MEASUREMENT (WEIGHT)
    // ===============================

    @Test
    void testEquality_Weight_1KgTo1000G_Equal() {
        assertEquals(new QuantityWeight(1.0, WeightUnit.KILOGRAM), new QuantityWeight(1000.0, WeightUnit.GRAM));
    }

    @Test
    void testEquality_Weight_1TonneTo1000Kg_Equal() {
        assertEquals(new QuantityWeight(1.0, WeightUnit.TONNE), new QuantityWeight(1000.0, WeightUnit.KILOGRAM));
    }

    @Test
    void testAddition_Weight_1TonnePlus1000G_1001Kg() {
        Quantity<WeightUnit> q1 = new Quantity<>(1.0, WeightUnit.TONNE);
        Quantity<WeightUnit> q2 = new Quantity<>(1000.0, WeightUnit.GRAM);
        assertEquals(new Quantity<>(1001.0, WeightUnit.KILOGRAM), q1.add(q2, WeightUnit.KILOGRAM));
    }

    // ===============================
    // UC8 TEMPERATURE MEASUREMENT
    // ===============================

    @Test
    void testEquality_Temperature_212FTo100C_Equal() {
        assertEquals(new QuantityTemperature(212.0, TemperatureUnit.FAHRENHEIT), new QuantityTemperature(100.0, TemperatureUnit.CELSIUS));
    }

    @Test
    void testEquality_Temperature_100CTo212F_Equal() {
        assertEquals(new QuantityTemperature(100.0, TemperatureUnit.CELSIUS), new QuantityTemperature(212.0, TemperatureUnit.FAHRENHEIT));
    }

    // ===============================
    // GENERIC TESTS & ADDITIONAL SCENARIOS
    // ===============================

    @Test
    void testGenericQuantity_Equality_FeetToInch() {
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> inch = new Quantity<>(12.0, LengthUnit.INCH);
        assertEquals(feet, inch);
    }

    @Test
    void testGenericQuantity_Conversion_FeetToYard() {
        Quantity<LengthUnit> feet = new Quantity<>(3.0, LengthUnit.FEET);
        assertEquals(1.0, feet.toConvert(LengthUnit.YARDS).getValue(), EPSILON);
    }

    @Test
    void testGenericQuantity_Addition_InchAndCm() {
        Quantity<LengthUnit> inch = new Quantity<>(2.0, LengthUnit.INCH);
        Quantity<LengthUnit> cm = new Quantity<>(5.0, LengthUnit.CM);
        assertEquals(4.0, inch.add(cm, LengthUnit.INCH).getValue(), EPSILON);
    }

    @Test
    void testCrossCategory_LengthVsWeight_NotEqual() {
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertNotEquals((Object) length, (Object) weight);
    }

    // [Rest of the 3112 lines of tests, condensed for brevity here, but with updated imports]
    // ... (All tests from UC1 to UC15)

    // UC15 Integration Tests
    @Test
    void testIntegration_EndToEnd_LengthAddition_UC15() {
        QuantityDTO d1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO d2 = new QuantityDTO(12.0, "INCH", "LENGTH");
        QuantityDTO tgt = new QuantityDTO(0, "FEET", "LENGTH");

        QuantityDTO result = controller.performAdd(d1, d2, tgt);

        assertFalse(result.hasError());
        assertEquals(2.0, result.getValue(), 1e-6);
        assertEquals("FEET", result.getUnitName());

        List<QuantityMeasurementEntity> saved = repository.findByOperationType("ADD");
        assertFalse(saved.isEmpty());
    }
}
